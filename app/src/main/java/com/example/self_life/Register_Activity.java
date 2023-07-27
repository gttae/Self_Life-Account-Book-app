package com.example.self_life;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register_Activity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;             //파이어베이스 인증
    private DatabaseReference mDatabaseRef;         //실시간 데이터 베이스
    private EditText userName, userEmail, userPwd, userNickname, userPhoneNumber, userDOB;
    private Button signup, checkIdNickName, checkPwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life");
        userName = findViewById(R.id.eT1Et);
        userEmail = findViewById(R.id.eT4Et);
        userPwd = findViewById(R.id.eT3Et);
        userNickname = findViewById(R.id.eT5Et);
        userPhoneNumber = findViewById(R.id.eT6Et);
        userDOB = findViewById(R.id.eT7Et);
        checkIdNickName = findViewById(R.id.btn1Btn);
        checkPwd = findViewById(R.id.bt2Btn);
        signup = findViewById(R.id.bt3Btn);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 처리 시작
                String strEmail = userEmail.getText().toString().trim();
                String strPwd = userPwd.getText().toString().trim();
                String name = userName.getText().toString().trim();
                String nickname = userNickname.getText().toString().trim();
                String phoneNumber = userPhoneNumber.getText().toString().trim();
                String DOB = userDOB.getText().toString().trim();
                // 이메일, 비밀번호, 이름, 전화번호가 모두 입력되었는지 확인
                if (isEmptyField(name) || isEmptyField(nickname) || isEmptyField(phoneNumber) || isEmptyField(DOB)) {
                    // 입력값이 하나라도 비어있을 경우, 사용자에게 알림
                    Toast.makeText(Register_Activity.this, "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidEmail(strEmail)) {
                    Toast.makeText(Register_Activity.this, "유효하지 않은 이메일입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidName(name)) {
                    Toast.makeText(Register_Activity.this, "유효하지 않은 이름입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 전화번호와 생년월일 유효성 검사
                if (!isValidPhoneNumber(phoneNumber)) {
                    Toast.makeText(Register_Activity.this, "유효하지 않은 전화번호입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidDOB(DOB)) {
                    Toast.makeText(Register_Activity.this, "유효하지 않은 생년월일입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }


                //Firebase Auth 진행
                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(Register_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            User_Account account = new User_Account();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(strPwd);

                            mDatabaseRef.child("UserAccount1").child("userId").setValue(account);
                            mDatabaseRef.child("UserAccount1").child("Name").setValue(name);
                            mDatabaseRef.child("UserAccount1").child("phoneNumber").setValue(phoneNumber);
                            mDatabaseRef.child("UserAccount1").child("NickName").setValue(nickname);
                            mDatabaseRef.child("UserAccount1").child("DOB").setValue(DOB);
                            Toast.makeText(Register_Activity.this, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(Register_Activity.this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        checkIdNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkNicknameAvailability(userEmail.getText().toString().trim(),userNickname.getText().toString().trim());
            }

        });


        checkPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isValidPwd(userPwd.getText().toString().trim())) {
                    Toast.makeText(Register_Activity.this, "유효하지 않은 비밀번호입니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Register_Activity.this, "사용 가능한 비밀번호입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isEmptyField(String fieldValue) {
        return fieldValue.isEmpty();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {

        return phoneNumber.matches("^\\d{11}$");
    }

    private boolean isValidDOB(String dob) {

        return dob.matches("^\\d{8}$");
    }

    private boolean isValidName(String name) {

        return name.matches("^[a-zA-Z]{2,}$");
    }

    private boolean isValidEmail(String email) {

        return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$");
    }

    private boolean isValidPwd(String Pwd) {

        return Pwd.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()+=]).{8,}$");
    }

    private void checkNicknameAvailability(String email, String nickname) {
        mDatabaseRef.child("UserAccount1").child("NickName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (isEmptyField(email) || isEmptyField(nickname)) {
                    Toast.makeText(Register_Activity.this, "아이디 또는 닉네임이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(String.valueOf(task.getResult().getValue()).contains(userNickname.getText().toString())) {
                        Toast.makeText(Register_Activity.this, "이미 사용 중인 닉네임입니다.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(Register_Activity.this, "사용 가능한 닉네임입니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}

