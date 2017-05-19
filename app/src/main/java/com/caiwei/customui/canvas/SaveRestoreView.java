package com.caiwei.customui.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.caiwei.customui.R;

/**
 * Created by wei.cai on 2017/5/19.
 */

public class SaveRestoreView extends View {
    private final String TAG = getClass().getSimpleName();
    private Paint mPaint;
    private Paint paintText;
    private Bitmap mBitmap;

    public SaveRestoreView(Context context) {
        this(context, null);
    }

    public SaveRestoreView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lsj);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10);

        paintText = new Paint();
        paintText.setColor(Color.RED);
        paintText.setAntiAlias(true);
        paintText.setStyle(Paint.Style.FILL);
        paintText.setTextSize(40);
        paintText.setStrokeWidth(20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "Current SaveCount = " + canvas.getSaveCount());//1

        canvas.save();
        Log.d(TAG, "Current SaveCount = " + canvas.getSaveCount());//2

        canvas.translate(500, 500);
        RectF rectF = new RectF(0, 0, 400, 500);

//        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        canvas.drawBitmap(mBitmap, null, rectF, mPaint);
        canvas.drawText("1", 30, 30, paintText);

        canvas.save();
        Log.d(TAG, "Current SaveCount = " + canvas.getSaveCount());//3

        canvas.rotate(90);
        canvas.drawBitmap(mBitmap, null, rectF, mPaint);
        canvas.drawText("2", 30, 30, paintText);

        canvas.save();
        Log.d(TAG, "Current SaveCount = " + canvas.getSaveCount());//4

        canvas.rotate(90);
        canvas.drawBitmap(mBitmap, null, rectF, mPaint);
        canvas.drawText("3", 30, 30, paintText);

        canvas.save();
        Log.d(TAG, "Current SaveCount = " + canvas.getSaveCount());//5

        canvas.restoreToCount(1);
        Log.d(TAG, "Current SaveCount = " + canvas.getSaveCount());//1
        canvas.translate(0, 1000);
        canvas.drawBitmap(mBitmap, null, rectF, mPaint);
        canvas.drawText("4", 30, 30, paintText);

//        canvas.restoreToCount(3); // canvas.restoreToCount(1)已经把其他save出栈，所以无法回到这里，我测试时这里报错了
//        Log.d(TAG, "Current SaveCount = " + canvas.getSaveCount());
//        canvas.drawBitmap(mBitmap, null, rectF, mPaint);
    }
}
