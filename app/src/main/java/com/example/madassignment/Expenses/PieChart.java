package com.example.madassignment.Expenses;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class PieChart extends View {
    private ArrayList<Float> dataValues;
    private ArrayList<Integer> colors;
    private float holeRadiusPercentage = 0.3f; // Adjust this percentage for the size of the hole

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        dataValues = new ArrayList<>();
        colors = new ArrayList<>();
    }

    public void setData(ArrayList<Float> values, ArrayList<Integer> sliceColors) {
        dataValues = values;
        colors = sliceColors;
        invalidate(); // Trigger redraw
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (dataValues.isEmpty() || colors.isEmpty()) {
            return;
        }

        float total = 0;

        for (float value : dataValues) {
            total += value;
        }

        float startAngle = 0;

        RectF rectF = new RectF(0, 0, getWidth(), getHeight());

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        float holeRadius = Math.min(getWidth(), getHeight()) * holeRadiusPercentage;

        for (int i = 0; i < dataValues.size(); i++) {
            float sweepAngle = 360 * (dataValues.get(i) / total);

            // Draw the outer arc
            paint.setColor(colors.get(i));
            canvas.drawArc(rectF, startAngle, sweepAngle, true, paint);

            // Draw the inner arc to create the hole (donut)
            paint.setColor(Color.WHITE); // Set the color for the inner circle
            canvas.drawCircle(rectF.centerX(), rectF.centerY(), holeRadius, paint);

            startAngle += sweepAngle;
        }
    }
}
