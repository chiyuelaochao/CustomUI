package com.caiwei.customui.path;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.caiwei.customui.R;

/**
 * Created by wei.cai on 2017/5/29.
 */

public class CircleAirplaneView extends View {

    //view的宽高
    private int mWidth;
    private int mHeight;
    private int mRadius;

    private Paint mPaint;
    private Path mCirclePath;

    private PathMeasure mPathMeasure;
    private Bitmap mPlanBitmap;
    private float mPercent;
    private float[] mPos;
    private float[] mTan;
    private Matrix matrix;

    public CircleAirplaneView(Context context) {
        this(context, null);
    }

    public CircleAirplaneView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleAirplaneView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);

        mPathMeasure = new PathMeasure();
        mCirclePath = new Path();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        mPlanBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.twiter_bg, options);

        mPos = new float[2];
        mTan = new float[2];
        matrix = new Matrix();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnim();
            }
        });
        startAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#87CEFA"));

        mCirclePath.addCircle(mWidth / 2, mHeight / 2, mRadius, Path.Direction.CCW);
        canvas.drawPath(mCirclePath, mPaint);
        mPathMeasure.setPath(mCirclePath, false);
        mPathMeasure.getPosTan(mPathMeasure.getLength() * mPercent, mPos, mTan);

        double atan = Math.atan2(mTan[1], mTan[0]);//弧度制
        double angle = atan * 180 / Math.PI;
        matrix.reset();
        matrix.postRotate((float) angle + 90, mPlanBitmap.getWidth() / 2, mPlanBitmap.getHeight() / 2);
        matrix.postTranslate(mPos[0] - mPlanBitmap.getWidth() / 2, mPos[1] - mPlanBitmap.getHeight() / 2);
        canvas.drawBitmap(mPlanBitmap, matrix, mPaint);
    }

    private void startAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(5000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPercent = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
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
        this.mRadius = mWidth / 4;
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}