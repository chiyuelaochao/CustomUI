package com.caiwei.customui.path;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Cai Wei on 5/27/2017.
 */

public class TrashCanView extends View {
    private String TAG = getClass().getSimpleName();
    private int defaultSize = 500;//设置垃圾桶的大小高度
    private int mWidth, mHeight;//view的宽和高
    private int mBodyWith = 200, mBodyHeight = 300;//垃圾桶下部身体的宽和高 view*1/3

    private Paint mPaint;
    private Path mPath;

    private Float openProgress;
    private ValueAnimator animator;
    private static final int STATUS_RUNNING = 0;//动画状态
    private static final int STATUS_PAINT_LINE = 1;//画出盖檐
    private static final int STATUS_PAINT_HEAD = 2;//画出把手

    public TrashCanView(Context context) {
        this(context, null);
    }

    public TrashCanView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化Paint,path
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(5f);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画出垃圾桶下面区域
        drawBottom();
        canvas.drawColor(Color.GRAY);
        canvas.drawPath(mPath, mPaint);
        //画出垃圾桶盖子
        //动画判断是否刷新视图
        if (animator != null && animator.isRunning()) {
            //动画执行过程中具体帧值
            openProgress = (Float) animator.getAnimatedValue();

            //3.画垃圾桶打开动画，旋转画布
            canvas.rotate(
                    openProgress * 30,
                    mWidth / 2 + (mBodyWith / 2),
                    mHeight / 2 - (mBodyHeight / 2)
            );

            invalidate();
        }

        //1画出静止状态的垃圾桶盖子线条
        canvas.drawLine(
                mWidth / 2 - (mBodyWith / 2) - 10,
                mHeight / 2 - (mBodyHeight / 2) - 10,
                mWidth / 2 + (mBodyWith / 2) + 10,
                mHeight / 2 - (mBodyHeight / 2) - 10,
                mPaint
        );

        //2画出静止状态盖子上面的把手
        canvas.drawRect(
                mWidth / 2 - (mBodyWith / 9),
                mHeight / 2 - (mBodyHeight / 2) - 20,
                mWidth / 2 + (mBodyWith / 9),
                mHeight / 2 - (mBodyHeight / 2) - 10,
                mPaint
        );
    }


    /**
     * 画出垃圾桶下面区域
     */
    private void drawBottom() {
        //画垃圾桶边缘
        //1.移动到最左上角的点
        mPath.moveTo(mWidth / 2 - (mBodyWith / 2), mHeight / 2 - (mBodyHeight / 2));
        //2.连接左下角的点
        mPath.lineTo(mWidth / 2 - (mBodyWith / 3), mHeight / 2 + (mBodyHeight / 2));
        //3.连接右下角的点
        mPath.lineTo(mWidth / 2 + (mBodyWith / 3), mHeight / 2 + (mBodyHeight / 2));
        //4.连接右上角的点
        mPath.lineTo(mWidth / 2 + (mBodyWith / 2), mHeight / 2 - (mBodyHeight / 2));

        //画里面的竖线
        //1.画左边一条线
        mPath.moveTo(mWidth / 2 - (mBodyWith / 5), mHeight / 2 - (mBodyHeight / 3));
        mPath.lineTo(mWidth / 2 - (mBodyWith / 5), mHeight / 2 + (mBodyHeight / 3));
        //2.画右边一条线
        mPath.moveTo(mWidth / 2 + (mBodyWith / 5), mHeight / 2 - (mBodyHeight / 3));
        mPath.lineTo(mWidth / 2 + (mBodyWith / 5), mHeight / 2 + (mBodyHeight / 3));
        //3.画中间一条线
        mPath.moveTo(mWidth / 2, mHeight / 2 - (mBodyHeight / 3));
        mPath.lineTo(mWidth / 2, mHeight / 2 + (mBodyHeight / 3));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasureSize(defaultSize, widthMeasureSpec);
        int height = getMeasureSize(defaultSize, heightMeasureSpec);
        setMeasuredDimension(width, height);
        //赋值view的宽和高
        mWidth = width > 0 ? width : 0;
        mHeight = height > 0 ? height : 0;
    }

    /**
     * 测量布局的宽高
     *
     * @param defaultSize 测量的默认值大小
     * @param measureSpec 测量参数
     * @return 测量后的width or height
     */
    private int getMeasureSize(int defaultSize, int measureSpec) {
        int resultSize = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.AT_MOST:
                resultSize = defaultSize;
                break;
            case MeasureSpec.EXACTLY:
                resultSize = size;
                break;
            case MeasureSpec.UNSPECIFIED:
                resultSize = size;
                break;
        }
        return resultSize;
    }

    /**
     * 开始动画
     */
    public void startAnimation() {
        animator = ValueAnimator.ofFloat(0f, 1f, 0f);
        animator.setDuration(3000);
        animator.start();
        invalidate();
    }
}
