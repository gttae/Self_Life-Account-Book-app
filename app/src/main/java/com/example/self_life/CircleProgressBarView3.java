package com.example.self_life;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CircleProgressBarView3 extends View {

    private int segmentCount = 2;
    private float[] segmentValues = {73,27}; // 각 구간의 값 (총 합은 100이 되어야 함)

    private Paint[] segmentPaints;
    private RectF rectF;

    private int[] colors = {Color.RED, Color.BLUE}; // 다양한 색상 배열

    public CircleProgressBarView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleProgressBarView3(Context context) {
        super(context);
        init();
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        float centerX = width / 2f;
        float centerY = height / 2f;
        float radius = Math.min(width, height) / 2f - 15;

        float totalValue = 100; // 총 값은 100이라 가정
        float startAngle = -90; // 시작 각도

        for (int i = 0; i < segmentCount; i++) {
            float sweepAngle = (segmentValues[i] / totalValue) * 360;

            rectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
            canvas.drawArc(rectF, startAngle, sweepAngle, true, segmentPaints[i]);

            startAngle += sweepAngle; // 다음 구간의 시작 각도를 조정
        }
    }
}
