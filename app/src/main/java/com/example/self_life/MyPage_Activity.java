package com.example.self_life;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyPage_Activity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private BottomNavigationView bottomNavigationView;
    private Button modifyInfo, secession,appInfo, appcond, question;
    private LinearLayout backLayout,droppage;
    private TextView logout, userNickName, userName, userEmail, userJoin;
    private String userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        bottomNavigationView = findViewById(R.id.bottomNavigationView5);
        logout = findViewById(R.id.logoutTv);
        modifyInfo = findViewById(R.id.fix);
        secession = findViewById(R.id.out);
        question = findViewById(R.id.question);
        appInfo = findViewById(R.id.information);
        appcond = findViewById(R.id.conditions);
        userNickName = findViewById(R.id.userNameTv);
        userName = findViewById(R.id.mypageids);
        userEmail = findViewById(R.id.mypageemails);
        userJoin = findViewById(R.id.mypagejoins);
        backLayout = findViewById(R.id.backFl);
        droppage = findViewById(R.id.lossPage);
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        userId = firebaseUser.getUid();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/UserData/"+userId);

        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String userNickNametemp = dataSnapshot.child("UserInfo").child("userNickName").getValue(String.class);
                String userNametemp = dataSnapshot.child("UserInfo").child("userName").getValue(String.class);
                String userEmailtemp = dataSnapshot.child("UserInfo").child("userEmail").getValue(String.class);
                long userJointemp = dataSnapshot.child("UserInfo").child("CreateTime").getValue(Long.class);
                Date currentDate = new Date(userJointemp);
                // SimpleDateFormat을 사용하여 연월일 형식으로 변환
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = sdf.format(currentDate);
                userNickName.setText(userNickNametemp);
                userName.setText(userNametemp);
                userEmail.setText(userEmailtemp);
                userJoin.setText(formattedDate);
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
                    Intent intent = new Intent(MyPage_Activity.this, Calendar_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_chart) {
                    Intent intent = new Intent(MyPage_Activity.this, Chart_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_lifeitem) {
                    Intent intent = new Intent(MyPage_Activity.this, LifeItem_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_board) {
                    Intent intent = new Intent(MyPage_Activity.this, Board_Activity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFirebaseAuth.getInstance().signOut();
                // 자동 로그인 설정 해제
                SharedPreferences.Editor editor = getSharedPreferences("login_prefs", MODE_PRIVATE).edit();
                editor.putBoolean("auto_login", false);
                editor.remove("email");
                editor.remove("password");
                editor.apply();
                Intent intent = new Intent(MyPage_Activity.this, Login_Activity.class);
                startActivity(intent);
            }
        });

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPage_Activity.this, Question_Activity.class);
                startActivity(intent);
            }
        });

        modifyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPage_Activity.this, Modify_Info_Activity.class);
                startActivity(intent);
            }
        });

        secession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backLayout.setVisibility(View.VISIBLE);
                droppage.setVisibility(View.VISIBLE);
            }
        });

        backLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(droppage.getVisibility() == View.VISIBLE){
                    backLayout.setVisibility(View.GONE);
                    droppage.setVisibility(View.GONE);
                }
                return false;
            }
        });

        droppage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

}
