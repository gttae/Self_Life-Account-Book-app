package com.example.self_life;

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

public class CircleProgressBarView3 extends View {

    private int segmentCount = 2;
    private float[] segmentValues = new float[segmentCount];

    private Paint[] segmentPaints;
    private RectF rectF;

    private int[] colors = {Color.RED, Color.BLUE}; // 다양한 색상 배열

    public CircleProgressBarView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        fetchDataFromFirebase(); // Firebase에서 데이터 가져오기
    }

    public CircleProgressBarView3(Context context) {
        super(context);
        init();
        fetchDataFromFirebase(); // Firebase에서 데이터 가져오기
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
        String userId = firebaseUser.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String monthString = MonthValue + "월";
        DatabaseReference incomeRef = database.getReference("self_life/UserData/" + userId + "/FundData/"+monthString+"수입");
        DatabaseReference expenseRef = database.getReference("self_life/UserData/" + userId + "/FundData/"+monthString+"지출");
        incomeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    float incomeTotal = 0;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String category = snapshot.child("Category").getValue(String.class);
                        if ("실수령".equals(category)) {
                            float price = Float.valueOf(snapshot.child("Price").getValue(String.class));
                            incomeTotal += price;
                        }
                    }
                    segmentValues[0] = incomeTotal;
                } else {
                    segmentValues[0] = 1.0f;
                }

                // 데이터 업데이트
                updateData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 처리 중 에러가 발생한 경우
            }
        });

        expenseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    float expenseTotal = 0;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String category = snapshot.child("Category").getValue(String.class);
                        if ("실사용".equals(category)) {
                            float price = Float.valueOf(snapshot.child("Price").getValue(String.class));
                            expenseTotal += price;
                        }
                    }
                    segmentValues[1] = expenseTotal;
                } else {
                    segmentValues[1] = 1.0f;
                }

                // 데이터 업데이트
                updateData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 처리 중 에러가 발생한 경우
            }
        });
    }

    private void updateData() {
        // 데이터를 기반으로 segmentValues를 업데이트합니다.
        // 이후 View를 다시 그리도록 invalidate()를 호출합니다.
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        float centerX = width / 2f;
        float centerY = height / 2f;
        float radius = Math.min(width, height) / 2f - 15;

        float totalValue = segmentValues[0] + segmentValues[1]; // 총 값은 두 세그먼트의 합
        float startAngle = -90; // 시작 각도

        if(totalValue <= 2){
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

                startAngle += sweepAngle; // 다음 세그먼트의 시작 각도를 조정
            }
        }
    }
}
