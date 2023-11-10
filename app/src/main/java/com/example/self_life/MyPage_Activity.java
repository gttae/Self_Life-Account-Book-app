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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

public class MyPage_Activity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private BottomNavigationView bottomNavigationView;
    private Button modifyInfo, secession,appInfo, appcond, question, reportPost, reportComment;
    private LinearLayout backLayout,droppage,deleteId;
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
        reportPost = findViewById(R.id.reportPost);
        reportComment = findViewById(R.id.reportComment);
        userNickName = findViewById(R.id.userNameTv);
        userName = findViewById(R.id.mypageids);
        userEmail = findViewById(R.id.mypageemails);
        userJoin = findViewById(R.id.mypagejoins);
        backLayout = findViewById(R.id.backFl);
        droppage = findViewById(R.id.lossPage);
        deleteId = findViewById(R.id.deleteId);
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        userId = firebaseUser.getUid();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/UserData/"+userId);

        if(userId.equals("dC9EUCkwqGeqQELJIYHLOEwYJzk2")){
            reportPost.setVisibility(View.VISIBLE);
            reportComment.setVisibility(View.VISIBLE);
        }

        reportPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPage_Activity.this, PostReportList_Activity.class);
                startActivity(intent);
            }
        });

        reportComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPage_Activity.this, CommentReportList_Activity.class);
                startActivity(intent);
            }
        });

        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String userNickNametemp = dataSnapshot.child("UserInfo").child("userNickName").getValue(String.class);
                String userNametemp = dataSnapshot.child("UserInfo").child("userName").getValue(String.class);
                String userEmailtemp = dataSnapshot.child("UserInfo").child("userEmail").getValue(String.class);
                long userJointemp = dataSnapshot.child("UserInfo").child("CreateTime").getValue(Long.class);
                Date currentDate = new Date(userJointemp);
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
        appInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 앱 정보를 표시하는 Dialog
                showAppInfoDialog();
            }
        });

        appcond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 앱 이용약관을 표시하는 Dialog
                showAppConditionsDialog();
            }
        });

        droppage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // 데이터 삭제가 성공한 경우
                                    // Firebase Authentication에서 사용자 삭제
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    if (user != null) {
                                        user.delete()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            // 사용자 삭제가 성공한 경우
                                                            Toast.makeText(MyPage_Activity.this, "회원 탈퇴가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            // 사용자 삭제가 실패한 경우
                                                            Toast.makeText(MyPage_Activity.this, "회원 탈퇴 실패", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                } else {
                                    // 데이터 삭제가 실패한 경우
                                    Toast.makeText(MyPage_Activity.this, "데이터 삭제 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
    private void showAppInfoDialog() {
        // Dialog 레이아웃 설정
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_appinfo, null);

        // Dialog 생성 및 내용 설정
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("앱 정보");
        dialogBuilder.setPositiveButton("확인", null); // 확인 버튼 추가

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    private void showAppConditionsDialog() {
        // Dialog 레이아웃 설정
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_appuse, null);

        // Dialog 생성 및 내용 설정
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("앱 이용약관");
        dialogBuilder.setPositiveButton("확인", null); // 확인 버튼 추가

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

}
