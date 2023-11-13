package com.example.self_life;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LifeItem_Activity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ImageView mypageBtn;
    private FrameLayout input_LifeItem,page_input,itemMenu;
    private ScrollView seAn, lifeitem, hwajang;
    private TextView lifeItemCategory;
    private EditText[] MaxEt = new EditText[13];
    private EditText[] MaxEt2 = new EditText[5];
    private EditText[] MinEt = new EditText[13];
    private EditText[] NumEt = new EditText[13];
    private Button[] optionBtn = new Button[13];
    private Button[] optionBtn2 = new Button[5];
    private Button[] regressionBtn = new Button[13];
    private Button[] regressionBtn2 = new Button[5];
    private TextView[] rebuyTv = new TextView[13];
    private TextView[] rebuyTv2 = new TextView[5];
    private TextView[] regressionValueTv = new TextView[13];
    private TextView[] regressionValueTv2 = new TextView[5];
    private Integer[] MaxEtIds = {R.id.Max1Et,R.id.Max2Et,R.id.Max3Et,R.id.Max5Et,R.id.Max21Et,R.id.Max22Et,R.id.Max23Et,R.id.Max24Et,R.id.Max25Et,R.id.Max26Et,R.id.Max31Et,R.id.Max32Et,R.id.Max33Et};
    private Integer[] MaxEtIds2 = {R.id.Max4Et,R.id.Max6Et,R.id.Max7Et,R.id.Max8Et,R.id.Max34Et};
    private Integer[] MinEtIds = {R.id.Min1Et,R.id.Min2Et,R.id.Min3Et,R.id.Min5Et,R.id.Min21Et,R.id.Min22Et,R.id.Min23Et,R.id.Min24Et,R.id.Min25Et,R.id.Min26Et,R.id.Min31Et,R.id.Min32Et,R.id.Min33Et};
    private Integer[] NumEtIds = {R.id.Num1Et,R.id.Num2Et,R.id.Num3Et,R.id.Num5Et,R.id.Num21Et,R.id.Num22Et,R.id.Num23Et,R.id.Num24Et,R.id.Num25Et,R.id.Num26Et,R.id.Num31Et,R.id.Num32Et,R.id.Num33Et};
    private Integer[] optionBtnIds = {R.id.Option1Btn,R.id.Option2Btn,R.id.Option3Btn,R.id.Option5Btn,R.id.Option21Btn,R.id.Option22Btn,R.id.Option23Btn,R.id.Option24Btn,R.id.Option25Btn,R.id.Option26Btn,R.id.Option31Btn,R.id.Option32Btn,R.id.Option33Btn};
    private Integer[] optionBtnIds2 = {R.id.Option4Btn,R.id.Option6Btn,R.id.Option7Btn,R.id.Option8Btn,R.id.Option34Btn};
    private Integer[] regressionBtnIds = {R.id.regressionBtn1,R.id.regressionBtn2,R.id.regressionBtn3,R.id.regressionBtn5,R.id.regressionBtn21,R.id.regressionBtn22,R.id.regressionBtn23,R.id.regressionBtn24,R.id.regressionBtn25,R.id.regressionBtn26,R.id.regressionBtn31,R.id.regressionBtn32,R.id.regressionBtn33};
    private Integer[] regressionBtnIds2 = {R.id.regressionBtn4,R.id.regressionBtn6,R.id.regressionBtn7,R.id.regressionBtn8,R.id.regressionBtn34};
    private Integer[] rebuyTvIds = {R.id.rebuy1Tv,R.id.rebuy2Tv,R.id.rebuy3Tv,R.id.rebuy5Tv,R.id.rebuy21Tv,R.id.rebuy22Tv,R.id.rebuy23Tv,R.id.rebuy24Tv,R.id.rebuy25Tv,R.id.rebuy26Tv,R.id.rebuy31Tv,R.id.rebuy32Tv,R.id.rebuy33Tv};
    private Integer[] rebuyTvIds2 = {R.id.rebuy4Tv,R.id.rebuy6Tv,R.id.rebuy7Tv,R.id.rebuy8Tv,R.id.rebuy34Tv};
    private Integer[] regressionValueTvIds = {R.id.regressionValue1Tv,R.id.regressionValue2Tv,R.id.regressionValue3Tv,R.id.regressionValue5Tv,R.id.regressionValue21Tv,R.id.regressionValue22Tv,R.id.regressionValue23Tv,R.id.regressionValue24Tv,R.id.regressionValue25Tv,R.id.regressionValue26Tv,R.id.regressionValue31Tv,R.id.regressionValue32Tv,R.id.regressionValue33Tv};
    private Integer[] regressionValueTvIds2 = {R.id.regressionValue4Tv,R.id.regressionValue6Tv,R.id.regressionValue7Tv,R.id.regressionValue8Tv,R.id.regressionValue34Tv};
    private LinearLayout backLayout;
    private double temp;
    private int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifeitem);
        bottomNavigationView = findViewById(R.id.bottomNavigationView5);
        itemMenu = findViewById(R.id.itemMenu);
        mypageBtn = findViewById(R.id.mypageBtn);
        lifeItemCategory = findViewById(R.id.lifeitem_category);
        seAn =findViewById(R.id.seAndogu);
        lifeitem = findViewById(R.id.lifeItem);
        hwajang = findViewById(R.id.hwajangdogu);
        input_LifeItem = findViewById(R.id.lifeitem_plus);
        page_input = findViewById(R.id.DaisoFl);
        backLayout = findViewById(R.id.backFl);
        for(i=0; i < MaxEt.length; i++){
            MaxEt[i] = findViewById(MaxEtIds[i]);
        }
        for(i=0; i < MinEt.length; i++){
            MinEt[i] = findViewById(MinEtIds[i]);
        }

        for(i=0; i < NumEt.length; i++){
            NumEt[i] = findViewById(NumEtIds[i]);
        }
        for(i=0; i < optionBtn.length; i++){
            final int index = i;
            optionBtn[i] = findViewById(optionBtnIds[i]);
            optionBtn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDateInputDialog(index);
                }
            });
        }
        for(i=0; i < regressionBtn.length; i++){
            regressionBtn[i] = findViewById(regressionBtnIds[i]);
        }
        for(i=0; i < rebuyTv.length; i++){
            rebuyTv[i] = findViewById(rebuyTvIds[i]);
        }
        for(i=0; i < regressionValueTv.length; i++){
            regressionValueTv[i] = findViewById(regressionValueTvIds[i]);
        }
        for(i=0; i < MaxEt2.length; i++){
            MaxEt2[i] = findViewById(MaxEtIds2[i]);
        }
        for(i=0; i < optionBtn2.length; i++){
            optionBtn2[i] = findViewById(optionBtnIds2[i]);
        }
        for(i=0; i < regressionBtn2.length; i++){
            regressionBtn2[i] = findViewById(regressionBtnIds2[i]);
        }
        for(i=0; i < rebuyTv2.length; i++){
            rebuyTv2[i] = findViewById(rebuyTvIds2[i]);
        }
        for(i=0; i < regressionValueTv2.length; i++){
            regressionValueTv2[i] = findViewById(regressionValueTvIds2[i]);
        }

        for (i = 0; i < regressionBtn.length; i++) {
            final int index = i;
            regressionBtn[i] = findViewById(regressionBtnIds[i]);
            regressionBtn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDateInputDialog(index);
                }
            });
        }


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_cal) {
                    Intent intent = new Intent(LifeItem_Activity.this, Calendar_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_chart) {
                    Intent intent = new Intent(LifeItem_Activity.this, Chart_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_lifeitem) {
                    return true;
                } else if (item.getItemId() == R.id.action_board) {
                    Intent intent = new Intent(LifeItem_Activity.this, Board_Activity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        mypageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LifeItem_Activity.this, MyPage_Activity.class);
                startActivity(intent);
            }
        });

        itemMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });

        input_LifeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                page_input.setVisibility(View.VISIBLE);
                backLayout.setVisibility(View.VISIBLE);
            }
        });
        backLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (page_input.getVisibility() == View.VISIBLE) {
                    page_input.setVisibility(View.GONE);
                    backLayout.setVisibility(View.GONE);
                }
                return false;
            }
        });
        page_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.lifeitem_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_item1) {
                    lifeItemCategory.setText("세안용품");
                    seAn.setVisibility(View.VISIBLE);
                    lifeitem.setVisibility(View.GONE);
                    hwajang.setVisibility(View.GONE);
                    return true;
                } else if (item.getItemId() == R.id.menu_item2) {
                    lifeItemCategory.setText("생활용품");
                    seAn.setVisibility(View.GONE);
                    lifeitem.setVisibility(View.VISIBLE);
                    hwajang.setVisibility(View.GONE);
                    return true;
                } else if (item.getItemId() == R.id.menu_item3) {
                    lifeItemCategory.setText("화장품");
                    seAn.setVisibility(View.GONE);
                    lifeitem.setVisibility(View.GONE);
                    hwajang.setVisibility(View.VISIBLE);
                    return true;
                }  else {
                    return false;
                }

            }
        });

        popupMenu.show();
    }

    private void calculateRegressionDate(final int index, Date selectedDate) {
        double max = getNumericValueFromEditText(MaxEt[index]);
        double min = getNumericValueFromEditText(MinEt[index]);
        double num = getNumericValueFromEditText(NumEt[index]);

        double daysLeft = max / (min * num);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate); // 선택한 날짜로 설정
        calendar.add(Calendar.DAY_OF_MONTH, (int) daysLeft);

        Date lastUsageDate = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault());
        String lastUsageDateString = dateFormat.format(lastUsageDate);

        // 시스템 날짜와 선택 날짜 간의 차이를 계산
        int daysDifference = getDaysDifference(selectedDate, Calendar.getInstance().getTime());

        rebuyTv[index].setText(daysDifference + "일 뒤에 재구매 예정입니다. (" + lastUsageDateString +")");
        rebuyTv[index].setVisibility(View.VISIBLE);

        double regressionDays = max / (min * num);
        calendar = Calendar.getInstance();
        calendar.setTime(selectedDate); // 선택한 날짜로 설정
        calendar.add(Calendar.DAY_OF_MONTH, (int) regressionDays);

        Date regressionDate = calendar.getTime();

        String regressionDateString = dateFormat.format(regressionDate);
        regressionValueTv[index].setText(regressionDateString);
    }

    private int getDaysDifference(Date startDate, Date endDate) {
        long diff = endDate.getTime() - startDate.getTime();
        return (int) (diff / (24 * 60 * 60 * 1000));
    }


    private double getNumericValueFromEditText(EditText editText) {
        String text = editText.getText().toString();
        // 숫자와 소수점만 남기고 나머지는 제거하는 정규식 사용
        String numericText = text.replaceAll("[^0-9.]", "");

        if (!numericText.isEmpty()) {
            // 숫자와 소수점만 포함된 문자열이 비어있지 않으면 해당 숫자를 반환
            return Double.parseDouble(numericText);
        } else {
            // 숫자가 없는 경우 기본값이나 에러 처리를 수행할 수 있습니다.
            return 0.0;
        }
    }
    private void showDateInputDialog(final int index) {
        // 현재 날짜를 가져옴
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // DatePickerDialog 생성
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // 날짜가 선택되면 실행되는 콜백
                        // 선택된 날짜를 형식에 맞게 가공하여 EditText에 표시
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault());
                        Date selectedDate = calendar.getTime();
                        optionBtn[index].setText("선택완료");

                        // 선택된 날짜를 기반으로 재구매 날짜 예측
                        calculateRegressionDate(index, selectedDate);
                    }
                }, year, month, day);

        // 다이얼로그 보이기
        datePickerDialog.show();
    }

}
