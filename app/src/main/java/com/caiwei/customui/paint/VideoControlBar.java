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
    private final static int OFFSET_X = 200;
    private final static int OFFSET_Y = 200;
    private final static int BUTTON_RADIUS = 30;

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
        float[] direction = new float[]{0, 4, 4};// 设置x、y、z三个方向效果
        float light = 0.6f;// 设置环境光亮度
        float specular = 0.3f;// 选择要应用的反射等级
        float blur = 4;// 向mask应用一定级别的模糊
        EmbossMaskFilter emboss = new EmbossMaskFilter(direction, light, specular, blur);
        circlePaint.setMaskFilter(emboss);
//        circlePaint.setMaskFilter(new BlurMaskFilter(5, BlurMaskFilter.Blur.INNER));
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);     //关闭硬件加速

        pathBack = new Path();
        pathControl = new Path();
        pathStartButton = new Path();
        pathEndButton = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        canvas.translate(OFFSET_X, OFFSET_Y);
        start = OFFSET_X;
        end = canvas.getWidth() - 200;
        if (endPosition == 0) {
            endPosition = end;
        }
        pathBack.lineTo(end, 0);

        canvas.drawPath(pathBack, paintBack);
        pathControl.reset();
        pathControl.moveTo(startPosition, 0);
        pathControl.lineTo(endPosition, 0);
        canvas.drawPath(pathControl, controlPaint);

        pathStartButton.reset();
        pathStartButton.addCircle(startPosition, 0, BUTTON_RADIUS, Path.Direction.CW);
        pathEndButton.reset();
        pathEndButton.addCircle(endPosition, 0, BUTTON_RADIUS, Path.Direction.CW);

        // draw progress control button
        canvas.drawPath(pathStartButton, circlePaint);
        canvas.drawPath(pathEndButton, circlePaint);
    }

    private float startPosition = 0;
    private float endPosition = 0;
    private Path pathStartButton, pathEndButton;
    private int buttonFlag;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                distance = 0;
                if (isInStartRegion(event.getX() - OFFSET_X, event.getY() - OFFSET_Y)) {
                    Log.e(TAG, "isInStartRegion");
                    buttonFlag = 1;
                    distance = event.getX() - this.startPosition;
                } else if (isInEndRegion(event.getX() - OFFSET_X, event.getY() - OFFSET_Y)) {
                    Log.e(TAG, "isInEndRegion");
                    buttonFlag = 2;
                    distance = event.getX() - this.endPosition;
                } else {
                    buttonFlag = 0;
                    Log.e(TAG, "nothing");
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getX() < end) {
                    switch (buttonFlag) {
                        case 1:
                            Log.e("123", "event.getX() = " + event.getX());
                            Log.e("123", "distance = " + distance);
                            Log.e("123", "start = " + start);
                            if (event.getX() + distance >= start && event.getX() < endPosition) {
                                setStartPosition(event.getX());
                                if (listener != null) {
                                    listener.onProgressChange(this.startPosition, this.endPosition);
                                }
                                Log.e("123", "startPosition = " + event.getX());
                                Log.e("123", "endPosition = " + endPosition);
                            } else {
                                Log.e("123", "setStartPosition cant ");
                            }
                            break;
                        case 2:
                            if (event.getX()- distance <= end && event.getX()  > startPosition) {
                                setEndPosition(event.getX());
                                if (listener != null) {
                                    listener.onProgressChange(this.startPosition, this.endPosition);
                                }
                            } else {
                                Log.e("123", "setEndPosition cant ");
                            }
                            break;
                        default:
                            break;
                    }
                    invalidate();
                } else {
                    Log.e(TAG, "Position numbers are out of limits!");
                }
                break;
            case MotionEvent.ACTION_UP:
                buttonFlag = 0;
                distance = 0;
                break;
        }
        return true;
    }

    private boolean isInStartRegion(float x, float y) {
        RectF bounds = new RectF();
        pathStartButton.computeBounds(bounds, true);
        Region region = new Region();
        region.setPath(pathStartButton, new Region((int) bounds.left, (int) bounds.top, (int) bounds.right, (int) bounds.bottom));
        return region.contains((int) x, (int) y);
    }

    private boolean isInEndRegion(float x, float y) {
        RectF bounds = new RectF();
        pathEndButton.computeBounds(bounds, true);
        Region region = new Region();
        region.setPath(pathEndButton, new Region((int) bounds.left, (int) bounds.top, (int) bounds.right, (int)
                bounds.bottom));
        return region.contains((int) x, (int) y);
    }

    public float getStartPosition() {
        return startPosition;
    }

    private float distance;

    public void setStartPosition(float startPosition) {
        this.startPosition = startPosition - distance;
    }

    public float getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(float endPosition) {
        this.endPosition = endPosition - distance;
    }

    public interface ProgressChangeListener {
        void onProgressChange(float startPosition, float endPosition);
    }

    public void setOnProgressChangeListener(ProgressChangeListener listener) {
        this.listener = listener;
    }

    ProgressChangeListener listener;
}