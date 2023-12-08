package com.example.madassignment.Expenses;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CircularProgressBar extends View {
    public CircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Initialization code goes here
    }

    float progress;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        int centerX = (width + paddingLeft) / 2;
        int centerY = (height + paddingTop) / 2;

        int radius = Math.min(width, height) / 2;

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#d9d9d9")); // Set your desired color
        paint.setStrokeWidth(50); // Set the stroke width
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);

        progress = 0.8f; // Set your progress value (0 to 1)

        RectF rectF = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        canvas.drawArc(rectF, -90, progress * 360, false, paint);
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

}
