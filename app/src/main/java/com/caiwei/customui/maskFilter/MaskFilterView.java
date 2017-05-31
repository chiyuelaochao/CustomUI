package com.caiwei.customui.maskFilter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 孙群,博文：http://blog.csdn.net/iispring/article/details/47690011
 */
public class MaskFilterView extends View {
    private TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private float density = getResources().getDisplayMetrics().density;
    private float fontSize = 20;
    private Bitmap bitmap;

    public MaskFilterView(Context context) {
        super(context);
        init(null, 0);
    }

    public MaskFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MaskFilterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        /*final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.MyView, defStyle, 0);
        a.recycle();*/

        //根据当前的屏幕密度设置字体大小
        fontSize *= density;
        paint.setTextSize(fontSize);
        //设置画笔线宽
        paint.setStrokeWidth(10 * density);
        //设置画笔颜色
        paint.setColor(0xff8bc5ba);
    }

    //通过该方法为画笔设置MaskFilter
    public void setMaskFilterForPaint(MaskFilter maskFilter) {
        paint.setMaskFilter(maskFilter);
        //在为画笔设置了MaskFilter之后，我们需要调用postInvalidate()方法重绘MyView
        postInvalidate();
    }

    //释放Bitmap
    private void releaseBitmap() {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        bitmap = null;
    }

    //设置Bitmap
    public void setBitmap(Bitmap bitmap) {
        releaseBitmap();
        this.bitmap = bitmap;
    }

    //在销毁的时候释放Bitmap资源
    public void destroy() {
        releaseBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int offsetX = (int) (10 * density);
        int canvasWidth = canvas.getWidth() - offsetX;
        int canvasHeight = canvas.getHeight();
        int count = 7;
        int deltaY = canvasHeight / (count + 1);
        int smallDeltaY = deltaY / (count + 1);
        canvas.translate(offsetX, 0);

        /*---------------------------绘制文本--------------------------*/
        canvas.translate(0, smallDeltaY);
        canvas.drawText("绘制文本", 0, fontSize, paint);

        /*---------------------------绘制点--------------------------*/
        canvas.translate(0, deltaY + smallDeltaY);
        int pointDeltaX = canvasWidth / 3;
        //设置画笔线宽
        paint.setStrokeWidth(20 * density);
        //绘制BUTT类型的点
        paint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawPoint(0, 0, paint);
        //绘制ROUND类型的点
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(pointDeltaX, 0, paint);
        //绘制SQUARE类型的点
        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawPoint(pointDeltaX * 2, 0, paint);

        /*---------------------------绘制直线--------------------------*/
        canvas.translate(0, deltaY + smallDeltaY);
        //设置画笔线宽
        paint.setStrokeWidth(5 * density);
        canvas.drawLine(0, 0, canvasWidth, 0, paint);

        /*---------------------------绘制弧线--------------------------*/
        canvas.translate(0, deltaY + smallDeltaY);
        paint.setStyle(Paint.Style.STROKE);
        RectF arcRecF = new RectF();
        arcRecF.left = 0;
        arcRecF.top = 0;
        arcRecF.right = deltaY * 2;
        arcRecF.bottom = deltaY;
        canvas.drawArc(arcRecF, 225, 135, true, paint);

        /*---------------------------绘制矩形--------------------------*/
        canvas.translate(0, deltaY + smallDeltaY);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, canvasWidth / 2, deltaY / 2, paint);

        /*---------------------------绘制椭圆面--------------------------*/
        canvas.translate(0, deltaY + smallDeltaY);
        RectF ovalRecF = new RectF();
        ovalRecF.left = 0;
        ovalRecF.top = 0;
        ovalRecF.right = deltaY * 2;
        ovalRecF.bottom = deltaY;
        canvas.drawOval(ovalRecF, paint);

        /*---------------------------绘制Bitmap--------------------------*/
        if (bitmap != null) {
            canvas.translate(0, deltaY + smallDeltaY);
            canvas.drawBitmap(bitmap, 0, 0, paint);
        }
    }
}