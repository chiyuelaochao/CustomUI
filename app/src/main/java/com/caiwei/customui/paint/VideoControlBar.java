package com.caiwei.customui.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wei.cai on 2017/5/31.
 * https://www.diycode.cc/topics/429
 */

public class VideoControlBar extends View {
    private final String TAG = getClass().getSimpleName();
    private Paint paintBack, circlePaint, controlPaint;
    private int start, end;
    private Path pathBack, pathControl;

    public VideoControlBar(Context context) {
        this(context, null);
    }

    public VideoControlBar(Context context, AttributeSet attrs) {
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
        float[] direction = new float[]{0, 6, 6};// 设置x、y、z三个方向效果
        float light = 0.9f;// 设置环境光亮度
        float specular = 0.5f;// 选择要应用的反射等级
        float blur = 8;// 向mask应用一定级别的模糊
        EmbossMaskFilter emboss = new EmbossMaskFilter(direction, light, specular, blur);
        circlePaint.setMaskFilter(emboss);
//        circlePaint.setMaskFilter(new BlurMaskFilter(5, BlurMaskFilter.Blur.INNER));
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);     //关闭硬件加速

        pathBack = new Path();
        pathControl = new Path();
        pathStartButton = new Path();
        pathEndButton = new Path();
        startRegion = new Region();
        endRegion = new Region();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        canvas.translate(60, 200);
        start = 0;
        end = canvas.getWidth() - 200;
        pathBack.lineTo(end, 0);

        canvas.drawPath(pathBack, paintBack);
        pathControl.reset();
        pathControl.moveTo(startPosition, 0);
        pathControl.lineTo(endPosition == 0 ? end : endPosition, 0);
        canvas.drawPath(pathControl, controlPaint);

        pathStartButton.reset();
        pathStartButton.addCircle(startPosition, 0, 40, Path.Direction.CW);
        pathStartButton.close();
        pathEndButton.reset();
        pathEndButton.addCircle(endPosition == 0 ? end : endPosition, 0, 40, Path.Direction.CW);
        pathEndButton.close();

        // draw progress control button
        canvas.drawPath(pathStartButton, circlePaint);
        canvas.drawPath(pathEndButton, circlePaint);
    }

    private float startPosition = 0;
    private float endPosition = 0;
    private Path pathStartButton, pathEndButton;
    private Region startRegion, endRegion;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "ACTION_DOWN:" + event.getX() + "," + event.getY());
                if (isInStartRegion(event.getX(), event.getY())) {
                    Log.e(TAG, "isInStartRegion");
                } else if (isInEndRegion(event.getX(), event.getY())) {
                    Log.e(TAG, "isInEndRegion");
                } else {
                    Log.e(TAG, "nothing");
                }
                break;
            case MotionEvent.ACTION_MOVE:
//                Log.e(TAG, "ACTION_MOVE:" + event.getX() + "," + event.getY());
                if (event.getX() < end) {
                    setStartPosition(event.getX());
//                    setEndPosition(event.getX() + 300);
                    invalidate();
                    if (listener != null) {
                        listener.onProgressChange(this.startPosition, this.getEndPosition());
                    }
                } /*else {
                    Log.e(TAG, "Position numbers are out of limits!");
                }*/
                break;
            case MotionEvent.ACTION_UP:
//                Log.e(TAG, "ACTION_UP:" + event.getX() + "," + event.getY());
                break;
        }
        return true;
    }

//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        Region globalRegion = new Region(-w, -h, w, h); // 将剪裁边界设置为视图大小
//        startRegion.setPath(pathStartButton, globalRegion); // 将 Path 添加到 Region 中
//        endRegion.setPath(pathEndButton, globalRegion);
//    }

    private boolean isInStartRegion(float x, float y) {
//        return startRegion.contains((int) x, (int) y);
        RectF bounds = new RectF();
        pathStartButton.computeBounds(bounds, true);
        Region region = new Region();
        region.setPath(pathStartButton, new Region((int) bounds.left, (int) bounds.top, (int) bounds.right, (int) bounds.bottom));
        return region.contains((int) x, (int) y);
    }

    private boolean isInEndRegion(float x, float y) {
//        return endRegion.contains((int) x, (int) y);
        RectF bounds = new RectF();
        pathEndButton.computeBounds(bounds, true);
        Region region = new Region();
        region.setPath(pathEndButton, new Region((int) bounds.left, (int) bounds.top, (int) bounds.right, (int) bounds.bottom));
        return region.contains((int) x, (int) y);
    }

    public float getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(float startPosition) {
        this.startPosition = startPosition;
    }

    public float getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(float endPosition) {
        this.endPosition = endPosition;
    }

    public interface ProgressChangeListener {
        void onProgressChange(float startPosition, float endPosition);
    }

    public void setOnProgressChangeListener(ProgressChangeListener listener) {
        this.listener = listener;
    }

    ProgressChangeListener listener;
}
