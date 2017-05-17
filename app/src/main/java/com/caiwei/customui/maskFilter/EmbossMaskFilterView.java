package com.caiwei.customui.maskFilter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Cai Wei on 5/17/2017.
 */

public class EmbossMaskFilterView extends View {
    private Paint paint;
    private EmbossMaskFilter emboss;

    public EmbossMaskFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(32);
        paint.setTextSize(368);
        float[] direction = new float[]{1, 1, 1};// 设置x、y、z三个方向效果
        float light = 0.1f;// 设置环境光亮度
        float specular = 5;// 选择要应用的反射等级
        float blur = 5;// 向mask应用一定级别的模糊
        emboss = new EmbossMaskFilter(direction, light, specular, blur);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);     //关闭硬件加速
        paint.setMaskFilter(emboss);
        canvas.drawText("蔡炜", 30, 400, paint);
    }

    private Canvas canvas;

    @SuppressLint("NewApi")
    public void setparam(float x, float y, float z, float light, float specular, float blur) {
        emboss = new EmbossMaskFilter(new float[]{x, y, z}, light, specular, blur);
        paint.setMaskFilter(emboss);
        canvas.drawText("蔡炜", 30, 400, paint);
        invalidate();
    }
}