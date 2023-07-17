package com.example.self_life;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.self_life.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_Activity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;             //파이어베이스 인증
    private DatabaseReference mDatabaseRef;         //실시간 데이터 베이스
    private EditText UserIdTV, UserPwdTV, SignUp;
    private Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("kkk");

        UserIdTV=findViewById(R.id.UserIdTv);
        UserPwdTV=findViewById(R.id.UserPwdTv);
        Login=findViewById(R.id.btn3Bt);

        Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //회원가입 처리 시작
                String strEmail = UserIdTV.getText().toString();
                String strPwd = UserPwdTV.getText().toString();

                //Firebase Auth 진행
                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(Register_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            com.example.self_life.User_Account account = new com.example.self_life.User_Account();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(strPwd);

                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);
                            Toast.makeText(Register_Activity.this, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT);
                        }else{
                            Toast.makeText(Register_Activity.this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT);
                        }
                    }
                });
            }
        });

    }
}