package com.example.self_life;

import static com.example.self_life.CircleProgressBarView.calculateArraySum;
import static com.example.self_life.YearMonth_Value.MonthValue;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CircleProgressBarView2 extends View {

    private int segmentCount = 11;
    private float[] segmentValues = new float[segmentCount];

    private Paint[] segmentPaints;
    private RectF rectF;

    private int[] colors = {Color.parseColor("#EB385A"), Color.parseColor("#FA8231"), Color.parseColor("#FED330"), Color.parseColor("#2BCBBA"), Color.parseColor("#45AAF2"), Color.parseColor("#3867D6"), Color.parseColor("#A65EEA"), Color.parseColor("#E99386"), Color.parseColor("#E84493"), Color.parseColor("#A29BFE"), Color.parseColor("#00B894")};


    public CircleProgressBarView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        fetchDataFromFirebase();
    }

    public CircleProgressBarView2(Context context) {
        super(context);
        init();
        fetchDataFromFirebase();
    }

    private void init() {
        segmentPaints = new Paint[segmentCount];
        for (int i = 0; i < segmentCount; i++) {
            segmentPaints[i] = new Paint();
            segmentPaints[i].setColor(colors[i % colors.length]); // 다양한 색상 배열에서 색상 선택
            segmentPaints[i].setStyle(Paint.Style.FILL);
        }

        rectF = new RectF();
    }

    private void fetchDataFromFirebase() {
        // Firebase Database 인스턴스 생성
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        String userId;
        userId = firebaseUser.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String monthString = MonthValue + "월";
        String monthexpense = monthString + "지출";
        DatabaseReference fundDataRef = database.getReference("self_life/UserData/"+userId+"/FundData/"+monthexpense);

        fundDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // 배열 초기화
                for (int i = 0; i < segmentCount; i++) {
                    segmentValues[i] = 0;
                }
                if(dataSnapshot.exists()) {
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
                }
                else{
                    segmentValues[0] = 1;
                    for (int i = 1; i < segmentCount; i++) {
                        segmentValues[i] = 0;
                    }
                }
                updateData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 여기서 오류 처리
            }
        });
    }

    private int getSegmentIndexByDivision(String fundDivision) {
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

    private void updateData() {
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        float centerX = width / 2f;
        float centerY = height / 2f;
        float radius = Math.min(width, height) / 2f - 40;

        float totalSegmentValue = calculateArraySum(segmentValues);
        float totalValue = totalSegmentValue;
        float startAngle = -90; // 시작 각도

        if(totalValue < 2) {
            rectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
            Paint greyPaint = new Paint();
            greyPaint.setColor(Color.GRAY);
            greyPaint.setStyle(Paint.Style.FILL);
            canvas.drawArc(rectF, startAngle, 360, true, greyPaint);
        } else {
            for (int i = 0; i < segmentCount; i++) {
                float sweepAngle = (segmentValues[i] / totalValue) * 360;

                rectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
                canvas.drawArc(rectF, startAngle, sweepAngle, true, segmentPaints[i]);

                startAngle += sweepAngle; // 다음 구간의 시작 각도를 조정
            }
        }
    }
}
