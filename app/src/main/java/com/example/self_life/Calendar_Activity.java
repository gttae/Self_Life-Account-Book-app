package com.example.self_life;

import static com.example.self_life.YearMonth_Value.getCurrentMonth;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Calendar_Activity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mFirebaseAuth;
    private RecyclerView smallRecyclerView, bigRecyclerView;
    private List<Fund_List> fundList = new ArrayList<>();
    private List<Fund_List> bigfundList = new ArrayList<>();
    private fundAdapter adapter,bigadapter;
    private Calendar calendar;
    private TextView smallExpenseValue,smallIncomeValue,bigExpenseValue,bigIncomeValue;
    private ImageView mypageBtn;
    private String uid="",kind="지출",tempFundId="";
    private LinearLayout baseLl,smallExpense,smallIncome,bigExpense,bigIncome,calendarPlusLl;
    private CalendarView calendarView;
    private FrameLayout calendarPlus, calendarPlusLayout;
    private int selectYear,selectMonth,selectDay,usedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        bottomNavigationView = findViewById(R.id.bottomNavigationView5);
        mypageBtn = findViewById(R.id.mypageBtn);
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        uid = firebaseUser.getUid();
        baseLl = findViewById(R.id.baseLl);
        calendarPlus = findViewById(R.id.calendarPlus);
        calendarPlusLayout = findViewById(R.id.calendarPlusLayout);
        calendarView = findViewById(R.id.calendar);
        smallExpense = findViewById(R.id.smallExpense);
        smallIncome = findViewById(R.id.smallIncome);
        smallExpenseValue = findViewById(R.id.smallExpenseValue);
        smallIncomeValue = findViewById(R.id.smallIncomeValue);
        smallRecyclerView = findViewById(R.id.DayFundList);
        bigExpense = findViewById(R.id.bigExpense);
        bigIncome = findViewById(R.id.bigIncome);
        bigExpenseValue = findViewById(R.id.bigExpenseValue);
        bigIncomeValue = findViewById(R.id.bigIncomeValue);
        bigRecyclerView = findViewById(R.id.BigFundList);
        calendarPlusLl = findViewById(R.id.calendarPlusLl);
        calendar = Calendar.getInstance();
        usedValue = 0;
        int dayOfMonth2 = calendar.get(Calendar.DAY_OF_MONTH);
        selectMonth = getCurrentMonth();
        selectDay =dayOfMonth2;
        smallRecyclerView.setHasFixedSize(true);
        smallRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bigRecyclerView.setHasFixedSize(true);
        bigRecyclerView.setLayoutManager(new LinearLayoutManager(this));




        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_cal) {
                    Intent intent = new Intent(Calendar_Activity.this, Calendar_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_chart) {
                    Intent intent = new Intent(Calendar_Activity.this, Chart_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_lifeitem) {
                    Intent intent = new Intent(Calendar_Activity.this, LifeItem_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_board) {
                    Intent intent = new Intent(Calendar_Activity.this, Board_Activity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

    mypageBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Calendar_Activity.this, MyPage_Activity.class);
            startActivity(intent);
        }
    });

    smallIncome.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            kind = "수입";
            updateDataForSelectedDate();
        }
    });

    smallExpense.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            kind = "지출";
            updateDataForSelectedDate();
        }
    });

    bigIncome.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            kind = "수입";
            updateDataForSelectedDate();
        }
    });

    bigExpense.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            kind = "지출";
            updateDataForSelectedDate();
        }
    });

        updateDataForSelectedDate();
        expenseValueTextForSelectedDate();
        incomeValueTextForSelectedDate();
        adapter = new fundAdapter(this, fundList);
        bigadapter = new fundAdapter(this, bigfundList);
        smallRecyclerView.setAdapter(adapter);
        bigRecyclerView.setAdapter(bigadapter);


    baseLl.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            calendarPlus.setVisibility(View.GONE);
        }
    });
    calendarPlus.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    });
    calendarPlusLayout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            calendarPlus.setVisibility(View.VISIBLE);
        }
    });
    calendarPlusLl.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            calendarPlus.setVisibility(View.GONE);
        }
    });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // 선택한 날짜에 해당하는 데이터를 가져와서 어댑터에 설정
                selectYear = year;
                selectMonth = month+1;
                selectDay = dayOfMonth;

                updateDataForSelectedDate();
                expenseValueTextForSelectedDate();
                incomeValueTextForSelectedDate();
            }
        });

    }
    private void updateDataForSelectedDate() {
        // Fetch and display data for the selected date based on the 'kind' (expense or income)

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/UserData/" + uid + "/FundData/" + selectMonth + "월" + kind);
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fundList.clear();
                bigfundList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String day = snapshot.child("Day").getValue(String.class);
                    String month = snapshot.child("Month").getValue(String.class);

                    if (day.equals(String.valueOf(selectDay)) && month.equals(String.valueOf(selectMonth))) {
                        String fundId = snapshot.child("FundId").getValue(String.class);
                        String fundKind = kind;
                        String fundDivision = snapshot.child("FundDivision").getValue(String.class);
                        String fundMemo = snapshot.child("Description").getValue(String.class);
                        String fundValue = snapshot.child("Price").getValue(String.class);
                        String date = month + "-" + day;
                        fundList.add(new Fund_List(fundId, fundKind, fundDivision, fundMemo, fundValue, date));
                        bigfundList.add(new Fund_List(fundId, fundKind, fundDivision, fundMemo, fundValue, date));
                    }
                }
                adapter.notifyDataSetChanged(); // Notify the adapter of data changes
                bigadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        });
    }
    private void expenseValueTextForSelectedDate(){
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/UserData/" + uid + "/FundData/" + selectMonth + "월" + "지출");
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int totalExpenseValue = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String day = snapshot.child("Day").getValue(String.class);
                    String month = snapshot.child("Month").getValue(String.class);
                    if (day.equals(String.valueOf(selectDay)) && month.equals(String.valueOf(selectMonth))) {
                        String fundValue = snapshot.child("Price").getValue(String.class);
                        String fundCategory = snapshot.child("Category").getValue(String.class);
                        if (fundCategory.equals("고정(실사용)") ||fundCategory.equals("유동")) {
                            int value = Integer.parseInt(fundValue);
                                totalExpenseValue += value;
                        }
                    }
                }
                smallExpenseValue.setText(String.valueOf(totalExpenseValue));
                bigExpenseValue.setText(String.valueOf(totalExpenseValue));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        });
    }
    private void incomeValueTextForSelectedDate(){
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/UserData/" + uid + "/FundData/" + selectMonth + "월" + "수입");
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int totalIncomeValue = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String day = snapshot.child("Day").getValue(String.class);
                    String month = snapshot.child("Month").getValue(String.class);
                    if (day.equals(String.valueOf(selectDay)) && month.equals(String.valueOf(selectMonth))) {
                        String fundValue = snapshot.child("Price").getValue(String.class);
                        String fundCategory = snapshot.child("Category").getValue(String.class);
                        if (fundCategory.equals("고정(실수령)") ||fundCategory.equals("유동")) {
                            int value = Integer.parseInt(fundValue);
                            totalIncomeValue += value;
                        }
                    }
                }
                smallIncomeValue.setText(String.valueOf(totalIncomeValue));
                bigIncomeValue.setText(String.valueOf(totalIncomeValue));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        });
    }
}