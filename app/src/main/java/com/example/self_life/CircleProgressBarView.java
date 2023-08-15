package com.example.self_life;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CircleProgressBarView extends View {

    private int segmentCount = 5;
    private float[] segmentValues = {20, 20, 20, 20, 20}; // 각 구간의 값 (총 합은 100이 되어야 함)
    private float[] usedValues = {5, 15, 7, 20, 5}; // 각 구간에서 사용된 값

    private Paint[] usedPaints;
    private Paint remainingPaint;
    private RectF rectF;

    private int[] colors = {Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW, Color.MAGENTA}; // 다양한 색상 배열

    public CircleProgressBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleProgressBarView(Context context) {
        super(context);
        init();
    }

    private void init() {
        int grayColor = Color.parseColor("#80CCCCCC"); // 50% 투명한 회색
        remainingPaint = new Paint();
        remainingPaint.setColor(grayColor);
        remainingPaint.setStyle(Paint.Style.STROKE); // 원형에서 링 형태로 변경
        remainingPaint.setStrokeWidth(10);

        usedPaints = new Paint[segmentCount];
        for (int i = 0; i < segmentCount; i++) {
            usedPaints[i] = new Paint();
            usedPaints[i].setColor(colors[i % colors.length]); // 다양한 색상 배열에서 색상 선택
            usedPaints[i].setStyle(Paint.Style.STROKE); // 원형에서 링 형태로 변경
            usedPaints[i].setStrokeWidth(10); // 링 두께 조절
        }

        rectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        float centerX = width / 2f;
        float centerY = height / 2f;
        float radius = Math.min(width, height) / 2f - 20; // 중앙으로부터의 거리 조정

        float totalValue = 100; // 총 값은 100이라 가정
        float startAngle = -90; // 시작 각도

        for (int i = 0; i < segmentCount; i++) {
            float remainingSweepAngle = (segmentValues[i] / totalValue) * 360;
            float usedSweepAngle = (usedValues[i] / segmentValues[i]) * remainingSweepAngle;

            // Draw remaining part
            rectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
            canvas.drawArc(rectF, startAngle, remainingSweepAngle, false, remainingPaint); // 라인 스타일 변경

            // Draw used part
            canvas.drawArc(rectF, startAngle, usedSweepAngle, false, usedPaints[i]); // 라인 스타일 변경

            startAngle += remainingSweepAngle; // 다음 구간의 시작 각도를 조정
        }
    }
}
