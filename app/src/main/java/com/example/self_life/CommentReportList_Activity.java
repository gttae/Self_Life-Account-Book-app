package com.example.self_life;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CommentReportList_Activity extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private List<ReportComment_List> RCList = new ArrayList<>();
    private ReportCommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentreport_list);
        bottomNavigationView = findViewById(R.id.bottomNavigationView5);
        recyclerView = findViewById(R.id.board_reportCommenttList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/ReportData/CommentReport");
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                RCList.clear();
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    String reportId = snapshot.child("ReportId").getValue(String.class);
                    String nickName = snapshot.child("NickName").getValue(String.class);
                    String postId = snapshot.child("PostInfo").getValue(String.class);
                    String date = snapshot.child("Date").getValue(String.class);
                    String content = snapshot.child("Content").getValue(String.class);
                    String commentId = snapshot.child("commentId").getValue(String.class);
                    RCList.add(new ReportComment_List(reportId,postId,nickName,date,content,commentId));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter = new ReportCommentAdapter(this,RCList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ReportCommentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String postId) {
                showReportOptionsDialog(position,postId);
            }
        });


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_cal) {
                    Intent intent = new Intent(CommentReportList_Activity.this, Calendar_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_chart) {
                    Intent intent = new Intent(CommentReportList_Activity.this, Chart_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_lifeitem) {
                    Intent intent = new Intent(CommentReportList_Activity.this, LifeItem_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_board) {
                    Intent intent = new Intent(CommentReportList_Activity.this, Board_Activity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
    private void showReportOptionsDialog(int position, String postId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("신고 옵션 선택");

        String[] options = {"유저 강퇴하기", "무시하기"};

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) { // "유저 강퇴하기" 선택
                    // 해당 아이템과 관련된 데이터를 self_life/UserData/writer에서 삭제
                    mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/UserData");
                    mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                            for(DataSnapshot snapshot : datasnapshot.getChildren()){
                                String userNickname = snapshot.child("UserInfo").child("userNickName").getValue(String.class);
                                String userNickName = RCList.get(position).getNickcname();
                                if (userNickname.equals(userNickName)) {
                                    String postId = RCList.get(position).getPostId();
                                    mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/BoardData/" + postId +"/comment");
                                    mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                            for(DataSnapshot snapshot : datasnapshot.getChildren()){
                                                String content = snapshot.child("content").getValue(String.class);
                                                String Content = RCList.get(position).getContent();
                                                if (content.equals(Content)) {
                                                    String commentId = snapshot.child("CommentId").getValue(String.class);
                                                    DatabaseReference commentReference = FirebaseDatabase.getInstance().getReference("self_life/BoardData/" + postId +"/comment/"+commentId);
                                                    commentReference.removeValue();
                                                    Toast.makeText(CommentReportList_Activity.this, "유저를 강퇴시켰습니다.", Toast.LENGTH_SHORT).show();
                                                    break;
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    DatabaseReference userReference = snapshot.getRef();
                                    userReference.removeValue();
                                    break; // Assuming there is only one user with the specified nickname
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    String reportId = RCList.get(position).getReportId();
                    DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("self_life/ReportData/CommentReport/" + reportId);
                    userReference.removeValue();

                } else if (which == 1) { // "무시하기" 선택
                    String reportId = RCList.get(position).getReportId();
                    DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("self_life/ReportData/CommentReport/" + reportId);
                    userReference.removeValue();
                    Toast.makeText(CommentReportList_Activity.this, "신고내역을 삭제시켰습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CommentReportList_Activity.this, CommentReportList_Activity.class);
                    startActivity(intent);
                }
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }
}
