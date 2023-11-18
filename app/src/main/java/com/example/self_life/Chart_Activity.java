package com.example.self_life;

import static com.example.self_life.YearMonth_Value.MonthValue;
import static com.example.self_life.YearMonth_Value.YearValue;
import static com.example.self_life.YearMonth_Value.getCurrentMonth;
import static com.example.self_life.YearMonth_Value.getCurrentYear;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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
    private TextView[] expensePlan = new TextView[11];
    private Integer[] expensePlanId = {R.id.expensePlan1,R.id.expensePlan2,R.id.expensePlan3,R.id.expensePlan4,R.id.expensePlan5,R.id.expensePlan6,R.id.expensePlan7,R.id.expensePlan8,R.id.expensePlan9,R.id.expensePlan10,R.id.expensePlan11};
    private String[] expenseStr = {"식비", "교통/차량", "문화생활", "마트", "패션/미용", "생활용품", "주거/통신", "건강", "교육", "경조사/회비", "기타"};
    private int segmentCount = 11, i;
    private int monthValue = getCurrentMonth();
    private int yearValue = getCurrentYear();
    private float[] segmentValues = new float[segmentCount];

    private float[] usedValues = new float[6];
    private float limitWeek = 0.0F;
    private float totalsegmentValues = 0.0F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        bottomNavigationView = findViewById(R.id.bottomNavigationView5);
        mypageBtn = findViewById(R.id.mypageBtn);
        dataInput = findViewById(R.id.chartPlus);
        leftPage = findViewById(R.id.leftPage);
        chartMonth = findViewById(R.id.chartMonth);
        rightPage = findViewById(R.id.rightPage);
        userUseFund = findViewById(R.id.userUseFund);
        userPlanFund = findViewById(R.id.userPlanFund);
        modifyFund = findViewById(R.id.button2);

        for (i=0; i < expensePlan.length;i++) {
            expensePlan[i] = findViewById(expensePlanId[i]);
        }
        chartMonth.setText(YearValue+"년 "+MonthValue+"월");


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_cal) {
                    Intent intent = new Intent(Chart_Activity.this, Calendar_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_chart) {
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
        leftPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MonthValue--;
                if(MonthValue < 1){
                    YearValue--;
                    MonthValue = 12;
                }
                //chartMonth.setText(yearValue+"년 "+ MonthValue+"월");
                expenseOfPlan(MonthValue);
                //MonthValue = monthValue;
                Intent intent = new Intent(Chart_Activity.this, Chart_Activity.class);
                startActivity(intent);
            }
        });
        rightPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MonthValue++;
                if(MonthValue > 12){
                    YearValue++;
                    MonthValue = 1;
                }
                //chartMonth.setText(yearValue+"년 "+ MonthValue+"월");
                expenseOfPlan(MonthValue);
                //MonthValue = monthValue;
                Intent intent = new Intent(Chart_Activity.this, Chart_Activity.class);
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

        modifyFund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Chart_Activity.this, Modify_FundList_Activity.class);
                startActivity(intent);
            }
        });

        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        String userId;
        userId = firebaseUser.getUid();
        DatabaseReference fixExpenseFundRef = FirebaseDatabase.getInstance().getReference("self_life/UserData/"+userId+"/FundData/고정지출");
        fixExpenseFundRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if(snapshot.child("Month").getValue(String.class).equals(String.valueOf(getCurrentMonth()))){

                        } else {
                            DatabaseReference fundTempRef = snapshot.getRef();
                            fundTempRef.child("Month").setValue(String.valueOf(getCurrentMonth()));
                            String FundId = snapshot.child("FundId").getValue(String.class);
                            String year = snapshot.child("Year").getValue(String.class);
                            String day = snapshot.child("Day").getValue(String.class);
                            String fundDivision = snapshot.child("FundDivision").getValue(String.class);
                            String price = snapshot.child("Price").getValue(String.class);
                            String memo = snapshot.child("Description").getValue(String.class);
                            String category = "실사용";

                            DatabaseReference fundRef = FirebaseDatabase.getInstance().getReference("self_life/UserData/"+userId+"/FundData/"+getCurrentMonth()+"월지출");
                            String fundKey = fundRef.push().getKey();
                            fundRef.child(fundKey).child("FundId").setValue(FundId);
                            fundRef.child(fundKey).child("Year").setValue(year);
                            fundRef.child(fundKey).child("Month").setValue(String.valueOf(getCurrentMonth()));
                            fundRef.child(fundKey).child("Day").setValue(day);
                            fundRef.child(fundKey).child("FundDivision").setValue(fundDivision);
                            fundRef.child(fundKey).child("Price").setValue(price);
                            fundRef.child(fundKey).child("Description").setValue(memo);
                            fundRef.child(fundKey).child("Category").setValue(category);
                        }
                    }
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference fixIncomeFundRef = FirebaseDatabase.getInstance().getReference("self_life/UserData/"+userId+"/FundData/고정수입");
        fixIncomeFundRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if(snapshot.child("Month").getValue(String.class).equals(String.valueOf(getCurrentMonth()))){

                        } else {
                            DatabaseReference fundTempRef = snapshot.getRef();
                            fundTempRef.child("Month").setValue(String.valueOf(getCurrentMonth()));
                            String FundId = snapshot.child("FundId").getValue(String.class);
                            String year = snapshot.child("Year").getValue(String.class);
                            String day = snapshot.child("Day").getValue(String.class);
                            String fundDivision = snapshot.child("FundDivision").getValue(String.class);
                            String price = snapshot.child("Price").getValue(String.class);
                            String memo = snapshot.child("Description").getValue(String.class);
                            String category = "실수령";

                            DatabaseReference fundRef = FirebaseDatabase.getInstance().getReference("self_life/UserData/"+userId+"/FundData/"+getCurrentMonth()+"월수입");
                            String fundKey = fundRef.push().getKey();
                            fundRef.child(fundKey).child("FundId").setValue(FundId);
                            fundRef.child(fundKey).child("Year").setValue(year);
                            fundRef.child(fundKey).child("Month").setValue(String.valueOf(getCurrentMonth()));
                            fundRef.child(fundKey).child("Day").setValue(day);
                            fundRef.child(fundKey).child("FundDivision").setValue(fundDivision);
                            fundRef.child(fundKey).child("Price").setValue(price);
                            fundRef.child(fundKey).child("Description").setValue(memo);
                            fundRef.child(fundKey).child("Category").setValue(category);
                        }
                    }
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        monthChart(monthValue);
        expenseOfPlan(MonthValue);
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

    private void monthChart(int monthValue) {
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        String userId;
        userId = firebaseUser.getUid();
        String monthString = monthValue + "월";
        int monthValue2 = monthValue;
        String monthexpense = monthString + "지출";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference fundDataRef = database.getReference("self_life/UserData/"+userId+"/FundData/"+monthexpense);

        fundDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
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
                            if ("계 획".equals(category)) {
                                segmentValues[segmentIndex] += price;
                            }
                        }
                    }

                    for (int i = 0; i < segmentValues.length; i++) {
                        totalsegmentValues += segmentValues[i];
                    }
                    weekendFund(totalsegmentValues);
                }
                else {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 여기서 오류 처리
            }
        });
    }

    private void weekendFund(float fund){
        Calendar calendar = Calendar.getInstance();

        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);
        int currentWeek = calendar.get(Calendar.WEEK_OF_MONTH);

        calendar.set(Calendar.YEAR, currentYear);
        calendar.set(Calendar.MONTH, currentMonth);

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        int lastDay = calendar.get(Calendar.DAY_OF_MONTH);

        float temp = fund;
        float dailyfund = temp / lastDay;

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
                        if ("실사용".equals(category)) {
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
                    limitWeek = weeklyDayCounts[currentWeek-1] - usedValues[currentWeek-1];
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

    private void expenseOfPlan(int monthValue){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        String userId;
        userId = firebaseUser.getUid();
        String monthString = monthValue + "월";
        String monthexpense = monthString + "지출";
        DatabaseReference fundDataRef = database.getReference("self_life/UserData/"+userId+"/FundData/"+monthexpense);
        fundDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer[] totalValue = new Integer[11];
                Integer[] usedValue = new Integer[11];

                    // 배열 초기화
                for (i = 0; i < expensePlan.length; i++) {
                    totalValue[i] = 0;
                    usedValue[i] = 0;

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String category = snapshot.child("Category").getValue(String.class);
                        if (category.equals("계 획")) {
                            String fundDivision = snapshot.child("FundDivision").getValue(String.class);
                            if (fundDivision.equals(expenseStr[i])) {
                                int price = Integer.valueOf(snapshot.child("Price").getValue(String.class));
                                totalValue[i] += price;
                            }
                        } else if ( category.equals("실사용")) {
                            String fundDivision = snapshot.child("FundDivision").getValue(String.class);
                            if (fundDivision.equals(expenseStr[i])) {
                                int price = Integer.valueOf(snapshot.child("Price").getValue(String.class));
                                usedValue[i] += price;
                            }
                        }
                    }
                    int tempValue = totalValue[i] - usedValue[i];
                    expensePlan[i].setText(expenseStr[i] + " : " + usedValue[i] + " / " + totalValue[i] + " || " + "남은금액 : " + tempValue);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 여기서 오류 처리
            }
        });
    }

}
