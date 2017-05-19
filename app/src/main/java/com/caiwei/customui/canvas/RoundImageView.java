package com.caiwei.customui.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

import com.caiwei.customui.R;

/**
 * Created by Cai Wei on 2017/5/20.
 */

public class RoundImageView extends View {
    private Bitmap mBitmap;
    private Rect mRect = new Rect();
    private Paint mPaint = new Paint();
    private Path mPath = new Path();

    private int DEFAULT_SIZE = 100;

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundImageView(Context context) {
        this(context, null);
    }


    private void init() {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);// 抗锯尺
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lsj);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(DEFAULT_SIZE, widthMeasureSpec), getDefaultSize(DEFAULT_SIZE, heightMeasureSpec));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap == null) {
            return;
        }
        canvas.drawColor(Color.GRAY);
        mRect.set(0, 0, getWidth(), getHeight());

        PaintFlagsDrawFilter pdf = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG);
        canvas.setDrawFilter(pdf);

        mPath.addCircle(getWidth() / 4, getWidth() / 4, getHeight() / 4, Path.Direction.CCW);
        canvas.clipPath(mPath, Region.Op.REPLACE);

        canvas.drawBitmap(mBitmap, null, mRect, mPaint);
    }
}
