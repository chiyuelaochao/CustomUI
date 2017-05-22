package com.caiwei.customui.maskFilter;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Cai Wei on 5/17/2017.
 */

public class BlurMaskFilterView extends View {
    private BlurMaskFilter bmfNormal, bmfOuter, bmfInner, bmfSolid;
    private Paint paint;

    public BlurMaskFilterView(Context context) {
        super(context);
        init();
    }

    public BlurMaskFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BlurMaskFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);          //抗锯齿
        paint.setColor(Color.RED);         //画笔颜色
        paint.setStyle(Paint.Style.FILL);  //画笔风格
        paint.setTextSize(68);             //绘制文字大小，单位px
        paint.setStrokeWidth(5);           //画笔粗细

        bmfNormal = new BlurMaskFilter(10f, BlurMaskFilter.Blur.NORMAL);
        bmfOuter = new BlurMaskFilter(10f, BlurMaskFilter.Blur.OUTER);
        bmfInner = new BlurMaskFilter(10f, BlurMaskFilter.Blur.INNER);
        bmfSolid = new BlurMaskFilter(10f, BlurMaskFilter.Blur.SOLID);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setMaskFilter(bmfNormal);
        canvas.drawText("Hello World!", 100, 100, paint);

        paint.setMaskFilter(bmfOuter);
        canvas.drawText("Hello World!", 100, 200, paint);

        paint.setMaskFilter(bmfInner);
        canvas.drawText("Hello World!", 100, 300, paint);

        paint.setMaskFilter(bmfSolid);
        canvas.drawText("Hello World!", 100, 400, paint);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);     //关闭硬件加速
    }

}