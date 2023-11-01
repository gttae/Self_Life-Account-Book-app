package com.example.self_life;

import static com.example.self_life.YearMonth_Value.getCurrentMonth;
import static com.example.self_life.YearMonth_Value.getCurrentYear;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
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

import java.util.Calendar;

public class Chart_Activity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ImageView mypageBtn;
    private FrameLayout dataInput;
    private LinearLayout modifyFund;
    private TextView leftPage,rightPage,chartMonth,userUseFund,userPlanFund;
    private Button recommendChart;
    private int segmentCount = 11;
    private float[] segmentValues = new float[segmentCount];
    private float[] usedValues = new float[6];
    private float totalsegmentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        bottomNavigationView = findViewById(R.id.bottomNavigationView5);
        mypageBtn = findViewById(R.id.mypageBtn);
        dataInput = findViewById(R.id.chartPlus);
        recommendChart = findViewById(R.id.button1);
        leftPage = findViewById(R.id.leftPage);
        chartMonth = findViewById(R.id.chartMonth);
        rightPage = findViewById(R.id.rightPage);
        userUseFund = findViewById(R.id.userUseFund);
        userPlanFund = findViewById(R.id.userPlanFund);
        modifyFund = findViewById(R.id.button2);
        chartMonth.setText(getCurrentYear()+"년 "+getCurrentMonth()+"월");

        recommendChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Chart_Activity.this, Recommend_Model_Activity.class);
                startActivity(intent);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_cal) {
                    Intent intent = new Intent(Chart_Activity.this, Calendar_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_chart) {
                    Intent intent = new Intent(Chart_Activity.this, Chart_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_lifeitem) {
                    Intent intent = new Intent(Chart_Activity.this, LifeItem_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_board) {
                    Intent intent = new Intent(Chart_Activity.this, Board_Activity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        mypageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Chart_Activity.this, MyPage_Activity.class);
                startActivity(intent);
            }
        });
        dataInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Chart_Activity.this, Input_FundData_Activity.class);
                startActivity(intent);
            }
        });

        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        String userId;
        userId = firebaseUser.getUid();
        String monthString = getCurrentMonth() + "월";
        String monthexpense = monthString + "지출";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference fundDataRef = database.getReference("self_life/UserData/"+userId+"/FundData/"+monthexpense);

        fundDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // 배열 초기화
                for (int i = 0; i < segmentCount; i++) {
                    segmentValues[i] = 0;
                }

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String fundDivision = snapshot.child("FundDivision").getValue(String.class);
                    float price = Float.valueOf(snapshot.child("Price").getValue(String.class));
                    String category = snapshot.child("Category").getValue(String.class);

                    // fundDivision을 기반으로 해당 세그먼트에 가격 할당
                    int segmentIndex = getSegmentIndexByDivision(fundDivision);
                    if (segmentIndex != -1) {
                        if ("고정(계획)".equals(category)) {
                            segmentValues[segmentIndex] += price;
                        }
                    }
                }

                for (int i = 0; i < segmentValues.length; i++) {
                    totalsegmentValues += segmentValues[i];
                }
                weekendFund(totalsegmentValues);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 여기서 오류 처리
            }
        });
    }

    private int getSegmentIndexByDivision(String fundDivision) {
        // fundDivision을 올바른 세그먼트 인덱스로 매핑합니다.
        switch (fundDivision) {
            case "식비":
                return 0;
            case "교통/차량":
                return 1;
            case "문화생활":
                return 2;
            case "마트":
                return 3;
            case "패션/미용":
                return 4;
            case "생활용품":
                return 5;
            case "주거/통신":
                return 6;
            case "건강":
                return 7;
            case "교육":
                return 8;
            case "경조사/회비":
                return 9;
            default:
                return 10;
        }
    }
    private void weekendFund(float fund){
        Calendar calendar = Calendar.getInstance();

        // 현재 월의 시작일과 끝일 설정
        //calendar.set(Calendar.DAY_OF_MONTH, 1);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);
        int currentWeek = calendar.get(Calendar.WEEK_OF_MONTH);

        // 원하는 연도와 월로 설정
        calendar.set(Calendar.YEAR, currentYear);
        calendar.set(Calendar.MONTH, currentMonth);

        // 마지막 날짜를 구하기 위해 월을 다음 월로 설정하고 1일 전으로 이동
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        int lastDay = calendar.get(Calendar.DAY_OF_MONTH);

        // 초기 temp 값
        float temp = fund;
        float dailyfund = temp / lastDay;

        // 주차별 한 주의 균등한 값의 합을 저장할 배열
        float[] weeklyDayCounts = {0,0,0,0,0,0}; // 주차에 포함된 요일 수

        for (int i = 1; i <= lastDay; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, i);
            int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
            weeklyDayCounts[weekOfMonth - 1]++;
        }

        float[] weeklyDayCounts2 = {0,0,0,0,0,0}; // 주차에 마지막 날의 일
        weeklyDayCounts2[0] = weeklyDayCounts[0];
        weeklyDayCounts2[1] = weeklyDayCounts2[0]+7;
        weeklyDayCounts2[2] = weeklyDayCounts2[1]+7;
        weeklyDayCounts2[3] = weeklyDayCounts2[2]+7;
        weeklyDayCounts2[4] = weeklyDayCounts2[3]+7;
        if(weeklyDayCounts2[4] - 31 < 0 ) {
            weeklyDayCounts2[4] = weeklyDayCounts2[3]+7;
            weeklyDayCounts2[5] = lastDay;
        }
        else{
            weeklyDayCounts2[4] = lastDay;
        }
        for (int i =0; i<=weeklyDayCounts.length-1; i++){
            weeklyDayCounts[i] = weeklyDayCounts[i] * dailyfund;
        }
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        String userId;
        userId = firebaseUser.getUid();
        String monthString = getCurrentMonth() + "월";
        String monthexpense = monthString + "지출";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference fundDataRef = database.getReference("self_life/UserData/"+userId+"/FundData/"+monthexpense);
        fundDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // 배열 초기화
                for (int i = 0; i < segmentCount; i++) {
                    segmentValues[i] = 0;
                }
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String fundDivision = snapshot.child("FundDivision").getValue(String.class);
                    float price = Float.valueOf(snapshot.child("Price").getValue(String.class));
                    int day = Integer.valueOf(snapshot.child("Day").getValue(String.class));
                    String category = snapshot.child("Category").getValue(String.class);
                    if ("고정(실사용)".equals(category) || "유동".equals(category)) {
                        if(day <= weeklyDayCounts2[0]){
                            usedValues[0] += price;
                        } else if (day <= weeklyDayCounts2[1]) {
                            usedValues[1] += price;
                        } else if (day <= weeklyDayCounts2[2]) {
                            usedValues[2] += price;
                        } else if (day <= weeklyDayCounts2[3]) {
                            usedValues[3] += price;
                        } else if (day <= weeklyDayCounts2[4]) {
                            usedValues[4] += price;
                        } else if (day <= weeklyDayCounts2[5]) {
                            usedValues[5] += price;
                        }
                    }
                }
                float limitWeek = weeklyDayCounts[currentWeek-1] - usedValues[currentWeek-1];

                userUseFund.setText("주간동안 ￦"+String.format("%.0f",usedValues[currentWeek-1])+"원 사용하셨습니다.");
                userPlanFund.setText("남은 기간동안 ￦"+String.format("%.0f",limitWeek)+"원을 사용하셔야합니다.");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 여기서 오류 처리
            }
        });

        float limitWeek = weeklyDayCounts[currentWeek-1] - usedValues[currentWeek-1];
        userUseFund.setText(String.format("%.0f",limitWeek));
    }
}
