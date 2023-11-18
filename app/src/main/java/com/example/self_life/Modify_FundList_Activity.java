package com.example.self_life;

import static com.example.self_life.YearMonth_Value.getCurrentMonth;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private RecyclerView todayRecyclerView, selectRecyclerView;
    private List<Fund_List> fundList = new ArrayList<>();
    private List<Fund_List> bigfundList = new ArrayList<>();
    private Button selectIncome,selectExpense,ModifyBtn,DeleteBtn;
    private TextView selectKind,selectDivision,selectMemo,selectValue,selectDay,selectDateList;
    private fundAdapter adapter,bigadapter;
    private Calendar calendar;
    private int year, month, day;
    private ImageView mypageBtn,selectDate;
    private int i,dayOfMonth,limitdayOfMonth;
    private String uid="",kind="지출",tempFundId="",selectedPeriod="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_fundlist);
        bottomNavigationView = findViewById(R.id.bottomNavigationView5);
        mypageBtn = findViewById(R.id.mypageBtn);
        todayRecyclerView = findViewById(R.id.todayFundList);
        selectRecyclerView = findViewById(R.id.FundList);
        selectIncome = findViewById(R.id.kindIncome);
        selectExpense = findViewById(R.id.kindExpense);
        selectKind = findViewById(R.id.selectKind);
        selectDivision = findViewById(R.id.selectDivision);
        selectMemo = findViewById(R.id.selectMemo);
        selectValue = findViewById(R.id.selectValue);
        selectDay = findViewById(R.id.selectDay);
        selectDateList = findViewById(R.id.selectDateList);
        selectDate = findViewById(R.id.selectDate);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        ModifyBtn = findViewById(R.id.ModifyBtn);
        DeleteBtn = findViewById(R.id.DeleteBtn);
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d");
        String todayStr = dateFormat.format(today);
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        uid = firebaseUser.getUid();
        todayRecyclerView.setHasFixedSize(true);
        todayRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        selectRecyclerView.setHasFixedSize(true);
        selectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        selectIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kind="수입";
                mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/UserData/"+uid+"/FundData/"+getCurrentMonth()+"월"+kind);
                mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        fundList.clear();
                        bigfundList.clear();
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
                        bigadapter.notifyDataSetChanged();
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
                        bigfundList.clear();
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
                        bigadapter.notifyDataSetChanged();
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
                bigfundList.clear();
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
                bigadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new fundAdapter(this, fundList);
        bigadapter = new fundAdapter(this, bigfundList);
        todayRecyclerView.setAdapter(adapter);
        selectRecyclerView.setAdapter(bigadapter);


        adapter.setOnItemClickListener(new fundAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String fundId) {
                mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/UserData/"+uid+"/FundData/"+getCurrentMonth()+"월"+kind+"/"+fundId);
                mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                            String day = snapshot.child("Day").getValue(String.class);
                            String month = snapshot.child("Month").getValue(String.class);
                            tempFundId = snapshot.child("FundId").getValue(String.class);
                            String fundKind = kind;
                            String fundDivision = snapshot.child("FundDivision").getValue(String.class);
                            String fundMemo = snapshot.child("Description").getValue(String.class);
                            String fundValue = snapshot.child("Price").getValue(String.class);
                            String date = month + "-" + day;
                            selectKind.setText(fundKind);
                            selectDivision.setText(fundDivision);
                            selectMemo.setText(fundMemo);
                            selectValue.setText(fundValue);
                            selectDay.setText(date);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        bigadapter.setOnItemClickListener(new fundAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String fundId) {
                mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/UserData/"+uid+"/FundData/"+getCurrentMonth()+"월"+kind+"/"+fundId);
                mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        String day = snapshot.child("Day").getValue(String.class);
                        String month = snapshot.child("Month").getValue(String.class);
                        tempFundId = snapshot.child("FundId").getValue(String.class);
                        String fundKind = kind;
                        String fundDivision = snapshot.child("FundDivision").getValue(String.class);
                        String fundMemo = snapshot.child("Description").getValue(String.class);
                        String fundValue = snapshot.child("Price").getValue(String.class);
                        String date = month + "-" + day;
                        selectKind.setText(fundKind);
                        selectDivision.setText(fundDivision);
                        selectMemo.setText(fundMemo);
                        selectValue.setText(fundValue);
                        selectDay.setText(date);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

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

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPeriodSelectionDialog();
            }
        });

        selectDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectKind.getText().toString().equals("수입")){
                    incomeshowKindSelectionDialog();
                }
                else {
                    expenseKindSelectionDialog();
                }
            }
        });

        selectKind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectKind.getText().toString().equals("수입")){
                    selectKind.setText("지출");
                }
                else{
                    selectKind.setText("수입");
                }
            }
        });

        ModifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/UserData/"+uid+"/FundData/"+getCurrentMonth()+"월"+kind+"/"+tempFundId);
                mDatabaseRef.child("FundDivision").setValue(selectDivision.getText().toString());
                mDatabaseRef.child("Description").setValue(selectMemo.getText().toString());
                mDatabaseRef.child("Price").setValue(selectValue.getText().toString().trim());
                mDatabaseRef.child("Month").setValue(String.valueOf(month+1));
                mDatabaseRef.child("Day").setValue(String.valueOf(day));
                mDatabaseRef.child("Year").setValue(String.valueOf(year));
                Toast.makeText(Modify_FundList_Activity.this,"수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Modify_FundList_Activity.this, Modify_FundList_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/UserData/"+uid+"/FundData/"+getCurrentMonth()+"월"+kind+"/"+tempFundId);
                mDatabaseRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Modify_FundList_Activity.this,"해당 내역이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Modify_FundList_Activity.this, Modify_FundList_Activity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Modify_FundList_Activity.this,"다시 실시해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        selectDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Modify_FundList_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String selectedDate = (month + 1) + "-" + dayOfMonth;
                                selectDay.setText(selectedDate);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
    }


    private void incomeshowKindSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("분류 선택");

        View view = getLayoutInflater().inflate(R.layout.income_selection_dialog, null);
        builder.setView(view);

        Button btnSalary = view.findViewById(R.id.btnSalary);
        Button btnAdditionalIncome = view.findViewById(R.id.btnAdditionalIncome);
        Button btnAllowance = view.findViewById(R.id.btnAllowance);
        Button btnRemittance = view.findViewById(R.id.btnRemittance);
        Button btnFinancialIncome = view.findViewById(R.id.btnFinancialIncome);
        Button btnOther = view.findViewById(R.id.btnOther);

        final AlertDialog dialog = builder.create();

        btnSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDivision.setText("월급");
                dialog.dismiss();
            }
        });

        btnAdditionalIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDivision.setText("부수입");
                dialog.dismiss();
            }
        });

        btnAllowance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDivision.setText("용돈");
                dialog.dismiss();
            }
        });

        btnRemittance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDivision.setText("송금");
                dialog.dismiss();
            }
        });

        btnFinancialIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDivision.setText("금융소득");
                dialog.dismiss();
            }
        });

        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDivision.setText("기타");
                dialog.dismiss();
            }
        });

        // 나머지 버튼들에 대해서도 동일한 방식으로 처리합니다.

        dialog.show();
    }
    private void expenseKindSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("분류 선택");

        // dialog_select_kind.xml 레이아웃을 inflate
        View view = getLayoutInflater().inflate(R.layout.expense_selection_dialog, null);
        builder.setView(view);

        // 버튼 클릭 리스너를 설정하여 사용자 선택 처리
        Button btnFoodExpense = view.findViewById(R.id.btnFoodExpense);
        Button btnTransport = view.findViewById(R.id.btnTransport);
        Button btnEntertainment = view.findViewById(R.id.btnEntertainment);
        Button btnMart = view.findViewById(R.id.btnMart);
        Button btnFashionBeauty = view.findViewById(R.id.btnFashionBeauty);
        Button btnHouseholdItems = view.findViewById(R.id.btnHouseholdItems);
        Button btnHousingCommunication = view.findViewById(R.id.btnHousingCommunication);
        Button btnHealth = view.findViewById(R.id.btnHealth);
        Button btnEducation = view.findViewById(R.id.btnEducation);
        Button btnCelebrationDues = view.findViewById(R.id.btnCelebrationDues);
        Button btnOther = view.findViewById(R.id.btnOther);

        final AlertDialog dialog = builder.create();

        btnFoodExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDivision.setText("식비");
                dialog.dismiss();
            }
        });

        btnTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDivision.setText("교통/차량");
                dialog.dismiss();
            }
        });

        btnEntertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDivision.setText("문화생활");
                dialog.dismiss();
            }
        });

        btnMart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDivision.setText("마트");
                dialog.dismiss();
            }
        });

        btnFashionBeauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // "용돈" 버튼을 클릭한 경우
                selectDivision.setText("패션/미용"); // selectKind의 텍스트 업데이트
                dialog.dismiss(); // 다이얼로그 닫기
            }
        });

        btnHouseholdItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDivision.setText("생활용품");
                dialog.dismiss();
            }
        });

        btnHousingCommunication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDivision.setText("주거/통신");
                dialog.dismiss();
            }
        });
        btnHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDivision.setText("건강");
                dialog.dismiss();
            }
        });
        btnEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDivision.setText("교육");
                dialog.dismiss();
            }
        });
        btnCelebrationDues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDivision.setText("경조사/회비");
                dialog.dismiss();
            }
        });
        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDivision.setText("기타");
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void showPeriodSelectionDialog() {
        final String[] periods = {"오늘", "일주일", "이번 달","*고정"};
        int checkedItem = -1;

        if (selectedPeriod != null) {
            if (selectedPeriod.equals("오늘")) {
                checkedItem = 0;
            } else if (selectedPeriod.equals("일주일")) {
                checkedItem = 1;
            } else if (selectedPeriod.equals("이번 달")) {
                checkedItem = 2;
            }else if (selectedPeriod.equals("*고정")) {
                checkedItem = 3;
            }

        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("주기 선택");
        builder.setSingleChoiceItems(periods, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedPeriod = periods[which];
                dialog.dismiss();

                // 선택된 주기에 따른 작업을 수행
                if (selectedPeriod.equals("오늘")) {
                    dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                    selectDateList.setText("오늘");
                } else if (selectedPeriod.equals("일주일")) {
                    dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                    limitdayOfMonth = dayOfMonth - 7;
                    selectDateList.setText("일주일");
                }else if (selectedPeriod.equals("이번 달")) {
                    dayOfMonth = getCurrentMonth();
                    selectDateList.setText("이번 달");
                } else if (selectedPeriod.equals("*고정")) {
                    selectDateList.setText("*고정");
                }

                updateBigFundList();
            }
        });

        builder.show();
    }
    private void updateBigFundList() {
        bigfundList.clear();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/UserData/"+uid+"/FundData/"+getCurrentMonth()+"월"+kind);
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d");
        String todayStr = dateFormat.format(today);
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //fundList.clear();
                bigfundList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String day = snapshot.child("Day").getValue(String.class);
                    String month = snapshot.child("Month").getValue(String.class);
                    if (selectedPeriod.equals("오늘") && day.equals(todayStr) && month.equals(String.valueOf(getCurrentMonth()))) {
                        addFundToList(snapshot);
                    } else if (selectedPeriod.equals("일주일") && (Integer.valueOf(day) <= Integer.valueOf(todayStr) && Integer.valueOf(day) >= limitdayOfMonth) && month.equals(String.valueOf(getCurrentMonth()))) {
                        addFundToList(snapshot);
                    }else if (selectedPeriod.equals("이번 달") && month.equals(String.valueOf(getCurrentMonth()))) {
                        addFundToList(snapshot);
                    }
                }
                bigadapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(selectedPeriod.equals("*고정")){
            DatabaseReference fixFundRef = FirebaseDatabase.getInstance().getReference("self_life/UserData/"+uid+"/FundData/"+"고정"+kind);
            fixFundRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    bigfundList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        addFundToList(snapshot);
                    }
                    bigadapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void addFundToList(DataSnapshot snapshot) {
        String fundId = snapshot.child("FundId").getValue(String.class);
        String fundKind = kind;
        String fundDivision = snapshot.child("FundDivision").getValue(String.class);
        String fundMemo = snapshot.child("Description").getValue(String.class);
        String fundValue = snapshot.child("Price").getValue(String.class);
        String date = snapshot.child("Month").getValue(String.class) + "-" + snapshot.child("Day").getValue(String.class);
        bigfundList.add(new Fund_List(fundId, fundKind, fundDivision, fundMemo, fundValue, date));
    }
}
