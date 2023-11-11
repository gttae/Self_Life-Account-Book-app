package com.example.self_life;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Question_input_Activity extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mFirebaseAuth;
    private BottomNavigationView bottomNavigationView;
    private ImageView mypageBtn;
    private EditText questionTitle, questionContent;
    private LinearLayout Btn;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna_input);
        bottomNavigationView = findViewById(R.id.bottomNavigationView5);
        mypageBtn = findViewById(R.id.mypageBtn);
        questionTitle = findViewById(R.id.questionTitle);
        questionContent = findViewById(R.id.questionContent);
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        uid = firebaseUser.getUid();
        Btn = findViewById(R.id.Btn1);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_cal) {
                    Intent intent = new Intent(Question_input_Activity.this, Calendar_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_chart) {
                    Intent intent = new Intent(Question_input_Activity.this, Chart_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_lifeitem) {
                    Intent intent = new Intent(Question_input_Activity.this, LifeItem_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_board) {
                    Intent intent = new Intent(Question_input_Activity.this, Board_Activity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        mypageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Question_input_Activity.this, MyPage_Activity.class);
                startActivity(intent);
            }
        });

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/QuestionData");
                String questionId = mDatabaseRef.push().getKey();
                mDatabaseRef.child(questionId).child("UserEmail").setValue(questionTitle.getText().toString());
                mDatabaseRef.child(questionId).child("Question").setValue(questionContent.getText().toString());
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("self_life/UserData/" + uid + "/UserInfo");
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String userName = snapshot.child("userName").getValue(String.class);
                        mDatabaseRef.child(questionId).child("UserName").setValue(userName);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Toast.makeText(Question_input_Activity.this,"문의가 전달되었습니다.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Question_input_Activity.this, MyPage_Activity.class);
                startActivity(intent);
            }
        });
    }
}