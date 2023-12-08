package com.example.madassignment.Expenses;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class LineGraph extends View {
    private ArrayList<Float> dataPoints;
    private Paint linePaint;
    private Paint axisPaint;
    private ArrayList<String> monthLabels;

    private float marginStart = 60; // Adjust as needed
    private float marginTop = 20; // Adjust as needed
    private float marginEnd = 20; // Adjust as needed
    private float marginBottom = 60; // Adjust as needed

    public LineGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
        dataPoints = new ArrayList<>();
        linePaint = new Paint();
        linePaint.setColor(Color.BLUE);
        linePaint.setStrokeWidth(5);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);

        axisPaint = new Paint();
        axisPaint.setColor(Color.BLACK);
        axisPaint.setStrokeWidth(2);
        axisPaint.setStyle(Paint.Style.STROKE);
        axisPaint.setAntiAlias(true);

        monthLabels = new ArrayList<>();
        // Add month labels (adjust as needed)
        monthLabels.add("Jan");
        monthLabels.add("Feb");
        monthLabels.add("Mar");
        monthLabels.add("Apr");
        monthLabels.add("May");
        monthLabels.add("Jun");
        monthLabels.add("Jul");
        monthLabels.add("Aug");
        monthLabels.add("Sep");
        monthLabels.add("Oct");
        monthLabels.add("Nov");
        monthLabels.add("Dec");
    }

    public void setData(ArrayList<Float> points) {
        dataPoints = points;
        invalidate(); // Trigger redraw
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int numMonths = monthLabels.size();

        if (numMonths < 2) {
            return; // Need at least two months to draw a line
        }

        float width = getWidth();
        float height = getHeight();
        float maxX = numMonths - 1;
        float maxY = getMaxValue(dataPoints);

        float xInterval = (width - marginStart - marginEnd) / maxX; // Adjusted for margins
        float yScale = (height - marginTop - marginBottom) / maxY; // Adjusted for margins

        Path path = new Path();

        for (int i = 0; i < numMonths; i++) {
            float x = marginStart + i * xInterval;

            // Draw a line if there's data for the month
            if (i < dataPoints.size()) {
                float dataPoint = dataPoints.get(i);
                float yPos = height - dataPoint * yScale;

                if (i == 0) {
                    path.moveTo(x, yPos); // Move to the starting point for the first data point
                } else {
                    path.lineTo(x, yPos);
                }
            }

            // Draw X-axis labels
            float labelY = height - marginBottom + 20; // Adjusted for margins
            canvas.drawText(monthLabels.get(i), x, labelY, axisPaint);
        }

        // Draw X-axis title
        String xAxisTitle = "Months";
        float xAxisTitleX = marginStart + (width - marginStart - marginEnd) / 2;
        float xAxisTitleY = height - 10; // Adjusted for margins
        canvas.drawText(xAxisTitle, xAxisTitleX, xAxisTitleY, axisPaint);

        // Draw X-axis
        canvas.drawLine(marginStart, height - marginBottom, width - marginEnd, height - marginBottom, axisPaint); // X-axis

        // Draw Y-axis labels
        for (float yLabel = 0; yLabel <= maxY; yLabel += maxY / 5) {
            float y = height - marginBottom - yLabel * yScale;
            float labelX = marginStart - 40; // Adjusted for margins
            canvas.drawText(String.valueOf((int) yLabel), labelX, y, axisPaint);
        }

        // Draw Y-axis title
        String yAxisTitle = "com/example/madassignment/Expenses";
        float yAxisTitleX = 10; // Adjusted for margins
        float yAxisTitleY = marginTop;
        canvas.drawText(yAxisTitle, yAxisTitleX, yAxisTitleY, axisPaint);

        // Draw Y-axis
        canvas.drawLine(marginStart, marginTop, marginStart, height - marginBottom, axisPaint); // Y-axis

        canvas.drawPath(path, linePaint);
    }




    private float getMaxValue(ArrayList<Float> values) {
        float max = Float.MIN_VALUE;
        for (float value : values) {
            max = Math.max(max, value);
        }
        return max;
    }
}
