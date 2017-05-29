package com.caiwei.customui.path;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wei.cai on 2017/5/29.
 */

public class CircleSearchView extends View {

    private Paint mPaint;
    private Path mCirclePath;
    private Path mHandPath;
    private Path mBigCirclePath;
    private Path mCirclePathDst;
    private Path mHandPathDst;
    private Path mBigCirclePathDst;

    private PathMeasure mPathMeasure;

    //view的宽高
    private int mWidth;
    private int mHeight;
    private int mRadius;

    //当前绘制进度占总长度的百分百
    private float mCirclePercent = 0;
    private float mHandPercent = 0;
    private float mBigCirclePercent = 0;
    private boolean isCircleAnimStart = false;
    private boolean isHandAnimStart = false;
    private boolean isBigCircleAnimStart = false;

    public CircleSearchView(Context context) {
        this(context, null);
    }

    public CircleSearchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleSearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);

        mCirclePath = new Path();
        mCirclePathDst = new Path();
        mBigCirclePath = new Path();
        mHandPath = new Path();
        mHandPathDst = new Path();
        mBigCirclePathDst = new Path();

        mPathMeasure = new PathMeasure();

        startCircleValueAnim();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isHandAnimStart && !isCircleAnimStart && !isBigCircleAnimStart) {
                    startCircleValueAnim();
                }
            }
        });
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#87CEFA"));

        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.rotate(45);
        mCirclePath.addCircle(0, 0, mRadius, Path.Direction.CW);
        mHandPath.moveTo(mRadius, 0);
        mHandPath.lineTo(mRadius + dp2px(getContext(), 60), 0);
        mBigCirclePath.addCircle(0, 0, mRadius + dp2px(getContext(), 60), Path.Direction.CW);
        if (!isCircleAnimStart) {
            canvas.drawPath(mCirclePath, mPaint);
        }
        if (!isHandAnimStart) {
            canvas.drawPath(mHandPath, mPaint);
        }

        mPathMeasure.setPath(mCirclePath, false);
        mCirclePathDst.reset();
        mPathMeasure.getSegment(mCirclePercent * mPathMeasure.getLength(), mPathMeasure.getLength(), mCirclePathDst, true);
        canvas.drawPath(mCirclePathDst, mPaint);
        if (mCirclePercent == 1.0) {
            mPathMeasure.nextContour();
            mPathMeasure.setPath(mHandPath, false);
            mHandPathDst.reset();
            mPathMeasure.getSegment(
                    mHandPercent * mPathMeasure.getLength(),
                    mPathMeasure.getLength(),
                    mHandPathDst,
                    true
            );
            canvas.drawPath(mHandPathDst, mPaint);
        }
        if (mHandPercent == 1) {
            mPathMeasure.nextContour();
            mBigCirclePathDst.reset();
            mPathMeasure.setPath(mBigCirclePath, false);
            mPathMeasure.getSegment(
                    mBigCirclePercent * mPathMeasure.getLength(),
                    mBigCirclePercent * mPathMeasure.getLength() + 50,
                    mBigCirclePathDst,
                    true
            );
            canvas.drawPath(mBigCirclePathDst, mPaint);
        }
        canvas.restore();
    }


    private void startHandValueAnim() {
        ValueAnimator handPathValueAnimator = ValueAnimator.ofFloat(0, 1);
        handPathValueAnimator.setDuration(500);
        handPathValueAnimator.start();
        handPathValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mHandPercent = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        handPathValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startBigCircleValueAnim();
            }
        });
    }

    private void startBigCircleValueAnim() {
        ValueAnimator bigCircleValueAnim = ValueAnimator.ofFloat(0, 1);
        bigCircleValueAnim.setDuration(1500);
        bigCircleValueAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBigCirclePercent = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        bigCircleValueAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isBigCircleAnimStart = false;
                isHandAnimStart = false;
                isCircleAnimStart = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                isBigCircleAnimStart = true;
            }
        });
        bigCircleValueAnim.start();
    }

    private void startCircleValueAnim() {
        ValueAnimator circleValueAnimator = ValueAnimator.ofFloat(0, 1);
        circleValueAnimator.setDuration(1000);
        circleValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCirclePercent = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        circleValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startHandValueAnim();
                isHandAnimStart = true;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                isCircleAnimStart = true;
            }
        });
        circleValueAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureSpec(widthMeasureSpec), measureSpec(heightMeasureSpec));
    }

    public int measureSpec(int measureSpec) {
        int expectSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            expectSize = size;
        } else {
            expectSize = dp2px(getContext(), 300f);
            if (mode == MeasureSpec.AT_MOST) {
                expectSize = Math.min(expectSize, size);
            }
        }

        return expectSize;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
        this.mRadius = mWidth / 6;
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}