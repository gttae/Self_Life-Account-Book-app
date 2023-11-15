package com.example.self_life;

import static com.example.self_life.YearMonth_Value.MonthValue;
import static com.example.self_life.YearMonth_Value.getCurrentMonth;

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

public class CircleProgressBarView extends View {

    private int segmentCount = 11;
    private float[] segmentValues = new float[segmentCount];
    private float[] usedValues = new float[segmentCount];
    public int monthValue = getCurrentMonth();

    private Paint[] usedPaints;
    private Paint remainingPaint;
    private RectF rectF;

    private int[] colors = {Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.DKGRAY, Color.WHITE, Color.BLACK, Color.CYAN, Color.RED, Color.GRAY};

    public CircleProgressBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        fetchDataFromFirebase(MonthValue);// Firebase에서 데이터 가져오기
    }

    public CircleProgressBarView(Context context) {
        super(context);
        init();
        fetchDataFromFirebase(MonthValue); // Firebase에서 데이터 가져오기
    }



    private void init() {
        int grayColor = Color.parseColor("#CCCCCC");
        remainingPaint = new Paint();
        remainingPaint.setColor(grayColor);
        remainingPaint.setStyle(Paint.Style.STROKE);
        remainingPaint.setStrokeWidth(10);

        usedPaints = new Paint[segmentCount];
        for (int i = 0; i < segmentCount; i++) {
            usedPaints[i] = new Paint();
            usedPaints[i].setColor(colors[i % colors.length]);  // 이 부분을 수정
            usedPaints[i].setStyle(Paint.Style.STROKE);
            usedPaints[i].setStrokeWidth(10);
        }


        rectF = new RectF();
    }

    private void fetchDataFromFirebase(int monthValue) {
        // Firebase Database 인스턴스 생성
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        String userId;
        userId = firebaseUser.getUid();
        String monthString = monthValue + "월";
        String monthexpense = monthString + "지출";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference fundDataRef = database.getReference("self_life/UserData/"+userId+"/FundData/"+monthexpense);

        fundDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // 배열 초기화
                for (int i = 0; i < segmentCount; i++) {
                    segmentValues[i] = 0;
                    usedValues[i] = 0;
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
                        } else if ("고정(실사용)".equals(category) || "유동".equals(category)) {
                            usedValues[segmentIndex] += price;
                        }
                    }
                }

                // 새 데이터로 뷰 업데이트
                updateData();
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

    public void updateData() {
        // 뷰를 다시 그리도록 invalidate()를 호출하여 업데이트된 데이터로 뷰를 그립니다.
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        float centerX = width / 2f;
        float centerY = height / 2f;
        float radius = Math.min(width, height) / 2f - 20;

        float totalSegmentValue = calculateArraySum(segmentValues);
        float totalValue = totalSegmentValue;
        float startAngle = -90;

        for (int i = 0; i < segmentCount; i++) {
            float remainingSweepAngle = (segmentValues[i] / totalValue) * 360;
            float usedSweepAngle = (segmentValues[i] != 0) ? (usedValues[i] / segmentValues[i]) * remainingSweepAngle : 0;

            rectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

            // 남은 부분 그리기
            canvas.drawArc(rectF, startAngle, remainingSweepAngle, false, usedPaints[i]);

            // 사용된 부분 그리기
            if (usedSweepAngle > 0) {
                canvas.drawArc(rectF, startAngle, usedSweepAngle, false, remainingPaint);
            }

            startAngle += remainingSweepAngle;
        }
    }


    public static float calculateArraySum(float[] array) {
        float sum = 0;
        for (float value : array) {
            sum += value;
        }
        return sum;
    }
}
