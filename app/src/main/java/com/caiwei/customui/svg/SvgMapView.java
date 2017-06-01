package com.caiwei.customui.svg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.caiwei.customui.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by wei.cai on 2017/6/1.
 */

public class SvgMapView extends View {
    public final String TAG = getClass().getSimpleName();
    private Context context;
    private int[] colors = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};
    private List<ProvinceEntity> provinceEntityList = new ArrayList<>();
    private Paint paint;

    public SvgMapView(Context context) {
        this(context, null);
    }

    public SvgMapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(2);
        loadMapThread.run();
    }

    Thread loadMapThread = new Thread() {
        @Override
        public void run() {
            //获取到DocumentBuilder的工厂实例化
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder builder = builderFactory.newDocumentBuilder();//拿到DocumentBuilder
                Document xml = builder.parse(getResources().openRawResource(R.raw.taiwan));//解析xml文件
                Element languages = xml.getDocumentElement();//获取其根标签
                NodeList list = languages.getElementsByTagName("path");//根据子标签进行查找，返回的是一个list集合
                for (int i = 0; i < list.getLength(); i++) {
                    Element lan = (Element) list.item(i);//获取到子标签lan
                    String pathData = lan.getAttribute("android:pathData");//获取到他的属性
                    Path path = PathParser.createPathFromPathData(pathData);
                    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL_AND_STROKE);
                    paint.setStrokeWidth(2);
                    paint.setColor(colors[i % 4]);
                    provinceEntityList.add(new ProvinceEntity(path, paint, false));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0);
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        for (ProvinceEntity entity : provinceEntityList) {
            entity.draw(canvas);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (provinceEntityList != null && provinceEntityList.size() > 0) {
                postInvalidate();
            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                selectedRegion(event.getX(), event.getY());
                postInvalidate();
                break;
        }
        return true;
    }

    private void selectedRegion(float x, float y) {
        for (ProvinceEntity entity : provinceEntityList) {
            boolean b = entity.isInRegion(x, y);
            entity.setSelected(b);
            if (b) {
                Log.e(TAG, "b = " + b);
            }
        }
    }
}
