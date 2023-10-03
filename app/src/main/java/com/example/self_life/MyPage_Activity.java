package com.example.self_life;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class MyPage_Activity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private BottomNavigationView bottomNavigationView;
    private Button modifyInfo, secession,appInfo, appcond, question;
    private TextView logout;

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
                finish();
            }
        });
        modifyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPage_Activity.this, Modify_Info_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
