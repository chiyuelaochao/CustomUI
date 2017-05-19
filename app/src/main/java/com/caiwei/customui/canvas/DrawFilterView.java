package com.caiwei.customui.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.caiwei.customui.R;

/**
 * 抗锯齿
 *
 * @author neng
 */

/**
 * Created by wei.cai on 2017/5/19.
 */

public class DrawFilterView extends View {
    private Paint paint;
    private Bitmap bitmap;
    private Matrix matrix; //用来旋转图片

    public DrawFilterView(Context context) {
        this(context, null);

    }


    public DrawFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(30);
        paint.setStyle(Paint.Style.STROKE);
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
        matrix = new Matrix();
        setFocusable(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.LTGRAY);
        //1. 画一条线, 倾斜后会锯齿
        canvas.drawLine(0, 0, 300, 400, paint);
        //2. 还是上面那条线,设置抗锯齿后的效果
        paint.setAntiAlias(true);//设置线条等图形的抗锯齿
        canvas.drawLine(100, 0, 320, 400, paint);

        //1、画一个图片. (绘制图片时可以不要最后一个参数---画笔)
        canvas.drawBitmap(bitmap, 0, 500, null);
        //2、旋转倾斜后, 就会出现锯齿效果
        matrix.setRotate(75);//旋转75度
        matrix.postTranslate(200, 200);//移动100\100.(第一个用set, 后面的设置都用post才叠加)
        canvas.drawBitmap(bitmap, matrix, null);

        //设置图形、图片的抗锯齿。可用于线条等。按位或.
        PaintFlagsDrawFilter pfdf = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        //3、旋转后, 图片的抗锯齿。
        canvas.setDrawFilter(pfdf);

        matrix.postTranslate(66, 0);
        canvas.drawBitmap(bitmap, matrix, null);
    }
}

