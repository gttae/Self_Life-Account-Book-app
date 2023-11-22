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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Select_Board extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ImageView mypageBtn, picture1,picture2,picture3,picture4,picture5;
    private TextView postTitleTextView, postWriterTextView, postCategoryTextView, postTimeTextView, postContentTextView;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mFirebaseAuth;
    private RecyclerView recyclerView;
    private CommentAdapter adapter;
    private List<Comment_List> commentList = new ArrayList<>();
    private EditText commentEt,commentReEt;
    private LinearLayout baseLl,commentLl,uploadComment,reportThisPost,modifyThisPost,deleteThisPost,commentReLl,uploadReComment;
    private FrameLayout selectcomment;
    private String uid, commentname ,postId, postCreater, modifyComment;

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
        commentReLl = findViewById(R.id.commentRePage);
        commentReEt = findViewById(R.id.commentRe);
        uploadReComment = findViewById(R.id.commentReUpload);
        baseLl = findViewById(R.id.boardBaseLl);
        commentLl = findViewById(R.id.commentPage);
        reportThisPost = findViewById(R.id.reportThisPost);
        modifyThisPost = findViewById(R.id.modifyThisPost);
        deleteThisPost = findViewById(R.id.deleteThisPost);
        uploadComment = findViewById(R.id.commentUpload);
        selectcomment = findViewById(R.id.commentPlus);
        picture1 = findViewById(R.id.PictureIv1);
        picture2 = findViewById(R.id.PictureIv2);
        picture3 = findViewById(R.id.PictureIv3);
        picture4 = findViewById(R.id.PictureIv4);
        picture5 = findViewById(R.id.PictureIv5);
        recyclerView = findViewById(R.id.commentVIew);
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        uid = firebaseUser.getUid();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        postId = intent.getStringExtra("postId");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/BoardData/" + postId);
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<String> imageUris = new ArrayList<>();
                for (DataSnapshot imageSnapshot : dataSnapshot.child("image").getChildren()) {
                    String uri = imageSnapshot.getValue(String.class);
                    imageUris.add(uri);
                }

                ImageView[] pictureViews = {picture1, picture2, picture3, picture4, picture5};
                for (int i = 0; i < pictureViews.length; i++) {
                    // i번째 ImageView에 대한 visibility 및 이미지 설정
                    if (i < imageUris.size()) {
                        pictureViews[i].setVisibility(View.VISIBLE);
                        Glide.with(Select_Board.this).load(imageUris.get(i)).into(pictureViews[i]);
                    } else {
                        pictureViews[i].setVisibility(View.GONE);
                    }
                }

                String title = dataSnapshot.child("title").getValue(String.class);
                String writer = dataSnapshot.child("writer").getValue(String.class);
                postCreater = dataSnapshot.child("writer").getValue(String.class);
                String category = dataSnapshot.child("category").getValue(String.class);

                String content = dataSnapshot.child("content").getValue(String.class);
                long time = dataSnapshot.child("time").getValue(Long.class);
                Date currentDate = new Date(time);
                // SimpleDateFormat을 사용하여 연월일 형식으로 변환
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = sdf.format(currentDate);
                DatabaseReference mDatabaseRef2 = FirebaseDatabase.getInstance().getReference("self_life/UserData/"+writer);
                mDatabaseRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String userNametemp = dataSnapshot.child("UserInfo").child("userNickName").getValue(String.class);
                        postWriterTextView.setText(userNametemp);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // 오류 처리
                    }
                });

                // 가져온 데이터를 뷰에 설정
                postTitleTextView.setText(title);
                postWriterTextView.setText(writer);
                postCategoryTextView.setText(category);
                postContentTextView.setText(content);
                postTimeTextView.setText(formattedDate);
                if(uid.equals(postCreater) || uid.equals("dC9EUCkwqGeqQELJIYHLOEwYJzk2")){
                    modifyThisPost.setVisibility(View.VISIBLE);
                    deleteThisPost.setVisibility(View.VISIBLE);
                }
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
                        mDatabaseRef.child("comment").child(commentId).child("CommentId").setValue(commentId);
                        mDatabaseRef.child("comment").child(commentId).child("time").setValue(time);
                        mDatabaseRef.child("comment").child(commentId).child("content").setValue(content);
                        Toast.makeText(Select_Board.this,"댓글 작성이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Select_Board.this, Select_Board.class);
                        intent.putExtra("postId", postId);
                        startActivity(intent);
                        finish();
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
                        mDatabaseRef.child(tempReportPost).child("ReportId").setValue(tempReportPost);
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

        deleteThisPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference deletePostRef = FirebaseDatabase.getInstance().getReference("self_life/BoardData/" + postId);
                Toast.makeText(Select_Board.this,"게시물을 삭제하였습니다.",Toast.LENGTH_SHORT).show();
                deletePostRef.removeValue();
                Intent intent = new Intent(Select_Board.this, Board_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        modifyThisPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Select_Board.this, Modify_Post_Activity.class);
                intent.putExtra("postId", postId);
                startActivity(intent);
                finish();
            }
        });

        mDatabaseRef.child("comment").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                commentList.clear();
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    String nickname = snapshot.child("nickname").getValue(String.class);
                    String time = snapshot.child("time").getValue(String.class);
                    Long timetemp = Long.valueOf(time);
                    Date currentDate = new Date(timetemp);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = sdf.format(currentDate);
                    String commentId = snapshot.child("CommentId").getValue(String.class);
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
        btnCommentMoidfy.setVisibility(View.GONE);
        btnCommentDelete.setVisibility(View.GONE);

        int selectedPosition = position;
        Comment_List selectedComment = commentList.get(selectedPosition);
        String nickname = selectedComment.getName();
        DatabaseReference mDatabaseRef2 = FirebaseDatabase.getInstance().getReference("self_life/UserData/"+uid);
        mDatabaseRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userNametemp = dataSnapshot.child("UserInfo").child("userNickName").getValue(String.class);
                if(nickname.equals(userNametemp) || uid.equals("dC9EUCkwqGeqQELJIYHLOEwYJzk2")){
                    btnCommentMoidfy.setVisibility(View.VISIBLE);
                    btnCommentDelete.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 오류 처리
            }
        });

        final AlertDialog dialog = builder.create();

        btnCommentMoidfy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedPosition = position;
                Comment_List selectedComment = commentList.get(selectedPosition);
                modifyComment = selectedComment.getCommentId();
                String content = selectedComment.getContent();
                commentReLl.setVisibility(View.VISIBLE);
                commentReEt.setText(content);
                dialog.dismiss(); // 다이얼로그 닫기
            }
        });

        uploadReComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mDatabaseRef3 = FirebaseDatabase.getInstance().getReference("self_life/BoardData/" + postId + "/comment");
                mDatabaseRef3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String content = commentReEt.getText().toString();
                        String commentId = modifyComment;
                        mDatabaseRef3.child(commentId).child("content").setValue(content);
                        Intent intent = new Intent(Select_Board.this, Select_Board.class);
                        intent.putExtra("postId", postId);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // 오류 처리
                    }
                });
            }
        });

        commentReLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnCommentReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedPosition = position;
                Comment_List selectedComment = commentList.get(selectedPosition);
                String formattedDate = selectedComment.getDate();
                String nickname = selectedComment.getName();
                String content = selectedComment.getContent();
                String commentId = selectedComment.getCommentId(); // commentId를 가져옴
                mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/ReportData/CommentReport");
                String tempReportComment = mDatabaseRef.push().getKey();
                mDatabaseRef.child(tempReportComment).child("ReportId").setValue(tempReportComment);
                mDatabaseRef.child(tempReportComment).child("PostInfo").setValue(postId);
                mDatabaseRef.child(tempReportComment).child("Date").setValue(formattedDate);
                mDatabaseRef.child(tempReportComment).child("commentId").setValue(commentId);
                mDatabaseRef.child(tempReportComment).child("NickName").setValue(nickname);
                mDatabaseRef.child(tempReportComment).child("Content").setValue(content);
                Toast.makeText(Select_Board.this,"신고가 완료되었습니다.",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btnCommentDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedPosition = position;
                Comment_List selectedComment = commentList.get(selectedPosition);
                String deleteComment = selectedComment.getCommentId();
                DatabaseReference deleteCommentRef = FirebaseDatabase.getInstance().getReference("self_life/BoardData/" + postId + "/comment/" + deleteComment);
                Toast.makeText(Select_Board.this,"댓글을 삭제하였습니다.",Toast.LENGTH_SHORT).show();
                deleteCommentRef.removeValue();
                dialog.dismiss(); // 다이얼로그 닫기
                Intent intent = new Intent(Select_Board.this, Select_Board.class);
                intent.putExtra("postId", postId);
                startActivity(intent);
            }
        });

        dialog.show();
    }
}

