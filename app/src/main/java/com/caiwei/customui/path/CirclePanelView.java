package com.caiwei.customui.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wei.cai on 2017/5/29.
 */

public class CirclePanelView extends View {
    private int circleCount = 12;
    private int spacing = 30;
    private int lineCount = 108;
    private float angle = (float) (Math.PI * 2 / lineCount);
    private float radius;                   //最大半径
    private int centerX, centerY;           //中心X,中心Y
    private Paint panelPaint;

    public CirclePanelView(Context context) {
        this(context, null);
    }

    public CirclePanelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        panelPaint = new Paint();
        panelPaint.setColor(Color.WHITE);
        panelPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius = Math.min(h, w) / 2 * 0.9f;
        //中心坐标
        centerX = w / 2;
        centerY = h / 2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);
        //方法1
//        drawCircles(canvas);
//        drawLines(canvas);

        //方法2
        drawPathList(canvas);
    }

    private void drawCircles(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < circleCount; i++) {
            float curR = radius - (i * spacing);//当前半径
            path.reset();
            path.addCircle(centerX, centerY, curR, Path.Direction.CW);
            path.close();//闭合路径
            canvas.drawPath(path, panelPaint);
        }
    }

    /**
     * 绘制直线,根据半径，计算出每个末端坐标
     */
    private void drawLines(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < lineCount; i++) {
            path.reset();
            float x0 = (float) (centerX + (radius - (circleCount - 1) * spacing) * Math.cos(angle * i));
            float y0 = (float) (centerY + (radius - (circleCount - 1) * spacing) * Math.sin(angle * i));
            path.moveTo(x0, y0);
            float x = (float) (centerX + radius * Math.cos(angle * i));
            float y = (float) (centerY + radius * Math.sin(angle * i));
            path.lineTo(x, y);
            canvas.drawPath(path, panelPaint);
        }
    }

    private List<Path> pathList = new ArrayList<>();

    private void drawPathList(Canvas canvas) {
        for (int i = 0; i < lineCount; i++) {
            for (int j = 1; j <= circleCount; j++) {
                Path path = new Path();
                path.reset();
                float x1 = (float) (centerX + (radius - (circleCount - j) * spacing) * Math.cos(angle * i));
                float y1 = (float) (centerY + (radius - (circleCount - j) * spacing) * Math.sin(angle * i));
                path.moveTo(x1, y1);

                float x2 = (float) (centerX + (radius - (circleCount - j) * spacing) * Math.cos(angle * (i + 1)));
                float y2 = (float) (centerY + (radius - (circleCount - j) * spacing) * Math.sin(angle * (i + 1)));
                path.lineTo(x2, y2);

                float x3 = (float) (centerX + (radius - (circleCount - j + 1) * Math.cos(angle * (i + 1))));
                float y3 = (float) (centerY + (radius - (circleCount - j + 1) * Math.sin(angle * (i + 1))));
                path.lineTo(x3, y3);

//                float x4 = (float) (centerX + (radius - (circleCount - j - 1) * Math.cos(angle * i)));
//                float y4 = (float) (centerY + (radius - (circleCount - j - 1) * Math.sin(angle * i)));
//                path.lineTo(x4, y4);

                path.close();
                pathList.add(path);
                canvas.drawPath(path, panelPaint);
            }
        }
    }

    /*private void drawPathList(Canvas canvas) {
        for (int i = 0; i < lineCount; i++) {
            for (int j = 1; j <= circleCount; j++) {
                Path path = new Path();
                path.reset();
                float x1 = (float) (centerX + (radius - (circleCount - j) * spacing) * Math.cos(angle * i));
                float y1 = (float) (centerY + (radius - (circleCount - j) * spacing) * Math.sin(angle * i));
                path.moveTo(x1, y1);

                float x2 = (float) (centerX + (radius - (circleCount - j) * spacing) * Math.cos(angle * (i + 1)));
                float y2 = (float) (centerY + (radius - (circleCount - j) * spacing) * Math.sin(angle * (i + 1)));
                path.lineTo(x2, y2);

                float x3 = (float) (centerX + (radius - (circleCount - j - 1) * Math.cos(angle * (i + 1))));
                float y3 = (float) (centerY + (radius - (circleCount - j - 1) * Math.sin(angle * (i + 1))));
                path.lineTo(x3, y3);

                float x4 = (float) (centerX + (radius - (circleCount - j - 1) * Math.cos(angle * i)));
                float y4 = (float) (centerY + (radius - (circleCount - j - 1) * Math.sin(angle * i)));
                path.lineTo(x4, y4);

                path.close();
                pathList.add(path);
                canvas.drawPath(path, panelPaint);
            }
        }
    }*/


}