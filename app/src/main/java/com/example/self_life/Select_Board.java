package com.example.self_life;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    private LinearLayout baseLl,commentLl,uploadComment;
    private FrameLayout selectcomment;
    private String uid, commentname;


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
        String postId = intent.getStringExtra("postId");
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

        adapter = new CommentAdapter(this, commentList);
        recyclerView.setAdapter(adapter);
        /*
        storageRef.child("1906053_.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // 성공적으로 다운로드 링크를 가져왔을 때 처리
                String imageUrl = uri.toString();

                // Glide 또는 다른 이미지 로딩 라이브러리를 사용하여 이미지 로드
                Glide.with(Select_Board.this).load(imageUrl).into(picture1);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // 다운로드 링크를 가져오지 못했을 때 처리
                // 예외 처리를 추가하여 오류를 확인할 수 있습니다.
                Toast.makeText(Select_Board.this,"오류",Toast.LENGTH_SHORT).show();
            }
        });

         */


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
        mDatabaseRef.child("comment").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                commentList.clear();
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    String nickname = snapshot.child("nickname").getValue(String.class);
                    long time = snapshot.child("time").getValue(Long.class);
                    Date currentDate = new Date(time);
                    // SimpleDateFormat을 사용하여 연월일 형식으로 변환
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = sdf.format(currentDate);
                    String commentId = "";
                    //Toast.makeText(Select_Board.this, snapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
                            //snapshot.getValue(String.class);
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
    }
}

