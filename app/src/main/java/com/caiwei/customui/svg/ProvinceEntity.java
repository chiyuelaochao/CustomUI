package com.caiwei.customui.svg;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;

/**
 * Created by wei.cai on 2017/6/1.
 */

public class ProvinceEntity {
    private static final String TAG = "ProvinceEntity";
    private Path path;
    private Paint paint, selectedPaint;
    private boolean isSelected;

    public ProvinceEntity(Path path, Paint paint, boolean isSelected) {
        this.path = path;
        this.paint = paint;
        this.isSelected = isSelected;

        selectedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectedPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        selectedPaint.setColor(Color.BLACK);
        selectedPaint.setStrokeWidth(2);
    }

    public void draw(Canvas canvas) {
//        Log.e(TAG, "isSelected = " + isSelected);
        canvas.drawPath(path, isSelected ? selectedPaint : paint);
    }

    public boolean isInRegion(float x, float y) {
        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        Region region = new Region();
        region.setPath(path, new Region((int) rectF.top, (int) rectF.left, (int) rectF.right, (int) rectF.bottom));
        return region.contains((int) x, (int) y);
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
