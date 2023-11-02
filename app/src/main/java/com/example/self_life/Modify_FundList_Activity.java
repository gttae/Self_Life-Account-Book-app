package com.example.self_life;

import static com.example.self_life.YearMonth_Value.getCurrentMonth;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Modify_FundList_Activity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mFirebaseAuth;
    private RecyclerView todayRecyclerView;
    private List<Fund_List> fundList = new ArrayList<>();
    private Button selectIncome,selectExpense;
    private fundAdapter adapter;
    private ImageView mypageBtn;
    private String uid="",kind="지출";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_fundlist);
        bottomNavigationView = findViewById(R.id.bottomNavigationView5);
        mypageBtn = findViewById(R.id.mypageBtn);
        todayRecyclerView = findViewById(R.id.todayFundList);
        selectIncome = findViewById(R.id.kindIncome);
        selectExpense = findViewById(R.id.kindExpense);
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d");
        String todayStr = dateFormat.format(today);
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        uid = firebaseUser.getUid();
        todayRecyclerView.setHasFixedSize(true);
        todayRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        selectIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kind="수입";
                mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/UserData/"+uid+"/FundData/"+getCurrentMonth()+"월"+kind);
                mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        fundList.clear();
                        //fundList.add(new Fund_List("test","test","test","test","test","test"));
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String day = snapshot.child("Day").getValue(String.class);
                            String month = snapshot.child("Month").getValue(String.class);
                            if(day.equals(todayStr) && month.equals(String.valueOf(getCurrentMonth()))){
                                String fundId = snapshot.child("FundId").getValue(String.class);
                                String fundKind = kind;
                                String fundDivision = snapshot.child("FundDivision").getValue(String.class);
                                String fundMemo = snapshot.child("Description").getValue(String.class);
                                String fundValue = snapshot.child("Price").getValue(String.class);
                                String date = month + "-" + day;
                                fundList.add(new Fund_List(fundId,fundKind,fundDivision,fundMemo,fundValue,date));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        selectExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kind="지출";
                mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/UserData/"+uid+"/FundData/"+getCurrentMonth()+"월"+kind);
                mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        fundList.clear();
                        //fundList.add(new Fund_List("test","test","test","test","test","test"));
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String day = snapshot.child("Day").getValue(String.class);
                            String month = snapshot.child("Month").getValue(String.class);
                            if(day.equals(todayStr) && month.equals(String.valueOf(getCurrentMonth()))){
                                String fundId = snapshot.child("FundId").getValue(String.class);
                                String fundKind = kind;
                                String fundDivision = snapshot.child("FundDivision").getValue(String.class);
                                String fundMemo = snapshot.child("Description").getValue(String.class);
                                String fundValue = snapshot.child("Price").getValue(String.class);
                                String date = month + "-" + day;
                                fundList.add(new Fund_List(fundId,fundKind,fundDivision,fundMemo,fundValue,date));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/UserData/"+uid+"/FundData/"+getCurrentMonth()+"월"+kind);
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fundList.clear();
                //fundList.add(new Fund_List("test","test","test","test","test","test"));
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String day = snapshot.child("Day").getValue(String.class);
                    String month = snapshot.child("Month").getValue(String.class);
                    if(day.equals(todayStr) && month.equals(String.valueOf(getCurrentMonth()))){
                        String fundId = snapshot.child("FundId").getValue(String.class);
                        String fundKind = kind;
                        String fundDivision = snapshot.child("FundDivision").getValue(String.class);
                        String fundMemo = snapshot.child("Description").getValue(String.class);
                        String fundValue = snapshot.child("Price").getValue(String.class);
                        String date = month + "-" + day;
                        fundList.add(new Fund_List(fundId,fundKind,fundDivision,fundMemo,fundValue,date));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new fundAdapter(this, fundList);
        todayRecyclerView.setAdapter(adapter);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_cal) {
                    Intent intent = new Intent(Modify_FundList_Activity.this, Calendar_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_chart) {
                    Intent intent = new Intent(Modify_FundList_Activity.this, Chart_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_lifeitem) {
                    Intent intent = new Intent(Modify_FundList_Activity.this, LifeItem_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_board) {
                    Intent intent = new Intent(Modify_FundList_Activity.this, Board_Activity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        mypageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Modify_FundList_Activity.this, MyPage_Activity.class);
                startActivity(intent);
            }
        });
    }
}
