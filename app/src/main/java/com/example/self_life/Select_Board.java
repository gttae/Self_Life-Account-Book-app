package com.example.self_life;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Select_Board extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ImageView mypageBtn, picture1;
    private TextView postTitleTextView, postWriterTextView, postCategoryTextView, postTimeTextView, postContentTextView;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mFirebaseAuth;
    private RecyclerView recyclerView;
    private CommentAdapter adapter;
    private List<Comment_List> commentList = new ArrayList<>();
    private EditText commentEt;
    private LinearLayout baseLl,commentLl,uploadComment,reportThisPost;
    private FrameLayout selectcomment;
    private String uid, commentname ,postId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_board);
        bottomNavigationView = findViewById(R.id.bottomNavigationView5);
        mypageBtn = findViewById(R.id.mypageBtn);
        postTitleTextView = findViewById(R.id.BoardTitle);
        postWriterTextView = findViewById(R.id.NameTv);
        postCategoryTextView = findViewById(R.id.BoardCategory);
        postTimeTextView = findViewById(R.id.DateTv);
        postContentTextView = findViewById(R.id.BoardTv);
        commentEt = findViewById(R.id.comment);
        baseLl = findViewById(R.id.boardBaseLl);
        commentLl = findViewById(R.id.commentPage);
        reportThisPost = findViewById(R.id.reportThisPost);
        uploadComment = findViewById(R.id.commentUpload);
        selectcomment = findViewById(R.id.commentPlus);
        picture1 = findViewById(R.id.PictureIv1);
        recyclerView = findViewById(R.id.commentVIew);
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        uid = firebaseUser.getUid();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StorageReference storageRef = FirebaseStorage.getInstance().getReference("1906053_.png");
        Intent intent = getIntent();
        postId = intent.getStringExtra("postId");
        Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/BoardData/" + postId);
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String title = dataSnapshot.child("title").getValue(String.class);
                String writer = dataSnapshot.child("writer").getValue(String.class);
                String category = dataSnapshot.child("category").getValue(String.class);
                String content = dataSnapshot.child("content").getValue(String.class);
                //String image = "\"C:\\Users\\gitae\\OneDrive\\Desktop\\image\\one-g2bc6019cf_640.png\"";
                //dataSnapshot.child("image").getValue(String.class);
                long time = dataSnapshot.child("time").getValue(Long.class);
                Date currentDate = new Date(time);
                // SimpleDateFormat을 사용하여 연월일 형식으로 변환
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = sdf.format(currentDate);

                // 가져온 데이터를 뷰에 설정
                postTitleTextView.setText(title);
                postWriterTextView.setText(writer);
                postCategoryTextView.setText(category);
                postContentTextView.setText(content);
                postTimeTextView.setText(formattedDate);
                //Toast.makeText(Select_Board.this,image,Toast.LENGTH_SHORT).show();
                Glide.with(Select_Board.this).load("https://firebasestorage.googleapis.com/v0/b/self-life-5b8ef.appspot.com/o/dice-g00bf226ea_640.png?alt=media&token=a23256c2-97bd-4a1e-8eb0-0f2f6e12478b&_gl=1*xng3ix*_ga*MzYzMDc0NTAxLjE2Nzk0MDU2OTQ.*_ga_CW55HF8NVT*MTY5NjgxODkzOS42NS4xLjE2OTY4MTg5NTYuNDMuMC4w").into(picture1);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 오류 처리
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_cal) {
                    Intent intent = new Intent(Select_Board.this, Calendar_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_chart) {
                    Intent intent = new Intent(Select_Board.this, Chart_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_lifeitem) {
                    Intent intent = new Intent(Select_Board.this, LifeItem_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_board) {
                    Intent intent = new Intent(Select_Board.this, Board_Activity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        mypageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Select_Board.this, MyPage_Activity.class);
                startActivity(intent);
            }
        });

        selectcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentLl.setVisibility(View.VISIBLE);
            }
        });

        baseLl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                commentLl.setVisibility(View.GONE);

                return false;
            }
        });

        commentLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        uploadComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference mDatabaseRef2 = FirebaseDatabase.getInstance().getReference("self_life/UserData/" + uid);
                mDatabaseRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        commentname = dataSnapshot.child("UserInfo").child("userNickName").getValue(String.class);
                        String content = commentEt.getText().toString();
                        String time = String.valueOf(System.currentTimeMillis());
                        DatabaseReference mDatabaseRef3 = FirebaseDatabase.getInstance().getReference("self_life/BoardData/" + postId).child("comment");
                        String commentId = mDatabaseRef3.push().getKey();
                        mDatabaseRef.child("comment").child(commentId).child("nickname").setValue(commentname);
                        //Toast.makeText(Select_Board.this, commentname, Toast.LENGTH_SHORT).show();
                        mDatabaseRef.child("comment").child(commentId).child("time").setValue(time);
                        mDatabaseRef.child("comment").child(commentId).child("content").setValue(content);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // 오류 처리
                    }
                });
            }
        });

        reportThisPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Select_Board.this);
                builder.setTitle("신고 확인");
                builder.setMessage("정말로 신고하시겠습니까?");

