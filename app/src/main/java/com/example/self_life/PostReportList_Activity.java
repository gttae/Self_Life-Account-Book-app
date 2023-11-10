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


public class PostReportList_Activity extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private BottomNavigationView bottomNavigationView;
    private List<ReportPost_List> RPList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ReportPostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postreport_list);
        bottomNavigationView = findViewById(R.id.bottomNavigationView5);
        recyclerView = findViewById(R.id.board_reportList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/ReportData/PostReport");

        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                RPList.clear();
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    String reportId = snapshot.child("ReportId").getValue(String.class);
                    String writer = snapshot.child("Writer").getValue(String.class);
                    String category = snapshot.child("Category").getValue(String.class);
                    String title = snapshot.child("Title").getValue(String.class);
                    String postId = snapshot.child("PostInfo").getValue(String.class);
                    String date = snapshot.child("Date").getValue(String.class);
                    String content = snapshot.child("Content").getValue(String.class);
                    RPList.add(new ReportPost_List(reportId,postId,writer,category,title,date,content));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter = new ReportPostAdapter(this,RPList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ReportPostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String postId) {
                showReportOptionsDialog(position, postId);
            }
        });


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_cal) {
                    Intent intent = new Intent(PostReportList_Activity.this, Calendar_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_chart) {
                    Intent intent = new Intent(PostReportList_Activity.this, Chart_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_lifeitem) {
                    Intent intent = new Intent(PostReportList_Activity.this, LifeItem_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_board) {
                    Intent intent = new Intent(PostReportList_Activity.this, Board_Activity.class);
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
                    String writer = RPList.get(position).getWriter();
                    DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("self_life/UserData/" + writer);
                    userReference.removeValue();
                    String postId = RPList.get(position).getPostId();
                    DatabaseReference reportReference = FirebaseDatabase.getInstance().getReference("self_life/BoardData/" + postId);
                    reportReference.removeValue();
                    String reportId = RPList.get(position).getReportId();
                    DatabaseReference uuserReference = FirebaseDatabase.getInstance().getReference("self_life/ReportData/PostReport/" + reportId);
                    uuserReference.removeValue();
                    Toast.makeText(PostReportList_Activity.this, "해당 유저를 탈퇴시켰습니다.", Toast.LENGTH_SHORT).show();
                } else if (which == 1) { // "무시하기" 선택
                    String reportId = RPList.get(position).getReportId();
                    DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("self_life/ReportData/PostReport/" + reportId);
                    userReference.removeValue();
                    Toast.makeText(PostReportList_Activity.this, "신고내역을 삭제시켰습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PostReportList_Activity.this, PostReportList_Activity.class);
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
