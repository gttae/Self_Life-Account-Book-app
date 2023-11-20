package com.example.self_life;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_Activity extends AppCompatActivity
{
    private FirebaseAuth mFirebaseAuth;             //파이어베이스 인증
    private DatabaseReference mDatabaseRef;
    private EditText UserId, UserPwd;
    private Button Login, SignUp ;
    private TextView findId, findPwd, autotext, wrongEmail, wrongPwd;
    private ImageView autobutton ;
    private int clicknum = 0;
    private boolean auto = false;

    @Override
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_login);
        //변수선언
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/UserData");
        UserId=findViewById(R.id.UserIdTv);
        UserPwd=findViewById(R.id.UserPwdTv);
        Login=findViewById(R.id.btn3Bt);
        autobutton=findViewById(R.id.autologinbtn);
        autotext=findViewById(R.id.autologintv);
        findId=findViewById(R.id.tV7Tv);
        findPwd=findViewById(R.id.tV9Tv);
        SignUp=findViewById(R.id.tV11Tv);
        wrongEmail=findViewById(R.id.wrongIdTv);
        wrongPwd=findViewById(R.id.wrongPwdTv);

        // 자동 로그인 정보 불러오기
        SharedPreferences loginPrefs = getSharedPreferences("login_prefs", MODE_PRIVATE);
        auto = loginPrefs.getBoolean("auto_login", false);
        if (auto) {
            String email = loginPrefs.getString("email", "");
            String password = loginPrefs.getString("password", "");

            // 저장된 이메일과 비밀번호가 있으면 자동으로 로그인 시도
            if (!email.isEmpty() && !password.isEmpty()) {

                loginWithEmailAndPassword(email, password);
            }
        }
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail=UserId.getText().toString();
                String strPwd=UserPwd.getText().toString();

                if ((strEmail.isEmpty() || strPwd.isEmpty())) {
                    // 입력값이 없는 경우 Toast 메시지 출력
                    Toast.makeText(Login_Activity.this, "입력값이 없습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String userEmail = snapshot.child("UserInfo").child("userEmail").getValue(String.class);
                                if(strEmail.equals(userEmail)){
                                    loginWithEmailAndPassword(strEmail, strPwd);
                                    break;
                                }
                                else {
                                    //wrongEmail.setVisibility(View.VISIBLE);
                                    int drawableResourceId = getResources().getIdentifier("backwrong", "drawable", getPackageName());
                                    //UserId.setBackgroundResource(drawableResourceId);
                                    wrongPwd.setVisibility(View.GONE);
                                    drawableResourceId = getResources().getIdentifier("textviewback", "drawable", getPackageName());
                                    UserPwd.setBackgroundResource(drawableResourceId);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        findId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this, findId_Activity.class);
                startActivity(intent);
            }
        });
        findPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this, findPwd_Activity.class);
                startActivity(intent);
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this, Register_Activity.class);
                startActivity(intent);
            }
        });

        autobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicknum++;
                if (clicknum % 2 == 1) {
                    autobutton.setImageResource(R.drawable.autologincheck);
                    auto = true;
                } else {
                    autobutton.setImageResource(R.drawable.autologin);
                    auto = false;
                }
            }
        });
        autotext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicknum++;
                if (clicknum % 2 == 1) {
                    autobutton.setImageResource(R.drawable.autologincheck);
                    auto = true;
                } else {
                    autobutton.setImageResource(R.drawable.autologin);
                    auto = false;
                }
            }
        });
    }
    private void loginWithEmailAndPassword(String email, String password) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login_Activity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (auto) {
                        // 자동 로그인 체크가 되어 있다면 로그인 정보 저장
                        SharedPreferences.Editor editor = getSharedPreferences("login_prefs", MODE_PRIVATE).edit();
                        editor.putBoolean("auto_login", true);
                        editor.putString("email", email);
                        editor.putString("password", password);
                        editor.apply();
                    }
                    Intent intent = new Intent(Login_Activity.this, Chart_Activity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                        // 해당 이메일로 가입된 계정이 없는 경우
                        wrongEmail.setVisibility(View.VISIBLE);
                        int drawableResourceId = getResources().getIdentifier("backwrong", "drawable", getPackageName());
                        UserId.setBackgroundResource(drawableResourceId);
                        wrongPwd.setVisibility(View.GONE);
                        drawableResourceId = getResources().getIdentifier("textviewback", "drawable", getPackageName());
                        UserPwd.setBackgroundResource(drawableResourceId);
                    } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        // 비밀번호가 틀린 경우
                        wrongPwd.setVisibility(View.VISIBLE);
                        int drawableResourceId = getResources().getIdentifier("backwrong", "drawable", getPackageName());
                        UserPwd.setBackgroundResource(drawableResourceId);
                        wrongEmail.setVisibility(View.GONE);
                        drawableResourceId = getResources().getIdentifier("textviewback", "drawable", getPackageName());
                        UserId.setBackgroundResource(drawableResourceId);
                    } else if ((task.getException() instanceof FirebaseAuthInvalidUserException) && (task.getException() instanceof FirebaseAuthInvalidCredentialsException)){
                        // 둘 다 틀린 경우
                        wrongEmail.setVisibility(View.VISIBLE);
                        wrongPwd.setVisibility(View.VISIBLE);
                        int drawableResourceId = getResources().getIdentifier("backwrong", "drawable", getPackageName());
                        UserId.setBackgroundResource(drawableResourceId);
                        UserPwd.setBackgroundResource(drawableResourceId);
                    }
                }
            }
        });
    }
}