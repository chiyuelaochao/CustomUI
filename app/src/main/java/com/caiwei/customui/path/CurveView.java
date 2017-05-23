package com.caiwei.customui.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Cai Wei on 2017/5/23.
 */

public class CurveView extends View {
    private float mStartX, mStartY;
    private float mEndX, mEndY;
    private float mControlX = 200, mControlY = 60;//默认值
    private Paint mPaint;
    private Path mPath;

    public CurveView(Context context) {
        this(context, null);
    }

    public CurveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mStartX = 60;
        mStartY = 350;
        mEndX = 450;
        mEndY = 350;
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(mStartX, mStartY);
        mPath.quadTo(mControlX, mControlY, mEndX, mEndY);
        //mPath.addCircle(mControlX,mControlY,50, Path.Direction.CW); //里面有一个方向值 --顺时针和逆时针
        mPaint.setTextSize(50);
        canvas.drawTextOnPath("CaiWei", mPath, mControlX, mControlY, mPaint);//可以去看一下文字的绘制方向
        canvas.drawPath(mPath, mPaint);
        canvas.drawPoint(mControlX, mControlY, mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            mControlX = event.getX();
            mControlY = event.getY();
            invalidate();
        }
        return true;
    }
}
