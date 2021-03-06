package com.caiwei.customui.path;

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
 * Created by Cai Wei on 2017/5/25.
 */

public class ArrowCircleView extends View {
    private final String TAG = getClass().getSimpleName();
    private final static int OFF_SET = 50;

    private float currentValue = 0;     // 用于纪录当前的位置,取值范围[0,1]映射Path的整个长度

    private float[] pos;                // 当前点的实际位置
    private float[] tan;                // 当前点的tangent值,用于计算图片所需旋转的角度
    private Bitmap mBitmap;             // 箭头图片
    private Matrix mMatrix;             // 矩阵,用于对图片进行一些操作
    private Paint mDefaultPaint;
    private int mViewWidth;
    private int mViewHeight;
    private Paint mPaint;

    public ArrowCircleView(Context context) {
        super(context);
        init(context);
    }

    public ArrowCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        pos = new float[2];
        tan = new float[2];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;       // 缩放图片
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow, options);
        mMatrix = new Matrix();

        mDefaultPaint = new Paint();
        mDefaultPaint.setColor(Color.RED);
        mDefaultPaint.setStrokeWidth(5);
        mDefaultPaint.setStyle(Paint.Style.STROKE);

        mPaint = new Paint();
        mPaint.setColor(Color.DKGRAY);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
    }

//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        mViewWidth = w;
//        mViewHeight = h;
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        // 平移坐标系
        canvas.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        // 画坐标线
        canvas.drawLine(-canvas.getWidth() / 2 + OFF_SET, 0, canvas.getWidth() / 2 - OFF_SET, 0, mPaint);
        canvas.drawLine(0, -canvas.getHeight() / 2 + OFF_SET, 0, canvas.getHeight() / 2 - OFF_SET, mPaint);

        Path path = new Path();                                 // 创建 Path
        path.addCircle(0, 0, 200, Path.Direction.CW);           // 添加一个圆形
        PathMeasure measure = new PathMeasure(path, false);     // 创建 PathMeasure

        currentValue += 0.005;                                  // 计算当前的位置在总长度上的比例[0,1]
        if (currentValue >= 1) {
            currentValue = 0;
        }

        // 方案一, 获取当前位置的坐标以及趋势
     /*   measure.getPosTan(measure.getLength() * currentValue, pos, tan);
        // 重置Matrix
        mMatrix.reset();
        // 计算图片旋转角度
        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI);
        // 旋转图片
        mMatrix.postRotate(degrees, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        // 将图片绘制中心调整到与当前点重合
        mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);*/

        // 方案二, 获取当前位置的坐标以及趋势的矩阵
        measure.getMatrix(measure.getLength() * currentValue, mMatrix,
                PathMeasure.TANGENT_MATRIX_FLAG | PathMeasure.POSITION_MATRIX_FLAG);
        // 将图片绘制中心调整到与当前点重合(注意:此处是前乘pre)
        mMatrix.preTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);
        canvas.drawPath(path, mDefaultPaint);

        canvas.drawBitmap(mBitmap, mMatrix, mDefaultPaint);
        invalidate();
    }
}
