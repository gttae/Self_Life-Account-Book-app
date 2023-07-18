package com.example.self_life;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login_Activity extends AppCompatActivity
{

    private FirebaseAuth mFirebaseAuth;             //파이어베이스 인증
    private DatabaseReference mDatabaseRef;         //실시간 데이터 베이스
    private EditText UserId, UserPwd, SignUp;
    private Button Login;

    @Override
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life");

        UserId=findViewById(R.id.UserIdTv);
        UserPwd=findViewById(R.id.UserPwdTv);
        Button Login=findViewById(R.id.btn3Bt);
        Login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //로그인 요청
                String strEmail=UserId.getText().toString();
                String strPwd=UserPwd.getText().toString();
                mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(Login_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //로그인성공
                            //Intent intent = new Intent(Login_Activity.this, "이동할 클래스".class)
                            //startActivity(intent);
                            //finish();//현재 엑티비티 파괴
                            Toast.makeText(Login_Activity.this, "성공입니다.", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(Login_Activity.this, "회원정보가 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        Button tV11Tv=findViewById(R.id.tV11Tv);
        tV11Tv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                //회원가입 화면으로 "화면이동"
                Intent intent = new Intent(Login_Activity.this, Register_Activity.class);
                startActivity(intent);
            }

        });



    }
}