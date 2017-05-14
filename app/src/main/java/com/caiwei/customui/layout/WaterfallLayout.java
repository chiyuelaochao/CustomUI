package com.caiwei.customui.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.caiwei.customui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wei.cai on 2017/5/8.
 */

public class WaterfallLayout extends ViewGroup {
    public static final String TAG = WaterfallLayout.class.getSimpleName();
    private static final int DEFAULT_COLUMN_NUM = 2;

    private List<List<View>> mViewColumnList = new ArrayList<>();
    private List<Integer> columnWidthList = new ArrayList<>();
    private List<Integer> columnHeightList = new ArrayList<>();
    private int columnNum;

    public WaterfallLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray s = context.obtainStyledAttributes(attrs, R.styleable.waterfallLayout);//从xml那传来的一组值
        columnNum = s.getInteger(R.styleable.waterfallLayout_column_num, DEFAULT_COLUMN_NUM);
        Log.i(TAG, "WaterfallLayout() columnNum = " + columnNum);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 定义FlowLayout测量大小
        int measuredWith = 0;
        int measuredHeight = 0;

        // 获取父容器为FlowLayout摄住的测量模式和建议大小
        int iWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int iHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int iWidthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int iHeightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (iWidthMode == MeasureSpec.EXACTLY && iHeightMode == MeasureSpec.EXACTLY) {
            measuredWith = iWidthSpecSize;
            measuredHeight = iHeightSpecSize;
        } else {
            // 根据columnNum来初始化三个数据存储集合
            mViewColumnList.clear();
            columnWidthList.clear();
            columnHeightList.clear();
            for (int i = 0; i < columnNum; i++) {
                mViewColumnList.add(new ArrayList<View>());// 1.每列Views集合
                columnWidthList.add(0);// 2.每列宽度集合
                columnHeightList.add(0);// 3.每列高度集合
            }

            for (int i = 0; i < getChildCount(); i++) {
                View childView = getChildAt(i);
                measureChild(childView, widthMeasureSpec, heightMeasureSpec);// 计算出childView的宽和高
                MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
                // 获取childView的宽度和高度
                int iChildWidth = childView.getMeasuredWidth() + params.leftMargin + params.rightMargin;
                int iChildHeight = childView.getMeasuredHeight() + params.topMargin + params.bottomMargin;

                // 根据每列的高度，把childView加入到高度最小的那列
                int index = getMinColumnHeightIndex(columnHeightList);
                columnHeightList.get(index);
                mViewColumnList.get(index).add(childView);
                if (iChildWidth > columnWidthList.get(index)) {
                    columnWidthList.set(index, iChildWidth);
                }
                columnHeightList.set(index, columnHeightList.get(index) + iChildHeight);
            }

            measuredWith = getTotalWidth(columnWidthList);
            measuredHeight = getMaxColumnHeight(columnHeightList);
        }

        // 设置FlowLayout大小
        setMeasuredDimension(measuredWith, measuredHeight);
    }

    //获取总宽度
    private int getTotalWidth(List<Integer> columnWidthList) {
        int totalWidth = 0;
        for (Integer width : columnWidthList) {
            totalWidth += width;
        }
        return totalWidth;
    }


    //获取最小值的下标
    public int getMinColumnHeightIndex(List<Integer> list) {
        int minIndex = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) < list.get(minIndex)) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    //获取最大值
    public int getMaxColumnHeight(List<Integer> list) {
        int maxNum = list.get(0);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > maxNum) {
                maxNum = list.get(i);
            }
        }
        return maxNum;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left, top, right, bottom;
        int curTop = 0;
        int curLeft = 0;
        int columnCount = mViewColumnList.size();

        for (int i = 0; i < columnCount; i++) {
            List<View> viewList = mViewColumnList.get(i);
            int lineViewSize = viewList.size();
            for (int j = 0; j < lineViewSize; j++) {
                View childView = viewList.get(j);
                MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();

                left = curLeft + layoutParams.leftMargin;
                top = curTop + layoutParams.topMargin;
                right = left + childView.getMeasuredWidth();
                bottom = top + childView.getMeasuredHeight();
                childView.layout(left, top, right, bottom);
                curTop += childView.getMeasuredHeight() + layoutParams.leftMargin + layoutParams.rightMargin;
            }
            curLeft += columnWidthList.get(i);
            curTop = 0;
        }

        mViewColumnList.clear();
        columnWidthList.clear();
        columnHeightList.clear();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int index);
    }

    public void setOnItemClickListener(final OnItemClickListener listener) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            final int finalI = i;
            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v, finalI);
                }
            });
        }
    }
}
