package com.caiwei.customui.xfermode;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.caiwei.customui.R;

/**
 * Created by Cai Wei on 5/14/2017.
 */
public class HeartMapView extends View {

    private Paint mPaint;
    private int mItemWaveLength = 0;
    private int dx = 0;

    private Bitmap BmpSRC, BmpDST;

    public HeartMapView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setColor(Color.RED);

        BmpDST = BitmapFactory.decodeResource(getResources(), R.drawable.heartmap, null);
        BmpSRC = Bitmap.createBitmap(BmpDST.getWidth(), BmpDST.getHeight(), Bitmap.Config.ARGB_8888);

        mItemWaveLength = BmpDST.getWidth();
        startAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Canvas c = new Canvas(BmpSRC);
        //清空bitmap
        c.drawColor(Color.RED, PorterDuff.Mode.CLEAR);
        //画上矩形
        c.drawRect(BmpDST.getWidth() - dx, 0, BmpDST.getWidth(), BmpDST.getHeight(), mPaint);

        //模式合成
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(BmpDST, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(BmpSRC, 0, 0, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }


    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, mItemWaveLength);
        animator.setDuration(6000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }

    /*private Paint mBitMapPaint;
    private Bitmap mBitMapSRC, mBitMapDST;
    private int dx;

    public HeartMapView(Context context) {
        this(context, null);

    }

    public HeartMapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mBitMapPaint = new Paint();
        mBitMapPaint.setColor(Color.RED);

        mBitMapDST = BitmapFactory.decodeResource(getResources(), R.drawable.heartmap);
        mBitMapSRC = Bitmap.createBitmap(mBitMapDST.getWidth(), mBitMapDST.getHeight(), Bitmap.Config.ARGB_8888);

        startAnimation();
    }

    private void startAnimation() {
        ValueAnimator animator = ValueAnimator.ofInt(0, mBitMapDST.getWidth());
        animator.setDuration(6000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 清空src bitmap的内容，变成透明的
        Canvas c = new Canvas(mBitMapSRC);
        c.drawColor(Color.RED, PorterDuff.Mode.CLEAR);

        // 画不透明的矩形区域
        c.drawRect(
                mBitMapDST.getWidth() - dx,
                0,
                mBitMapDST.getWidth(),
                mBitMapDST.getHeight(),
                mBitMapPaint
        );

        // 画目标图片
        canvas.drawBitmap(mBitMapDST, 0, 0, mBitMapPaint);
        mBitMapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(mBitMapSRC, 0, 0, mBitMapPaint);
        mBitMapPaint.setXfermode(null);
    }*/
}