// 확인 버튼 설정
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String reportTitle = postTitleTextView.getText().toString();
                        String reportWriter = postWriterTextView.getText().toString();
                        String reportCategory = postCategoryTextView.getText().toString();
                        String reportContent = postContentTextView.getText().toString();
                        String reportTime = postTimeTextView.getText().toString();

                        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/ReportData/PostReport");
                        String tempReportPost = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(tempReportPost).child("PostInfo").setValue(postId);
                        mDatabaseRef.child(tempReportPost).child("Date").setValue(reportTime);
                        mDatabaseRef.child(tempReportPost).child("Writer").setValue(reportWriter);
                        mDatabaseRef.child(tempReportPost).child("Title").setValue(reportTitle);
                        mDatabaseRef.child(tempReportPost).child("Content").setValue(reportContent);
                        mDatabaseRef.child(tempReportPost).child("Category").setValue(reportCategory);
                        Toast.makeText(Select_Board.this,"신고가 완료되었습니다.",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

// 취소 버튼 설정
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

// 다이얼로그 표시
                builder.create().show();
            }
        });

        mDatabaseRef.child("comment").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                commentList.clear();
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    String nickname = snapshot.child("nickname").getValue(String.class);
                    long time = snapshot.child("time").getValue(Long.class);
                    Date currentDate = new Date(time);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = sdf.format(currentDate);
                    String commentId = "";
                    String content = snapshot.child("content").getValue(String.class);
                    commentList.add(new Comment_List(formattedDate,nickname,content,commentId));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter = new CommentAdapter(this, commentList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemLongClickListener(new CommentAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position, String commentId) {
                commentSelectionDialog(position);
            }
        });
    }

    private void commentSelectionDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // dialog_select_kind.xml 레이아웃을 inflate
        View view = getLayoutInflater().inflate(R.layout.comment_selection_dialog, null);
        builder.setView(view);

        // 버튼 클릭 리스너를 설정하여 사용자 선택 처리
        Button btnCommentMoidfy = view.findViewById(R.id.btnCommentMoidfy);
        Button btnCommentReport = view.findViewById(R.id.btnCommentReport);
        Button btnCommentDelete = view.findViewById(R.id.btnCommentDelete);

        final AlertDialog dialog = builder.create();

        btnCommentMoidfy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // "월급" 버튼을 클릭한 경우
                dialog.dismiss(); // 다이얼로그 닫기
            }
        });

        btnCommentReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // "부수입" 버튼을 클릭한 경우
                int selectedPosition = position/* 여기에 선택한 아이템의 위치를 설정하세요 */;
                Comment_List selectedComment = commentList.get(selectedPosition);

                String formattedDate = selectedComment.getDate();
                String nickname = selectedComment.getName();
                String content = selectedComment.getContent();

                mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/ReportData/CommentReport");
                String tempReportComment = mDatabaseRef.push().getKey();
                mDatabaseRef.child(tempReportComment).child("PostInfo").setValue(postId);
                mDatabaseRef.child(tempReportComment).child("Date").setValue(formattedDate);
                mDatabaseRef.child(tempReportComment).child("NickName").setValue(nickname);
                mDatabaseRef.child(tempReportComment).child("Content").setValue(content);
                Toast.makeText(Select_Board.this,"신고가 완료되었습니다.",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btnCommentDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // "용돈" 버튼을 클릭한 경우
                dialog.dismiss(); // 다이얼로그 닫기
            }
        });



        // 나머지 버튼들에 대해서도 동일한 방식으로 처리합니다.

        dialog.show();
    }
}

