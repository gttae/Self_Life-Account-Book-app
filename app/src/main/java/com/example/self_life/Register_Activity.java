package com.example.self_life;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    private EditText userName, userEmail, userPwd, userNickname, userPhoneNumber, userDOB;         //회원가입 입력 내용
    private Button signup, checkEmailNickName, checkPwd;         //회원가입, 이메일닉네임 유효성 검사, 비밀번호 검사 버튼
    private Boolean registerPwdAccess,registerIdAccess = false;
    private String tempNickName, tempPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //변수선언
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life");
        userName = findViewById(R.id.eT1Et);
        userEmail = findViewById(R.id.eT4Et);
        userPwd = findViewById(R.id.eT3Et);
        userNickname = findViewById(R.id.eT5Et);
        userPhoneNumber = findViewById(R.id.eT6Et);
        userDOB = findViewById(R.id.eT7Et);
        checkEmailNickName = findViewById(R.id.btn1Btn);
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
                if (isEmptyField(name) || isEmptyField(phoneNumber) || isEmptyField(DOB) || isEmptyField(strEmail) || isEmptyField(strPwd)) {
                    // 입력값이 하나라도 비어있을 경우, 사용자에게 알림
                    Toast.makeText(Register_Activity.this, "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidName(name)) {
                    Toast.makeText(Register_Activity.this, "유효하지 않은 이름입니다.", Toast.LENGTH_SHORT).show();
                    userName.setText("");
                    return;
                }
                // 전화번호와 생년월일 유효성 검사
                if (!isValidPhoneNumber(phoneNumber)) {
                    Toast.makeText(Register_Activity.this, "유효하지 않은 전화번호입니다.", Toast.LENGTH_SHORT).show();
                    userPhoneNumber.setText("");
                    return;
                }

                if (!isValidDOB(DOB)) {
                    Toast.makeText(Register_Activity.this, "유효하지 않은 생년월일입니다.", Toast.LENGTH_SHORT).show();
                    userDOB.setText("");
                    return;
                }

                if (registerPwdAccess && registerIdAccess) {
                    if (tempNickName.equals(nickname) && tempPwd.equals(userPwd.getText().toString().trim())) {
                        //Firebase Auth 진행
                        mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(Register_Activity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                    User_Account account = new User_Account();
                                    long currentTimeMillis = System.currentTimeMillis();
                                    account.setIdToken(firebaseUser.getUid());
                                    account.setEmailId(firebaseUser.getEmail());
                                    account.setCreationDate(currentTimeMillis);
                                    mDatabaseRef.child("UserData").child(account.getIdToken()).child("UserInfo").child("userEmail").setValue(account.getEmailId());
                                    mDatabaseRef.child("UserData").child(account.getIdToken()).child("UserInfo").child("userName").setValue(name);
                                    mDatabaseRef.child("UserData").child(account.getIdToken()).child("UserInfo").child("userPhoneNumber").setValue(phoneNumber);
                                    mDatabaseRef.child("UserData").child(account.getIdToken()).child("UserInfo").child("userNickName").setValue(nickname);
                                    String NicknameKey = mDatabaseRef.child("userAccount").push().getKey(); // 새로운 닉네임을 위한 고유한 키 생성
                                    mDatabaseRef.child("userAccount").child(NicknameKey).setValue(nickname);
                                    mDatabaseRef.child("UserData").child(account.getIdToken()).child("UserInfo").child("userDOB").setValue(DOB);
                                    mDatabaseRef.child("UserData").child(account.getIdToken()).child("UserInfo").child("CreateTime").setValue(account.getCreationDate());
                                    Intent intent = new Intent(Register_Activity.this, SignUpSucces_Activity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(Register_Activity.this, "존재하는 이메일입니다.", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    } else {
                        Toast.makeText(Register_Activity.this, "닉네임과 비밀번호를 재확인해주세요.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Register_Activity.this, "이메일/닉네임/비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        checkEmailNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmailNicknameAvailability(userEmail.getText().toString().trim(),userNickname.getText().toString().trim());
            }
        });
        //비밀번호 유효성 검사
        checkPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmptyField(userPwd.getText().toString().trim())){
                    int drawableResourceId = getResources().getIdentifier("wrongtext", "drawable", getPackageName());
                    checkPwd.setBackgroundResource(drawableResourceId);
                    checkPwd.setTextColor(Color.RED);
                    checkPwd.setText("입력된 비밀번호X");
                    userPwd.setBackgroundResource(drawableResourceId);
                    registerPwdAccess = false;
                }
                if (!isValidPwd(userPwd.getText().toString().trim())) {
                    int drawableResourceId = getResources().getIdentifier("wrongtext", "drawable", getPackageName());
                    checkPwd.setBackgroundResource(drawableResourceId);
                    checkPwd.setTextColor(Color.RED);
                    checkPwd.setText("유효하지 않은 비밀번호");
                    userPwd.setBackgroundResource(drawableResourceId);
                    registerPwdAccess = false;
                }
                else {
                    int drawableResourceId = getResources().getIdentifier("righttext", "drawable", getPackageName());
                    checkPwd.setBackgroundResource(drawableResourceId);
                    checkPwd.setTextColor(Color.parseColor("#44b0e8"));
                    drawableResourceId = getResources().getIdentifier("txtcolor", "drawable", getPackageName());
                    userPwd.setBackgroundResource(drawableResourceId);
                    checkPwd.setText("사용 가능한 비밀번호");
                    tempPwd = userPwd.getText().toString().trim();
                    registerPwdAccess = true;
                }
            }
        });
    }

    //입력내용 유효성 검사 메소드
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

        return name.matches("^[a-zA-Z가-힣]{1,10}$");
    }

    private boolean isValidEmail(String email) {

        return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$");
    }

    private boolean isValidPwd(String Pwd) {

        return Pwd.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()+=]).{8,}$");
    }
    //이메일 닉네임 유효성 검사 메소드
    private void checkEmailNicknameAvailability(String email, String nickname) {
        mDatabaseRef.child("userAccount").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!isValidEmail(email)) {
                    int drawableResourceId = getResources().getIdentifier("wrongtext", "drawable", getPackageName());
                    checkEmailNickName.setBackgroundResource(drawableResourceId);
                    checkEmailNickName.setTextColor(Color.RED);
                    checkEmailNickName.setText("유효하지 않는 이메일");
                    userEmail.setBackgroundResource(drawableResourceId);
                    registerIdAccess = false;
                }
                else {
                    int drawableResourceId = getResources().getIdentifier("txtcolor", "drawable", getPackageName());
                    userEmail.setBackgroundResource(drawableResourceId);
                    if (!isValidName(nickname)) {
                        drawableResourceId = getResources().getIdentifier("wrongtext", "drawable", getPackageName());
                        checkEmailNickName.setBackgroundResource(drawableResourceId);
                        checkEmailNickName.setTextColor(Color.RED);
                        checkEmailNickName.setText("유효하지 않는 닉네임");
                        userNickname.setBackgroundResource(drawableResourceId);
                        registerIdAccess = false;

                    }
                    else {
                            if (dataSnapshot.exists()) {
                                boolean isNicknameDuplicate = false;
                                for (DataSnapshot nicknameSnapshot : dataSnapshot.getChildren()) {
                                    String existingNickname = nicknameSnapshot.getValue(String.class);
                                    if (existingNickname != null && existingNickname.equals(nickname)) {
                                        isNicknameDuplicate = true;
                                        break;
                                    }
                            }
                                if (isNicknameDuplicate) {
                                    drawableResourceId = getResources().getIdentifier("wrongtext", "drawable", getPackageName());
                                    checkEmailNickName.setBackgroundResource(drawableResourceId);
                                    checkEmailNickName.setTextColor(Color.RED);
                                    checkEmailNickName.setText("존재하는 닉네임");
                                    userNickname.setBackgroundResource(drawableResourceId);
                                    registerIdAccess = false;

                                } else {
                                    drawableResourceId = getResources().getIdentifier("righttext", "drawable", getPackageName());
                                    checkEmailNickName.setBackgroundResource(drawableResourceId);
                                    checkEmailNickName.setTextColor(Color.parseColor("#44b0e8"));
                                    checkEmailNickName.setText("사용 가능한 닉네임");
                                    drawableResourceId = getResources().getIdentifier("txtcolor", "drawable", getPackageName());
                                    userNickname.setBackgroundResource(drawableResourceId);
                                    tempNickName = nickname;
                                    registerIdAccess = true;
                                }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}