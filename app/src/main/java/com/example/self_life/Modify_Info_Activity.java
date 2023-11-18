package com.example.self_life;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

public class Modify_Info_Activity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private TextView userEmail, userJoin, changeNickNameTv, changePwdTv, changePhoneNumberTv;
    private LinearLayout changeNickNameLayout, changePwdLayout,changePhoneNumberLayout,backFl, changeNickName, changePwd,changePhoneNumber,changeName;
    private EditText userName,changeNickNameEt,changePhoneNumberEt;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_info);
        bottomNavigationView = findViewById(R.id.bottomNavigationView5);
        mFirebaseAuth = FirebaseAuth.getInstance();
        userEmail = findViewById(R.id.userEmail);
        userJoin = findViewById(R.id.userJoin);
        userName = findViewById(R.id.userName);
        changeNickNameTv = findViewById(R.id.changeNickNameTv);
        changeNickNameEt = findViewById(R.id.changeNickNameEt);
        changeNickName = findViewById(R.id.changeNickName);
        changeName = findViewById(R.id.changeName);
        changePwdTv = findViewById(R.id.changePwdTv);
        changePwd = findViewById(R.id.changePwd);
        changePhoneNumberTv = findViewById(R.id.changePhoneNumberTv);
        changePhoneNumberEt = findViewById(R.id.changePhoneNumberEt);
        changePhoneNumber = findViewById(R.id.changePhoneNumber);
        changeNickNameLayout = findViewById(R.id.changeNickNameLayout);
        changePwdLayout = findViewById(R.id.changePwdLayout);
        changePhoneNumberLayout = findViewById(R.id.changePhoneNumberLayout);
        backFl = findViewById(R.id.backFl);
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        userId = firebaseUser.getUid();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/UserData/"+userId);

        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userNametemp = dataSnapshot.child("UserInfo").child("userName").getValue(String.class);
                String userEmailtemp = dataSnapshot.child("UserInfo").child("userEmail").getValue(String.class);
                long userJointemp = dataSnapshot.child("UserInfo").child("CreateTime").getValue(Long.class);
                Date currentDate = new Date(userJointemp);
                // SimpleDateFormat을 사용하여 연월일 형식으로 변환
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = sdf.format(currentDate);
                userEmail.setText(userEmailtemp);
                userJoin.setText(formattedDate);
                userName.setText(userNametemp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_cal) {
                    Intent intent = new Intent(Modify_Info_Activity.this, Calendar_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_chart) {
                    Intent intent = new Intent(Modify_Info_Activity.this, Chart_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_lifeitem) {
                    Intent intent = new Intent(Modify_Info_Activity.this, LifeItem_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_board) {
                    Intent intent = new Intent(Modify_Info_Activity.this, Board_Activity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        changeNickNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backFl.setVisibility(View.VISIBLE);
                changeNickNameLayout.setVisibility(View.VISIBLE);
            }
        });
        changePwdTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backFl.setVisibility(View.VISIBLE);
                changePwdLayout.setVisibility(View.VISIBLE);
            }
        });
        changePhoneNumberTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backFl.setVisibility(View.VISIBLE);
                changePhoneNumberLayout.setVisibility(View.VISIBLE);
            }
        });
        backFl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backFl.setVisibility(View.GONE);
                changeNickNameLayout.setVisibility(View.GONE);
                changePwdLayout.setVisibility(View.GONE);
                changePhoneNumberLayout.setVisibility(View.GONE);
            }
        });
        changeNickNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        changePwdLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        changePhoneNumberLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        changeNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference nicknameRef = FirebaseDatabase.getInstance().getReference("self_life/userAccount");
                nicknameRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            boolean isNicknameDuplicate = false;
                            for (DataSnapshot nicknameSnapshot : dataSnapshot.getChildren()) {
                                String existingNickname = nicknameSnapshot.getValue(String.class);
                                if (existingNickname != null && existingNickname.equals(changeNickNameEt.getText().toString())) {
                                    isNicknameDuplicate = true;
                                    break;
                                }
                            }
                            if(isNicknameDuplicate){
                                Toast.makeText(Modify_Info_Activity.this, "존재하는 닉네임입니다.", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                mDatabaseRef.child("UserInfo").child("userNickName").setValue(changeNickNameEt.getText().toString().trim());
                                Toast.makeText(Modify_Info_Activity.this, "닉네임이 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                String NicknameKey = mDatabaseRef.child("userAccount").push().getKey(); // 새로운 닉네임을 위한 고유한 키 생성
                                nicknameRef.child(NicknameKey).setValue(changeNickNameEt.getText().toString());
                                Intent intent = new Intent(Modify_Info_Activity.this, MyPage_Activity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseRef.child("UserInfo").child("userName").setValue(userName.getText().toString().trim());
                Toast.makeText(Modify_Info_Activity.this, "이름이 변경되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Modify_Info_Activity.this, MyPage_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        changePhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseRef.child("UserInfo").child("userPhoneNumber").setValue(changePhoneNumberEt.getText().toString().trim());
                Toast.makeText(Modify_Info_Activity.this, "휴대폰 번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Modify_Info_Activity.this, MyPage_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        changePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = mFirebaseAuth.getCurrentUser().getEmail();

                if (userEmail != null) {
                    mFirebaseAuth.sendPasswordResetEmail(userEmail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Modify_Info_Activity.this, "비밀번호 변경 이메일이 전송되었습니다.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Modify_Info_Activity.this, "비밀번호 변경 이메일 전송 실패", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
