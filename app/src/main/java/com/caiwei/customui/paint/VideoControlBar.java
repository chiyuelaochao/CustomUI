package com.caiwei.customui.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.caiwei.customui.R;

/**
 * Created by wei.cai on 2017/5/31.
 * https://www.diycode.cc/topics/429
 */

public class VideoControlBar extends View {
    private final String TAG = getClass().getSimpleName();
    private Paint paintBack, circlePaint, controlPaint;
    private int minValue, maxValue;
    private Path pathBack, pathControl;
    private final static int BUTTON_RADIUS = 30;
    private final static int OFFSET_X = BUTTON_RADIUS;
    private final static int OFFSET_Y = BUTTON_RADIUS;

    public VideoControlBar(Context context) {
        this(context, null);
    }

    public VideoControlBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Bitmap barSkin = BitmapFactory.decodeResource(getResources(), R.drawable.ic_bar_skin, null);

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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = 0;
        int measureHeight = 0;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            measureWidth = widthSize;
            measureHeight = heightSize;
        } else {
            measureWidth = getMeasuredWidth();
            measureHeight = BUTTON_RADIUS * 2;
        }

        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(OFFSET_X, OFFSET_Y);
        minValue = OFFSET_X;
        maxValue = canvas.getWidth() - BUTTON_RADIUS * 2;
//        if (startPosition == 0) {
//            startPosition = minValue;
//        }
//        if (endPosition == 0) {
//            endPosition = maxValue;
//        }
        if (endPosition == 0) {
            endPosition = maxValue;
        }

        // draw progress control bar back
        pathBack.lineTo(maxValue, 0);
        canvas.drawPath(pathBack, paintBack);

        // draw progress control bar
        pathControl.reset();
        pathControl.moveTo(startPosition, 0);
        pathControl.lineTo(endPosition, 0);
        canvas.drawPath(pathControl, controlPaint);

        // draw progress control bar button
        pathStartButton.reset();
        pathStartButton.addCircle(startPosition, 0, BUTTON_RADIUS, Path.Direction.CW);
        pathEndButton.reset();
        pathEndButton.addCircle(endPosition, 0, BUTTON_RADIUS, Path.Direction.CW);
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
                    buttonFlag = 1;
                    distance = event.getX() - this.startPosition;
                    Log.d(TAG, "isInStartRegion, distance = " + distance
                            + ", event.getX() = " + event.getX()
                            + ", startPosition = " + startPosition
                    );
                } else if (isInEndRegion(event.getX() - OFFSET_X, event.getY() - OFFSET_Y)) {
                    buttonFlag = 2;
                    distance = event.getX() - this.endPosition;
                    Log.d(TAG, "isInEndRegion, distance = " + distance
                            + ", event.getX() = " + event.getX()
                            + ", endPosition = " + endPosition
                    );
                } else {
                    buttonFlag = 0;
                    Log.d(TAG, "nothing");
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getX() < maxValue) {
                    switch (buttonFlag) {
                        case 1:
                            if (event.getX() >= minValue && event.getX() < endPosition) {
                                Log.e(TAG, "Start: minValue = " + minValue
                                        + " <= event.getX() = " + event.getX()
                                        + " < endPosition = " + endPosition
                                );
                                setStartPosition(event.getX());
                                if (listener != null) {
                                    listener.onProgressChange(this.startPosition, this.endPosition);
                                }
                            } else {
                                Log.e(TAG, "cant setStartPosition");
                            }
                            break;
                        case 2:
                            if (event.getX() <= maxValue && event.getX() > startPosition) {
                                Log.e(TAG, "End: startPosition = " + startPosition
                                        + "< event.getX() = " + event.getX()
                                        + " <= maxValue = " + maxValue
                                );
                                setEndPosition(event.getX());
                                if (listener != null) {
                                    listener.onProgressChange(this.startPosition, this.endPosition);
                                }
                            } else {
                                Log.e(TAG, "cant setEndPosition");
                            }
                            break;
                        default:
                            Log.d(TAG, "not in any region, event.getX() = " + event.getX());
                            break;
                    }
                    invalidate();
                } else {
                    Log.d(TAG, "Position numbers are out of limits!");
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
        region.setPath(pathStartButton,
                new Region((int) bounds.left, (int) bounds.top, (int) bounds.right, (int) bounds.bottom));
        return region.contains((int) x, (int) y);
    }

    private boolean isInEndRegion(float x, float y) {
        RectF bounds = new RectF();
        pathEndButton.computeBounds(bounds, true);
        Region region = new Region();
        region.setPath(pathEndButton,
                new Region((int) bounds.left, (int) bounds.top, (int) bounds.right, (int) bounds.bottom));
        return region.contains((int) x, (int) y);
    }

    public float getStartPosition() {
        return startPosition;
    }

    private float distance;

    public void setStartPosition(float startPosition) {
        this.startPosition = startPosition - distance;
        Log.d(TAG, "setStartPosition, startPosition= " + startPosition + " - distance = " + distance + " = " + this.startPosition);
    }

    public float getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(float endPosition) {
        this.endPosition = endPosition - distance;
        Log.d(TAG, "setEndPosition, endPosition = " + endPosition + " - distance = " + distance + " = " + this.endPosition);
    }

    public interface ProgressChangeListener {
        void onProgressChange(float startPosition, float endPosition);
    }

    public void setOnProgressChangeListener(ProgressChangeListener listener) {
        this.listener = listener;
    }

    ProgressChangeListener listener;
}
