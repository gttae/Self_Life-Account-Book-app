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
import android.widget.Toast;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class LifeItem_Activity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ImageView mypageBtn;
    private FrameLayout input_LifeItem,page_input,itemMenu;
    private ScrollView seAn, lifeitem, hwajang, otherItem;
    private TextView lifeItemCategory;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private EditText MaxPlusEt;
    private EditText MinPlusEt;
    private EditText NumPlusEt;
    private Button optionPlusBtn;
    private LinearLayout itemPlusBtn;

    private EditText[] MaxEt = new EditText[13];
    private EditText[] MaxEt2 = new EditText[5];
    private EditText[] MaxEt3 = new EditText[4];
    private EditText[] MinEt = new EditText[13];
    private EditText[] MinEt3 = new EditText[4];
    private EditText[] NumEt = new EditText[13];
    private EditText[] NumEt3 = new EditText[4];
    private Button[] optionBtn = new Button[13];
    private Button[] optionBtn2 = new Button[5];
    private Button[] optionBtn3 = new Button[4];
    private Button[] regressionBtn = new Button[13];
    private Button[] regressionBtn2 = new Button[5];
    private Button[] regressionBtn3 = new Button[4];
    private TextView[] rebuyTv = new TextView[13];
    private TextView[] rebuyTv2 = new TextView[5];
    private TextView[] rebuyTv3 = new TextView[4];
    private TextView[] regressionValueTv = new TextView[13];
    private TextView[] regressionValueTv2 = new TextView[5];
    private TextView[] regressionValueTv3 = new TextView[4];
    private LinearLayout[] plusLayout = new LinearLayout[4];
    private Integer[] MaxEtIds = {R.id.Max1Et,R.id.Max2Et,R.id.Max3Et,R.id.Max5Et,R.id.Max21Et,R.id.Max22Et,R.id.Max23Et,R.id.Max24Et,R.id.Max25Et,R.id.Max26Et,R.id.Max31Et,R.id.Max32Et,R.id.Max33Et};
    private Integer[] MaxEtIds2 = {R.id.Max4Et,R.id.Max6Et,R.id.Max7Et,R.id.Max8Et,R.id.Max34Et};
    private Integer[] MaxEtIds3 = {R.id.Max41Et,R.id.Max42Et,R.id.Max43Et,R.id.Max44Et};
    private Integer[] MinEtIds = {R.id.Min1Et,R.id.Min2Et,R.id.Min3Et,R.id.Min5Et,R.id.Min21Et,R.id.Min22Et,R.id.Min23Et,R.id.Min24Et,R.id.Min25Et,R.id.Min26Et,R.id.Min31Et,R.id.Min32Et,R.id.Min33Et};
    private Integer[] MinEtIds3 = {R.id.Min41Et,R.id.Min42Et,R.id.Min43Et,R.id.Min44Et};
    private Integer[] NumEtIds = {R.id.Num1Et,R.id.Num2Et,R.id.Num3Et,R.id.Num5Et,R.id.Num21Et,R.id.Num22Et,R.id.Num23Et,R.id.Num24Et,R.id.Num25Et,R.id.Num26Et,R.id.Num31Et,R.id.Num32Et,R.id.Num33Et};
    private Integer[] NumEtIds3 = {R.id.Num41Et,R.id.Num42Et,R.id.Num43Et,R.id.Num44Et};
    private Integer[] optionBtnIds = {R.id.Option1Btn,R.id.Option2Btn,R.id.Option3Btn,R.id.Option5Btn,R.id.Option21Btn,R.id.Option22Btn,R.id.Option23Btn,R.id.Option24Btn,R.id.Option25Btn,R.id.Option26Btn,R.id.Option31Btn,R.id.Option32Btn,R.id.Option33Btn};
    private Integer[] optionBtnIds2 = {R.id.Option4Btn,R.id.Option6Btn,R.id.Option7Btn,R.id.Option8Btn,R.id.Option34Btn};
    private Integer[] optionBtnIds3 = {R.id.Option41Btn,R.id.Option42Btn,R.id.Option43Btn,R.id.Option44Btn};
    private Integer[] regressionBtnIds = {R.id.regressionBtn1,R.id.regressionBtn2,R.id.regressionBtn3,R.id.regressionBtn5,R.id.regressionBtn21,R.id.regressionBtn22,R.id.regressionBtn23,R.id.regressionBtn24,R.id.regressionBtn25,R.id.regressionBtn26,R.id.regressionBtn31,R.id.regressionBtn32,R.id.regressionBtn33};
    private Integer[] regressionBtnIds2 = {R.id.regressionBtn4,R.id.regressionBtn6,R.id.regressionBtn7,R.id.regressionBtn8,R.id.regressionBtn34};
    private Integer[] regressionBtnIds3 = {R.id.regressionBtn41,R.id.regressionBtn42,R.id.regressionBtn43,R.id.regressionBtn44};
    private Integer[] rebuyTvIds = {R.id.rebuy1Tv,R.id.rebuy2Tv,R.id.rebuy3Tv,R.id.rebuy5Tv,R.id.rebuy21Tv,R.id.rebuy22Tv,R.id.rebuy23Tv,R.id.rebuy24Tv,R.id.rebuy25Tv,R.id.rebuy26Tv,R.id.rebuy31Tv,R.id.rebuy32Tv,R.id.rebuy33Tv};
    private Integer[] rebuyTvIds2 = {R.id.rebuy4Tv,R.id.rebuy6Tv,R.id.rebuy7Tv,R.id.rebuy8Tv,R.id.rebuy34Tv};
    private Integer[] rebuyTvIds3 = {R.id.rebuy41Tv,R.id.rebuy42Tv,R.id.rebuy43Tv,R.id.rebuy44Tv};
    private Integer[] regressionValueTvIds = {R.id.regressionValue1Tv,R.id.regressionValue2Tv,R.id.regressionValue3Tv,R.id.regressionValue5Tv,R.id.regressionValue21Tv,R.id.regressionValue22Tv,R.id.regressionValue23Tv,R.id.regressionValue24Tv,R.id.regressionValue25Tv,R.id.regressionValue26Tv,R.id.regressionValue31Tv,R.id.regressionValue32Tv,R.id.regressionValue33Tv};
    private Integer[] regressionValueTvIds2 = {R.id.regressionValue4Tv,R.id.regressionValue6Tv,R.id.regressionValue7Tv,R.id.regressionValue8Tv,R.id.regressionValue34Tv};
    private Integer[] regressionValueTvIds3 = {R.id.regressionValue41Tv,R.id.regressionValue42Tv,R.id.regressionValue43Tv,R.id.regressionValue44Tv};
    private Integer[] plusLayoutIds = {R.id.plusfirst,R.id.plussecond,R.id.plusthird,R.id.plusforth};
    private String[] itemNames = {"샴푸","바디워시","치약","클렌징폼","토너","앰플","수분크림","향수","바디로션","선크림","주방세제","세탁세제","섬유유연제"};
    private String[] itemNames2 = {"칫솔","수건","샤워타월","면도기날","수세미"};
    private LinearLayout backLayout;
    private String userId;
    private String rebuyDay ="";
    private int i,tempNum=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifeitem);
        bottomNavigationView = findViewById(R.id.bottomNavigationView5);
        itemMenu = findViewById(R.id.itemMenu);
        mypageBtn = findViewById(R.id.mypageBtn);
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        userId = firebaseUser.getUid();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("self_life/UserData/" + userId + "/LifeItemData");
        lifeItemCategory = findViewById(R.id.lifeitem_category);
        seAn =findViewById(R.id.seAndogu);
        lifeitem = findViewById(R.id.lifeItem);
        hwajang = findViewById(R.id.hwajangdogu);
        otherItem = findViewById(R.id.OtherItem);
        input_LifeItem = findViewById(R.id.lifeitem_plus);
        page_input = findViewById(R.id.DaisoFl);
        backLayout = findViewById(R.id.backFl);
        MaxPlusEt = findViewById(R.id.MaxPlusEt);
        MinPlusEt = findViewById(R.id.MinPlusEt);
        NumPlusEt = findViewById(R.id.NumPlusEt);
        optionPlusBtn = findViewById(R.id.OptionPlusBtn);
        itemPlusBtn = findViewById(R.id.Write1Ll);

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
        for(i=0; i < regressionBtn2.length; i++){
            regressionBtn2[i] = findViewById(regressionBtnIds2[i]);
        }
        for(i=0; i < rebuyTv.length; i++){
            rebuyTv[i] = findViewById(rebuyTvIds[i]);
            rebuyTv[i].setVisibility(View.GONE);
        }
        for(i=0; i < rebuyTv2.length; i++){
            rebuyTv2[i] = findViewById(rebuyTvIds2[i]);
            rebuyTv2[i].setVisibility(View.GONE);
        }
        for(i=0; i < regressionValueTv.length; i++){
            regressionValueTv[i] = findViewById(regressionValueTvIds[i]);
        }
        for(i=0; i < regressionValueTv2.length; i++){
            regressionValueTv2[i] = findViewById(regressionValueTvIds2[i]);
        }
        for(i=0; i < MaxEt2.length; i++){
            MaxEt2[i] = findViewById(MaxEtIds2[i]);
        }
        for(i=0; i < optionBtn2.length; i++){
            final int index = i;
            optionBtn2[i] = findViewById(optionBtnIds2[i]);
            optionBtn2[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDateInputDialog2(index);
                }
            });
        }
        for(i=0; i < regressionBtn2.length; i++){
            final int index = i;
            regressionBtn2[i] = findViewById(regressionBtnIds2[i]);
            regressionBtn2[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rebuyTv2[index].setVisibility(View.VISIBLE);
                }
            });
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
                    rebuyTv[index].setVisibility(View.VISIBLE);
                }
            });
        }
        for(i=0; i < MaxEt3.length; i++){
            MaxEt3[i] = findViewById(MaxEtIds3[i]);
        }
        for(i=0; i < MinEt3.length; i++){
            MinEt3[i] = findViewById(MinEtIds3[i]);
        }

        for(i=0; i < NumEt3.length; i++){
            NumEt3[i] = findViewById(NumEtIds3[i]);
        }
        for(i=0; i < optionBtn3.length; i++){
            final int index = i;
            optionBtn3[i] = findViewById(optionBtnIds3[i]);
        }
        for(i=0; i < regressionBtn3.length; i++){
            regressionBtn3[i] = findViewById(regressionBtnIds3[i]);
        }
        for(i=0; i < rebuyTv3.length; i++){
            rebuyTv3[i] = findViewById(rebuyTvIds3[i]);
        }
        for(i=0; i < regressionValueTv3.length; i++){
            regressionValueTv3[i] = findViewById(regressionValueTvIds3[i]);
        }
        for(i=0; i < plusLayout.length; i++){
            plusLayout[i] = findViewById(plusLayoutIds[i]);
        }


        for (i = 0; i < itemNames.length; i++) {
            final int index = i;
            DatabaseReference mDatabaseRef2 = FirebaseDatabase.getInstance().getReference("self_life/UserData/" + userId + "/LifeItemData/" + itemNames[index]);
            mDatabaseRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        String rebuyDayStr = snapshot.child("RebuyDay").getValue(String.class);
                        Calendar calendar2 = Calendar.getInstance();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault());
                        String currentDateStr = dateFormat.format(calendar2.getTime());

                        try {
                            // RebuyDay와 시스템 날짜를 Date 형식으로 변환
                            Date rebuyDay = dateFormat.parse(rebuyDayStr);
                            Date currentDate = dateFormat.parse(currentDateStr);
                            long diffMillis = rebuyDay.getTime() - currentDate.getTime();
                            long diffDays = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);

                            if (currentDate.compareTo(rebuyDay) >= 0) {
                                mDatabaseRef2.removeValue();
                                rebuyTv[index].setVisibility(View.GONE);
                            }
                            else {
                                MaxEt[index].setText(snapshot.child("TotalCapacity").getValue(String.class));
                                MinEt[index].setText(snapshot.child("OnceCapacity").getValue(String.class));
                                NumEt[index].setText(snapshot.child("DailyUses").getValue(String.class));
                                rebuyTv[index].setText(diffDays + "일 뒤에 재구매 예정입니다. (" + rebuyDayStr + ")");
                                rebuyTv[index].setVisibility(View.VISIBLE);

                                if (diffDays <= 3) {
                                    rebuyTv[index].setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                                }
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // 에러 처리
                }
            });
        }

        for (i = 0; i < itemNames2.length; i++) {
            final int index = i;
            DatabaseReference mDatabaseRef2 = FirebaseDatabase.getInstance().getReference("self_life/UserData/" + userId + "/LifeItemData/" + itemNames2[index]);
            mDatabaseRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        String rebuyDayStr = snapshot.child("RebuyDay").getValue(String.class);
                        Calendar calendar3 = Calendar.getInstance();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault());
                        String currentDateStr2 = dateFormat.format(calendar3.getTime());

                        try {
                            // RebuyDay와 시스템 날짜를 Date 형식으로 변환
                            Date rebuyDay = dateFormat.parse(rebuyDayStr);
                            Date currentDate = dateFormat.parse(currentDateStr2);
                            long diffMillis = rebuyDay.getTime() - currentDate.getTime();
                            long diffDays = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);

                            if (currentDate.compareTo(rebuyDay) >= 0) {
                                mDatabaseRef2.removeValue();
                                rebuyTv2[index].setVisibility(View.GONE);
                            } else {
                                MaxEt2[index].setText(snapshot.child("TotalCapacity").getValue(String.class));
                                rebuyTv2[index].setText(diffDays + "일 뒤에 재구매 예정입니다. (" + rebuyDayStr + ")");
                                rebuyTv2[index].setVisibility(View.VISIBLE);

                                if (diffDays <= 3) {
                                    rebuyTv2[index].setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                                }
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // 에러 처리
                }
            });
        }

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("self_life/UserData/" + userId + "/LifeItemData/사용자정의");

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child("사용자정의4").exists()) {
                        String MaxEt = snapshot.child("사용자정의4").child("TotalCapacity").getValue(String.class);
                        String MinEt = snapshot.child("사용자정의4").child("OnceCapacity").getValue(String.class);
                        String NumEt = snapshot.child("사용자정의4").child("DailyUses").getValue(String.class);
                        String RebuyDay = snapshot.child("사용자정의4").child("RebuyDay").getValue(String.class);

                        Calendar calendar4 = Calendar.getInstance();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault());
                        String currentDateStr = dateFormat.format(calendar4.getTime());

                        try {
                            // RebuyDay와 시스템 날짜를 Date 형식으로 변환
                            Date rebuyDay = dateFormat.parse(RebuyDay);
                            Date currentDate = dateFormat.parse(currentDateStr);
                            long diffMillis = rebuyDay.getTime() - currentDate.getTime();
                            long diffDays = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);

                            if (currentDate.compareTo(rebuyDay) >= 0) {
                                DatabaseReference userPlusRef = snapshot.child("사용자정의4").getRef();
                                userPlusRef.removeValue();
                                plusLayout[3].setVisibility(View.GONE);
                                regressionBtn3[3].setVisibility(View.GONE);
                                rebuyTv3[3].setVisibility(View.GONE);
                            } else {
                                plusLayout[3].setVisibility(View.VISIBLE);
                                MaxEt3[3].setText(MaxEt);
                                MinEt3[3].setText(MinEt);
                                NumEt3[3].setText(NumEt);
                                regressionBtn3[3].setVisibility(View.VISIBLE);
                                rebuyTv3[3].setText(diffDays + "일 뒤에 재구매 예정입니다. (" + RebuyDay + ")");
                                rebuyTv3[3].setVisibility(View.VISIBLE);
                                if (diffDays <= 3) {
                                    rebuyTv3[3].setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                                }
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if(snapshot.child("사용자정의1").exists()) {
                        String MaxEt = snapshot.child("사용자정의1").child("TotalCapacity").getValue(String.class);
                        String MinEt = snapshot.child("사용자정의1").child("OnceCapacity").getValue(String.class);
                        String NumEt = snapshot.child("사용자정의1").child("DailyUses").getValue(String.class);
                        String RebuyDay = snapshot.child("사용자정의1").child("RebuyDay").getValue(String.class);

                        Calendar calendar4 = Calendar.getInstance();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault());
                        String currentDateStr = dateFormat.format(calendar4.getTime());

                        try {
                            // RebuyDay와 시스템 날짜를 Date 형식으로 변환
                            Date rebuyDay = dateFormat.parse(RebuyDay);
                            Date currentDate = dateFormat.parse(currentDateStr);
                            long diffMillis = rebuyDay.getTime() - currentDate.getTime();
                            long diffDays = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);

                            if (currentDate.compareTo(rebuyDay) >= 0) {
                                DatabaseReference userPlusRef = snapshot.child("사용자정의1").getRef();
                                userPlusRef.removeValue();
                                plusLayout[0].setVisibility(View.GONE);
                                regressionBtn3[0].setVisibility(View.GONE);
                                rebuyTv3[0].setVisibility(View.GONE);
                            } else {
                                plusLayout[0].setVisibility(View.VISIBLE);
                                MaxEt3[0].setText(MaxEt);
                                MinEt3[0].setText(MinEt);
                                NumEt3[0].setText(NumEt);
                                regressionBtn3[0].setVisibility(View.VISIBLE);
                                rebuyTv3[0].setText(diffDays + "일 뒤에 재구매 예정입니다. (" + RebuyDay + ")");
                                rebuyTv3[0].setVisibility(View.VISIBLE);
                                if (diffDays <= 3) {
                                    rebuyTv3[0].setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                                }
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if(snapshot.child("사용자정의2").exists()) {
                        String MaxEt = snapshot.child("사용자정의2").child("TotalCapacity").getValue(String.class);
                        String MinEt = snapshot.child("사용자정의2").child("OnceCapacity").getValue(String.class);
                        String NumEt = snapshot.child("사용자정의2").child("DailyUses").getValue(String.class);
                        String RebuyDay = snapshot.child("사용자정의2").child("RebuyDay").getValue(String.class);

                        Calendar calendar4 = Calendar.getInstance();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault());
                        String currentDateStr = dateFormat.format(calendar4.getTime());

                        try {
                            // RebuyDay와 시스템 날짜를 Date 형식으로 변환
                            Date rebuyDay = dateFormat.parse(RebuyDay);
                            Date currentDate = dateFormat.parse(currentDateStr);
                            long diffMillis = rebuyDay.getTime() - currentDate.getTime();
                            long diffDays = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);

                            if (currentDate.compareTo(rebuyDay) >= 0) {
                                DatabaseReference userPlusRef = snapshot.child("사용자정의2").getRef();
                                userPlusRef.removeValue();
                                plusLayout[1].setVisibility(View.GONE);
                                regressionBtn3[1].setVisibility(View.GONE);
                                rebuyTv3[1].setVisibility(View.GONE);
                            } else {
                                plusLayout[1].setVisibility(View.VISIBLE);
                                MaxEt3[1].setText(MaxEt);
                                MinEt3[1].setText(MinEt);
                                NumEt3[1].setText(NumEt);
                                regressionBtn3[1].setVisibility(View.VISIBLE);
                                rebuyTv3[1].setText(diffDays + "일 뒤에 재구매 예정입니다. (" + RebuyDay + ")");
                                rebuyTv3[1].setVisibility(View.VISIBLE);
                                if (diffDays <= 3) {
                                    rebuyTv3[1].setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                                }
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if(snapshot.child("사용자정의3").exists()) {
                        String MaxEt = snapshot.child("사용자정의3").child("TotalCapacity").getValue(String.class);
                        String MinEt = snapshot.child("사용자정의3").child("OnceCapacity").getValue(String.class);
                        String NumEt = snapshot.child("사용자정의3").child("DailyUses").getValue(String.class);
                        String RebuyDay = snapshot.child("사용자정의3").child("RebuyDay").getValue(String.class);

                        Calendar calendar4 = Calendar.getInstance();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault());
                        String currentDateStr = dateFormat.format(calendar4.getTime());

                        try {
                            // RebuyDay와 시스템 날짜를 Date 형식으로 변환
                            Date rebuyDay = dateFormat.parse(RebuyDay);
                            Date currentDate = dateFormat.parse(currentDateStr);
                            long diffMillis = rebuyDay.getTime() - currentDate.getTime();
                            long diffDays = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);

                            if (currentDate.compareTo(rebuyDay) >= 0) {
                                DatabaseReference userPlusRef = snapshot.child("사용자정의3").getRef();
                                userPlusRef.removeValue();
                                plusLayout[2].setVisibility(View.GONE);
                                regressionBtn3[2].setVisibility(View.GONE);
                                rebuyTv3[2].setVisibility(View.GONE);
                            } else {
                                plusLayout[2].setVisibility(View.VISIBLE);
                                MaxEt3[2].setText(MaxEt);
                                MinEt3[2].setText(MinEt);
                                NumEt3[2].setText(NumEt);
                                regressionBtn3[2].setVisibility(View.VISIBLE);
                                rebuyTv3[2].setText(diffDays + "일 뒤에 재구매 예정입니다. (" + RebuyDay + ")");
                                rebuyTv3[2].setVisibility(View.VISIBLE);
                                if (diffDays <= 3) {
                                    rebuyTv3[2].setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                                }
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        itemPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemPlus(MaxPlusEt.getText().toString(),MinPlusEt.getText().toString(),NumPlusEt.getText().toString(),rebuyDay);
            }
        });

        optionPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateInputDialog3();
            }
        });

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
                    otherItem.setVisibility(View.GONE);
                    return true;
                } else if (item.getItemId() == R.id.menu_item2) {
                    lifeItemCategory.setText("생활용품");
                    seAn.setVisibility(View.GONE);
                    lifeitem.setVisibility(View.VISIBLE);
                    hwajang.setVisibility(View.GONE);
                    otherItem.setVisibility(View.GONE);
                    return true;
                } else if (item.getItemId() == R.id.menu_item3) {
                    lifeItemCategory.setText("화장품");
                    seAn.setVisibility(View.GONE);
                    lifeitem.setVisibility(View.GONE);
                    hwajang.setVisibility(View.VISIBLE);
                    otherItem.setVisibility(View.GONE);
                    return true;
                } else if (item.getItemId() == R.id.menu_item4) {
                    lifeItemCategory.setText("기타");
                    seAn.setVisibility(View.GONE);
                    lifeitem.setVisibility(View.GONE);
                    hwajang.setVisibility(View.GONE);
                    otherItem.setVisibility(View.VISIBLE);
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

        SimpleDateFormat newdateFormat = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault());
        String lastUsageDateString = newdateFormat.format(lastUsageDate);


        // 시스템 날짜와 선택 날짜 간의 차이를 계산
        int daysDifference = getDaysDifference(Calendar.getInstance().getTime(),lastUsageDate) + 1;

        rebuyTv[index].setText(daysDifference + "일 뒤에 재구매 예정입니다. (" + lastUsageDateString +")");
        regressionValueTv[index].setText(lastUsageDateString);
        mDatabaseRef.child(itemNames[index]).child("TotalCapacity").setValue(MaxEt[index].getText().toString());
        mDatabaseRef.child(itemNames[index]).child("OnceCapacity").setValue(MinEt[index].getText().toString());
        mDatabaseRef.child(itemNames[index]).child("DailyUses").setValue(NumEt[index].getText().toString());
        mDatabaseRef.child(itemNames[index]).child("RebuyDay").setValue(lastUsageDateString);
    }

    private void calculateRegressionDate2(final int index, Date selectedDate) {
        double max = getNumericValueFromEditText(MaxEt2[index]);
        double daysLeft = 0;
        switch (index){
            case 0:
                daysLeft = max * 21;
                break;
            case 1:
                daysLeft = max * 365;
                break;
            case 2:
                daysLeft = max * 60;
                break;
            case 3:
                daysLeft = max * 60;
                break;
            case 4:
                daysLeft = max * 30;
                break;
            default:
                break;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate); // 선택한 날짜로 설정
        calendar.add(Calendar.DAY_OF_MONTH, (int) daysLeft);

        Date lastUsageDate = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault());
        String lastUsageDateString = dateFormat.format(lastUsageDate);

        // 시스템 날짜와 선택 날짜 간의 차이를 계산
        int daysDifference = getDaysDifference(Calendar.getInstance().getTime(),lastUsageDate) + 1;

        rebuyTv2[index].setText(daysDifference + "일 뒤에 재구매 예정입니다. (" + lastUsageDateString +")");
        regressionValueTv2[index].setText(lastUsageDateString);

        mDatabaseRef.child(itemNames2[index]).child("TotalCapacity").setValue(MaxEt2[index].getText().toString());
        mDatabaseRef.child(itemNames2[index]).child("RebuyDay").setValue(lastUsageDateString);

    }
    private void calculateRegressionDate3(Date selectedDate) {
        double max = getNumericValueFromEditText(MaxPlusEt);
        double min = getNumericValueFromEditText(MinPlusEt);
        double num = getNumericValueFromEditText(NumPlusEt);

        double daysLeft = max / (min * num);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate); // 선택한 날짜로 설정
        calendar.add(Calendar.DAY_OF_MONTH, (int) daysLeft);

        Date lastUsageDate = calendar.getTime();

        SimpleDateFormat newdateFormat = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault());
        String lastUsageDateString = newdateFormat.format(lastUsageDate);
        rebuyDay = lastUsageDateString;

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
            Toast.makeText(LifeItem_Activity.this, "올바른 값을 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
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

    private void showDateInputDialog2(final int index) {
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
                        optionBtn2[index].setText("선택완료");

                        // 선택된 날짜를 기반으로 재구매 날짜 예측
                        calculateRegressionDate2(index, selectedDate);
                    }
                }, year, month, day);

        // 다이얼로그 보이기
        datePickerDialog.show();
    }private void showDateInputDialog3() {
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
                        optionPlusBtn.setText("선택완료");

                        // 선택된 날짜를 기반으로 재구매 날짜 예측
                        calculateRegressionDate3(selectedDate);
                    }
                }, year, month, day);

        // 다이얼로그 보이기
        datePickerDialog.show();
    }
    private void itemPlus(String max, String min, String num, String rebuy){

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("self_life/UserData/" + userId + "/LifeItemData/사용자정의");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if(Integer.valueOf(dataSnapshot.child("Num").getValue(String.class))==tempNum){
                        tempNum++;
                    }
                    else{
                        break;
                    }
                }
                if(tempNum > 4){
                    Toast.makeText(LifeItem_Activity.this,"사용자 정의는 4개까지만 만들 수 있습니다.",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LifeItem_Activity.this, LifeItem_Activity.class);
                    startActivity(intent);
                    finish();
                } else {
                    userRef.child("사용자정의"+tempNum).child("Num").setValue(String.valueOf(tempNum));
                    userRef.child("사용자정의"+tempNum).child("TotalCapacity").setValue(String.valueOf(getNumericValueFromEditText(MaxPlusEt)));
                    userRef.child("사용자정의"+tempNum).child("OnceCapacity").setValue(String.valueOf(getNumericValueFromEditText(MinPlusEt)));
                    userRef.child("사용자정의"+tempNum).child("DailyUses").setValue(String.valueOf(getNumericValueFromEditText(NumPlusEt)) );
                    userRef.child("사용자정의"+tempNum).child("RebuyDay").setValue(rebuy);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Intent intent = new Intent(LifeItem_Activity.this, LifeItem_Activity.class);
        startActivity(intent);
        finish();
    }
}
