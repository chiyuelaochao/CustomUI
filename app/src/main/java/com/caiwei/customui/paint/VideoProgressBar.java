package com.caiwei.customui.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wei.cai on 2017/5/31.
 */

public class VideoProgressBar extends View {
    private final String TAG = getClass().getSimpleName();
    private Paint paintBack, circlePaint, controlPaint;
    private int start, end;
    private Path pathBack, pathControl;

    public VideoProgressBar(Context context) {
        this(context, null);
    }

    public VideoProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paintBack = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintBack.setStyle(Paint.Style.STROKE);
        paintBack.setColor(Color.GRAY);
        paintBack.setStrokeCap(Paint.Cap.ROUND);
        paintBack.setAlpha(127);
        paintBack.setStrokeWidth(30);

        controlPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        controlPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        controlPaint.setColor(Color.WHITE);
        controlPaint.setStrokeCap(Paint.Cap.ROUND);
        controlPaint.setStrokeWidth(32);

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        circlePaint.setColor(Color.WHITE);
        float[] direction = new float[]{1, 1, 1};// 设置x、y、z三个方向效果
        float light = 0.9f;// 设置环境光亮度
        float specular = 0.5f;// 选择要应用的反射等级
        float blur = 8;// 向mask应用一定级别的模糊
        EmbossMaskFilter emboss = new EmbossMaskFilter(direction, light, specular, blur);
        circlePaint.setMaskFilter(emboss);
//        circlePaint.setMaskFilter(new BlurMaskFilter(5, BlurMaskFilter.Blur.INNER));
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);     //关闭硬件加速

        pathBack = new Path();
        pathControl = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        canvas.translate(60, 60);
        start = 0;
        end = canvas.getWidth() - 200;
        pathBack.lineTo(end, 0);
//        RectF rectBack = new RectF(0, 0, end, 30);
//        pathBack.addRect(rectBack, Path.Direction.CW);
        canvas.drawPath(pathBack, paintBack);

        PathMeasure pathMeasure = new PathMeasure(pathBack, false);
        pathControl.reset();
        pathControl.moveTo(circlePosition, 0);
        pathControl.lineTo(end, 0);
        canvas.drawPath(pathControl, controlPaint);
        canvas.drawCircle(circlePosition, 0, 25, circlePaint);
        canvas.drawCircle(end, 0, 25, circlePaint);


        // draw progress control button
    }

    private float circlePosition = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "ACTION_DOWN:" + event.getX() + "," + event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "ACTION_MOVE:" + event.getX() + "," + event.getY());
                circlePosition = event.getX();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "ACTION_UP:" + event.getX() + "," + event.getY());
                break;
        }
        return true;
    }
}
