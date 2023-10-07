package com.example.self_life;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Select_Board extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ImageView mypageBtn;
    private TextView postTitleTextView, postWriterTextView, postCategoryTextView, postTimeTextView, postContentTextView;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board2);
        bottomNavigationView = findViewById(R.id.bottomNavigationView5);
        mypageBtn = findViewById(R.id.mypageBtn);
        postTitleTextView = findViewById(R.id.BoardTitle);
        postWriterTextView = findViewById(R.id.NameTv);
        postCategoryTextView = findViewById(R.id.BoardCategory);
        postTimeTextView = findViewById(R.id.DateTv);
        postContentTextView = findViewById(R.id.BoardTv);
        Intent intent = getIntent();
        String postId= intent.getStringExtra("postId");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/BoardData/"+postId);
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String title = dataSnapshot.child("title").getValue(String.class);
                    String writer = dataSnapshot.child("writer").getValue(String.class);
                    String category = dataSnapshot.child("category").getValue(String.class);
                    String content = dataSnapshot.child("content").getValue(String.class);
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
        }
    }
