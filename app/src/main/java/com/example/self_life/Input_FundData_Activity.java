package com.example.self_life;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Input_FundData_Activity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private BottomNavigationView bottomNavigationView;
    private ImageView mypageBtn;
    private Button incomeBtn, expenseBtn;
    private LinearLayout[] incomeButtons = new LinearLayout[6];
    private LinearLayout[] expenseButtons = new LinearLayout[11];
    private Integer[] incomeBtnIds = {R.id.incomeBtn1, R.id.incomeBtn2, R.id.incomeBtn3, R.id.incomeBtn4, R.id.incomeBtn5, R.id.incomeBtn6};
    private Integer[] expenseBtnIds = {R.id.expense1, R.id.expense2, R.id.expense3, R.id.expense4, R.id.expense5, R.id.expense6, R.id.expense7, R.id.expense8, R.id.expense9, R.id.expense10, R.id.expense11};
    private String[] incomeBtnStr = {"월급", "부수입", "용돈", "송금", "금융소득", "기타"};
    private String[] expenseBtnStr = {"식비", "교통/차량", "문화생활", "마트", "패션/미용", "생활용품", "주거/통신", "건강", "교육", "경조사/회비", "기타"};
    private LinearLayout incomeLl, expenseLl, saveBtn;
    private EditText incomeFundEt, incomeMemoEt, expenseFundEt, expenseMemoEt;
    private TextView incomeDayEt, incomeCategoryEt, expenseDayEt, expenseCategoryEt;
    private Calendar calendar;
    private int year, month, day;
    private RadioGroup incomeRgroup, expenseRgroup;
    private RadioButton incomeRbtn1,incomeRbtn2,incomeRbtn3,expenseRbtn1,expenseRbtn2,expenseRbtn3;
    private int i;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputfund);
        bottomNavigationView = findViewById(R.id.bottomNavigationView5);
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        userId = firebaseUser.getUid();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/UserData/" + userId);
        mypageBtn = findViewById(R.id.mypageBtn);
        incomeBtn = findViewById(R.id.incomeSelectBtn);
        expenseBtn = findViewById(R.id.expenseSelectBtn);
        incomeLl = findViewById(R.id.incomeLayout);
        expenseLl = findViewById(R.id.expenseLayout);
        incomeFundEt = findViewById(R.id.incomeFundEt);
        incomeMemoEt = findViewById(R.id.incomeMemoEt);
        expenseFundEt = findViewById(R.id.expenseFund);
        expenseMemoEt = findViewById(R.id.expneseMemoEt);
        incomeDayEt = findViewById(R.id.incomeDayTv);
        incomeCategoryEt = findViewById(R.id.incomeCategoryEt);
        expenseDayEt = findViewById(R.id.expenseDayEt);
        expenseCategoryEt = findViewById(R.id.expenseCategoryEt);
        incomeRgroup = findViewById(R.id.incomeRgroup);
        expenseRgroup = findViewById(R.id.expenseRgroup);
        saveBtn = findViewById(R.id.SaveBtn);
        incomeRbtn1 = findViewById(R.id.incomeRbtn1);
        incomeRbtn2 = findViewById(R.id.incomeRbtn2);
        incomeRbtn3 = findViewById(R.id.incomeRbtn3);
        expenseRbtn1 = findViewById(R.id.expenseRbtn1);
        expenseRbtn2 = findViewById(R.id.expenseRbtn2);
        expenseRbtn3 = findViewById(R.id.expenseRbtn3);

        for (i = 0; i < incomeButtons.length; i++) {
            final int buttonIndex = i;
            incomeButtons[i] = findViewById(incomeBtnIds[i]);
            incomeButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String buttonText = incomeBtnStr[buttonIndex];
                    incomeCategoryEt.setText(buttonText);
                }
            });
        }

        for (i = 0; i < expenseButtons.length; i++) {
            final int buttonIndex = i;
            expenseButtons[i] = findViewById(expenseBtnIds[i]);
            expenseButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String buttonText = expenseBtnStr[buttonIndex];
                    expenseCategoryEt.setText(buttonText);
                }
            });
        }

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        incomeDayEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Input_FundData_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                                incomeDayEt.setText(selectedDate);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        expenseDayEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Input_FundData_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                                expenseDayEt.setText(selectedDate);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_cal) {
                    Intent intent = new Intent(Input_FundData_Activity.this, Calendar_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_chart) {
                    Intent intent = new Intent(Input_FundData_Activity.this, Chart_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_lifeitem) {
                    Intent intent = new Intent(Input_FundData_Activity.this, LifeItem_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_board) {
                    Intent intent = new Intent(Input_FundData_Activity.this, Board_Activity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        mypageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Input_FundData_Activity.this, MyPage_Activity.class);
                startActivity(intent);
            }
        });

        incomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incomeLl.setVisibility(View.VISIBLE);
                expenseLl.setVisibility(View.GONE);
                int drawableResourceId = getResources().getIdentifier("box1", "drawable", getPackageName());
                incomeBtn.setBackgroundResource(drawableResourceId);
                incomeBtn.setTextColor(Color.parseColor("#ed6c59"));
                drawableResourceId = getResources().getIdentifier("box", "drawable", getPackageName());
                expenseBtn.setBackgroundResource(drawableResourceId);
                expenseBtn.setTextColor(Color.parseColor("#d4d4d8"));
            }
        });

        expenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incomeLl.setVisibility(View.GONE);
                expenseLl.setVisibility(View.VISIBLE);
                int drawableResourceId = getResources().getIdentifier("box", "drawable", getPackageName());
                incomeBtn.setBackgroundResource(drawableResourceId);
                incomeBtn.setTextColor(Color.parseColor("#d4d4d8"));
                drawableResourceId = getResources().getIdentifier("box1", "drawable", getPackageName());
                expenseBtn.setBackgroundResource(drawableResourceId);
                expenseBtn.setTextColor(Color.parseColor("#ed6c59"));
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (incomeLl.getVisibility() == View.VISIBLE ) {
                    if(incomeDayEt.getText().toString().equals("날짜 선택") || incomeFundEt.getText().toString().equals("") || incomeMemoEt.getText().toString().equals("")){
                        Toast.makeText(Input_FundData_Activity.this, "모든 내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }else {
                        String incomeCategory = "";
                        String dateString = incomeDayEt.getText().toString();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d");
                        try {
                            Date date = dateFormat.parse(dateString);
                            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                            SimpleDateFormat monthFormat = new SimpleDateFormat("M");
                            SimpleDateFormat dayFormat = new SimpleDateFormat("d");
                            String year = yearFormat.format(date);
                            String month = monthFormat.format(date);
                            String day = dayFormat.format(date);
                            String monthString = month + "월";
                            String kind = "";
                            if (incomeRbtn1.isChecked()) {
                                incomeCategory = incomeRbtn1.getText().toString();
                            }
                            if (incomeRbtn2.isChecked()) {
                                incomeCategory = incomeRbtn2.getText().toString();
                            }
                            if (incomeRbtn3.isChecked()) {
                                incomeCategory = incomeRbtn3.getText().toString();
                            }
                            if (incomeCategory.equals("")) {
                                Toast.makeText(Input_FundData_Activity.this, "모든 내용을 입해주세요.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if(incomeCategory.equals("고 정")){
                                    kind = "수입";
                                    String fundName = "고정" + kind;
                                    String fundKey = mDatabaseRef.child("FundData").child(fundName).push().getKey();
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("Year").setValue(year);
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("Month").setValue(month);
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("Day").setValue(day);
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("FundDivision").setValue(incomeCategoryEt.getText().toString());
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("Price").setValue(incomeFundEt.getText().toString());
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("Description").setValue(incomeMemoEt.getText().toString());
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("Category").setValue(incomeCategory);
                                    Toast.makeText(Input_FundData_Activity.this, "입력이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Input_FundData_Activity.this, Chart_Activity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    kind = "수입";
                                    String fundName = monthString + kind;
                                    String fundKey = mDatabaseRef.child("FundData").child(fundName).push().getKey();
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("Year").setValue(year);
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("Month").setValue(month);
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("Day").setValue(day);
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("FundDivision").setValue(incomeCategoryEt.getText().toString());
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("Price").setValue(incomeFundEt.getText().toString());
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("Description").setValue(incomeMemoEt.getText().toString());
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("Category").setValue(incomeCategory);
                                    Toast.makeText(Input_FundData_Activity.this, "입력이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Input_FundData_Activity.this, Chart_Activity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                } else if (expenseLl.getVisibility() == View.VISIBLE) {
                    if(expenseDayEt.getText().toString().equals("날짜 선택") || expenseFundEt.getText().toString().equals("") || expenseMemoEt.getText().toString().equals("")) {
                        Toast.makeText(Input_FundData_Activity.this, "모든 내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        String expenseCategory = "";
                        String dateString = expenseDayEt.getText().toString();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d");
                        try {
                            Date date = dateFormat.parse(dateString);
                            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                            SimpleDateFormat monthFormat = new SimpleDateFormat("M");
                            SimpleDateFormat dayFormat = new SimpleDateFormat("d");
                            String year = yearFormat.format(date);
                            String month = monthFormat.format(date);
                            String day = dayFormat.format(date);
                            String monthString = month + "월";
                            String kind = "";
                            if (expenseRbtn1.isChecked()) {
                                expenseCategory = expenseRbtn1.getText().toString();
                            }
                            if (expenseRbtn2.isChecked()) {
                                expenseCategory = expenseRbtn2.getText().toString();
                            }
                            if (expenseRbtn3.isChecked()) {
                                expenseCategory = expenseRbtn3.getText().toString();
                            }
                            if (expenseCategory.equals("")) {
                                Toast.makeText(Input_FundData_Activity.this, "모든 내용을 입해주세요.", Toast.LENGTH_SHORT).show();
                            } else {
                                if(expenseCategory.equals("고 정")){

                                    kind = "지출";
                                    String fundName = "고정" + kind;
                                    String fundKey = mDatabaseRef.child("FundData").child(fundName).push().getKey();
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("FundId").setValue(fundKey);
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("Year").setValue(year);
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("Month").setValue(month);
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("Day").setValue(day);
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("FundDivision").setValue(expenseCategoryEt.getText().toString());
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("Price").setValue(expenseFundEt.getText().toString());
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("Description").setValue(expenseMemoEt.getText().toString());
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("Category").setValue(expenseCategory);
                                    Toast.makeText(Input_FundData_Activity.this, "입력이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Input_FundData_Activity.this, Chart_Activity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    kind = "지출";
                                    String fundName = monthString + kind;
                                    String fundKey = mDatabaseRef.child("FundData").child(fundName).push().getKey();
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("FundId").setValue(fundKey);
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("Year").setValue(year);
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("Month").setValue(month);
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("Day").setValue(day);
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("FundDivision").setValue(expenseCategoryEt.getText().toString());
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("Price").setValue(expenseFundEt.getText().toString());
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("Description").setValue(expenseMemoEt.getText().toString());
                                    mDatabaseRef.child("FundData").child(fundName).child(fundKey).child("Category").setValue(expenseCategory);
                                    Toast.makeText(Input_FundData_Activity.this, "입력이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Input_FundData_Activity.this, Chart_Activity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    }
                    else{
                        Toast.makeText(Input_FundData_Activity.this, "모든 내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
        });
    }
}
