package com.caiwei.customui.path;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Cai Wei on 2017/5/25.
 */

public class CircleLoadingView extends View {
    public final String TAG = getClass().getSimpleName();
    private Path mPath;
    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private float mAnimatorValue;
    private Path mDstPath;
    private float mLength;
    private ValueAnimator valueAnimator;

    public CircleLoadingView(Context context) {
        this(context, null);
    }

    public CircleLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPathMeasure = new PathMeasure();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPath = new Path();
        mPath.addCircle(400, 400, 100, Path.Direction.CW);
        mPathMeasure.setPath(mPath, true);
        mLength = mPathMeasure.getLength();
        mDstPath = new Path();

        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.e(TAG, "onAnimationUpdate ,getAnimatedValue = " + (float) valueAnimator.getAnimatedValue());
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e(TAG, "onAnimationUpdate ,getAnimatedValue = " + (float) valueAnimator.getAnimatedValue());
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.e(TAG, "onAnimationUpdate ,getAnimatedValue = " + (float) valueAnimator.getAnimatedValue());
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.e(TAG, "onAnimationUpdate ,getAnimatedValue = " + (float) valueAnimator.getAnimatedValue());
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Log.e(TAG, "onAnimationUpdate ,getAnimatedValue = " + (float) valueAnimator.getAnimatedValue());
                mAnimatorValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
    }

    public void start() {
        valueAnimator.start();
    }

    public void end() {
        valueAnimator.end();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDstPath.reset();
        // 硬件加速的BUG
        mDstPath.lineTo(0, 0);
        /*float stop = mLength * mAnimatorValue;
        mPathMeasure.getSegment(0, stop, mDstPath, true);*/

        float stop = mLength * mAnimatorValue;
        float start = (float) (stop - ((0.5 - Math.abs(mAnimatorValue - 0.5)) * mLength));
        mPathMeasure.getSegment(start, stop, mDstPath, true);

        canvas.drawPath(mDstPath, mPaint);
    }
}
