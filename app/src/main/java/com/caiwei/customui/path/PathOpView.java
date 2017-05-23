package com.caiwei.customui.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Cai Wei on 2017/5/23.
 */

public class PathOpView extends View {

    private Paint mPaint;

    public PathOpView(Context context) {
        this(context, null);
    }

    public PathOpView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawDifferenceOp(canvas);       // DIFFERENCE -- 减去Path2后Path1区域剩下的部分
        drawIntersectOp(canvas);        // INTERSECT --- 保留Path2 和 Path1 共同的部分
        drawUnionOp(canvas);            // UNION -- 保留Path1 和 Path 2
        drawXorOp(canvas);              // XOR --- 保留Path1 和 Path2 还有共同的部分
        drawReverseDifferenceOp(canvas);// REVERSE_DIFFERENCE --- 减去Path1后Path2区域剩下的部分
    }

    private void drawDifferenceOp(Canvas canvas) {
        Path path1 = new Path();
        path1.addCircle(150, 150, 100, Path.Direction.CW);
        Path path2 = new Path();
        path2.addCircle(200, 200, 100, Path.Direction.CW);
        path1.op(path2, Path.Op.DIFFERENCE);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);
        canvas.drawPath(path1, mPaint);
        mPaint.setColor(Color.DKGRAY);
        mPaint.setStrokeWidth(2);
        canvas.drawCircle(150, 150, 100, mPaint);
        canvas.drawCircle(200, 200, 100, mPaint);
    }

    private void drawIntersectOp(Canvas canvas) {
        Path path1 = new Path();
        path1.addCircle(450, 150, 100, Path.Direction.CW);
        Path path2 = new Path();
        path2.addCircle(500, 200, 100, Path.Direction.CW);
        path1.op(path2, Path.Op.INTERSECT);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);
        canvas.drawPath(path1, mPaint);
        mPaint.setColor(Color.DKGRAY);
        mPaint.setStrokeWidth(2);
        canvas.drawCircle(450, 150, 100, mPaint);
        canvas.drawCircle(500, 200, 100, mPaint);
    }

    private void drawUnionOp(Canvas canvas) {
        Path path1 = new Path();
        path1.addCircle(150, 450, 100, Path.Direction.CW);
        Path path2 = new Path();
        path2.addCircle(200, 500, 100, Path.Direction.CW);
        path1.op(path2, Path.Op.UNION);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);
        canvas.drawPath(path1, mPaint);
        mPaint.setColor(Color.DKGRAY);
        mPaint.setStrokeWidth(2);
        canvas.drawCircle(150, 450, 100, mPaint);
        canvas.drawCircle(200, 500, 100, mPaint);
    }

    private void drawXorOp(Canvas canvas) {
        Path path1 = new Path();
        path1.addCircle(450, 450, 100, Path.Direction.CW);
        Path path2 = new Path();
        path2.addCircle(500, 500, 100, Path.Direction.CW);
        path1.op(path2, Path.Op.XOR);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);
        canvas.drawPath(path1, mPaint);
        mPaint.setColor(Color.DKGRAY);
        mPaint.setStrokeWidth(2);
        canvas.drawCircle(450, 450, 100, mPaint);
        canvas.drawCircle(500, 500, 100, mPaint);
    }

    private void drawReverseDifferenceOp(Canvas canvas) {
        Path path1 = new Path();
        path1.addCircle(150, 750, 100, Path.Direction.CW);
        Path path2 = new Path();
        path2.addCircle(200, 800, 100, Path.Direction.CW);
        path1.op(path2, Path.Op.REVERSE_DIFFERENCE);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);
        canvas.drawPath(path1, mPaint);
        mPaint.setColor(Color.DKGRAY);
        mPaint.setStrokeWidth(2);
        canvas.drawCircle(150, 750, 100, mPaint);
        canvas.drawCircle(200, 800, 100, mPaint);
    }

}
