package com.caiwei.customui.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wei.cai on 2017/5/19.
 */

public class SaveRestoreLayerView extends View {
    private final String TAG = getClass().getSimpleName();

    public SaveRestoreLayerView(Context context) {
        this(context, null);
    }

    public SaveRestoreLayerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        RectF rectF = new RectF(0, 0, 400, 500);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(15);
        paint.setColor(Color.GREEN);
        canvas.drawRect(rectF, paint);

        canvas.translate(50, 50);
        canvas.saveLayer(0, 0, canvas.getWidth() - 200, canvas.getHeight() - 300, null, Canvas.ALL_SAVE_FLAG);
//        canvas.save();
        canvas.drawColor(Color.GRAY);// 通过drawColor可以发现saveLayer是新建了一个图层，
        // 同时结合Lsn5的16种Xfermode叠加形式Demo可以验证是新建的透明图层
        paint.setColor(Color.YELLOW);
        canvas.drawRect(rectF, paint);

        canvas.restore();
        RectF rectF1 = new RectF(15, 15, 300, 400);
        paint.setColor(Color.RED);
        canvas.drawRect(rectF1, paint);
    }
}
