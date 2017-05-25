package com.caiwei.customui.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Cai Wei on 2017/5/25.
 */

public class PathMeasureBasicView extends View {
    private final String TAG = getClass().getSimpleName();
    private final static int OFF_SET = 50;
    private int mViewWidth;
    private int mViewHeight;
    private Paint mDefaultPaint;

    private Paint mPaint;

    public PathMeasureBasicView(Context context) {
        super(context);
        init();
    }

    public PathMeasureBasicView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        mDefaultPaint = new Paint();
        mDefaultPaint.setColor(Color.RED);
        mDefaultPaint.setStrokeWidth(5);
        mDefaultPaint.setStyle(Paint.Style.STROKE);

        mPaint = new Paint();
        mPaint.setColor(Color.DKGRAY);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 平移坐标系
        canvas.translate(mViewWidth / 2, mViewHeight / 2);
        // 画坐标线
        canvas.drawLine(-canvas.getWidth() / 2 + OFF_SET, 0, canvas.getWidth() / 2 - OFF_SET, 0, mPaint);
        canvas.drawLine(0, -canvas.getHeight() / 2 + OFF_SET, 0, canvas.getHeight() / 2 - OFF_SET, mPaint);

//        testForceClosed(canvas);
//        testGetSegment(canvas);
//        testGetSegmentMoveTo(canvas);
        testNextContour(canvas);
    }

    private void testNextContour(Canvas canvas) {
        Path path = new Path();
        Path pathSmall = new Path();
        Path pathBig = new Path();
        // 添加小矩形
        pathSmall.addRect(-100, -100, 100, 100, Path.Direction.CW);
        // 添加大矩形
        //path.addRect(-200, 200, 200, 600, Path.Direction.CW);
        pathBig.addRect(-200, -200, 200, 200, Path.Direction.CW);
        path.op(pathSmall, pathBig, Path.Op.XOR);
        canvas.drawPath(path, mDefaultPaint);

        PathMeasure measure = new PathMeasure(path, false);
        float len1 = measure.getLength();
        measure.nextContour();// 跳转到下一条路径
        float len2 = measure.getLength();

        Log.e(TAG, "len1 = " + len1);
        Log.e(TAG, "len2 = " + len2);
    }

    private void testGetSegmentMoveTo(Canvas canvas) {
        Path path = new Path();
        // 创建Path并添加了一个矩形
        path.addRect(-200, -200, 200, 200, Path.Direction.CW);

        Path dst = new Path();
        dst.lineTo(-300, -300);
        // 将 Path 与 PathMeasure 关联
        PathMeasure measure = new PathMeasure(path, false);

        // 截取一部分存入dst中，并使用 moveTo 保持截取得到的 Path 第一个点的位置不变
        //measure.getSegment(200, 600, dst, false);
        measure.getSegment(0, 600, dst, true);

        canvas.drawPath(path, mPaint);
        // 绘制 dst
        canvas.drawPath(dst, mDefaultPaint);
    }

    private void testGetSegment(Canvas canvas) {
        Path path = new Path();
        // 创建Path并添加了一个矩形
        path.addRect(-200, -200, 200, 200, Path.Direction.CW);

        Path dstPath = new Path();
        // 将 Path 与 PathMeasure 关联
        PathMeasure measure = new PathMeasure(path, false);

        // 截取一部分存入dst中，并使用 moveTo 保持截取得到的 Path 第一个点的位置不变
        measure.getSegment(200, 600, dstPath, false);

        canvas.drawPath(path, mPaint);
        // 绘制 dst
        canvas.drawPath(dstPath, mDefaultPaint);
    }

    private void testForceClosed(Canvas canvas) {
        Path path = new Path();
        path.lineTo(0, 200);
        path.lineTo(200, 200);
        path.lineTo(200, 0);

        PathMeasure measure1 = new PathMeasure(path, false);
        PathMeasure measure2 = new PathMeasure(path, true);

        Log.e(TAG, "forceClosed = false, measure length = " + measure1.getLength());
        Log.e(TAG, "forceClosed = true, measure length = " + measure2.getLength());

        canvas.drawPath(path, mDefaultPaint);
    }
}
