package com.caiwei.customui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cai Wei on 5/6/2017.
 */

public class FlowLayout extends ViewGroup {
    /**
     * 用来保存所有行views的列表
     */
    private List<List<View>> mViewLinesList = new ArrayList<>();

    /**
     * 用来保存行高的列表
     */
    private List<Integer> mLineHeightList = new ArrayList<>();

    /**
     * 用来保存单行views的列表
     */
//    private List<View> viewList = new ArrayList<>();
    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 用于父容器添加子View时调用，生成和此容器类型相匹配的布局参数。
     *
     * @param attrs xml解析inflate时生成和容器类型匹配的布局LayoutParams
     * @return 和容器类匹配的布局LayoutParams
     */
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

        // 定义每行的宽度和高度
        int iCurrentLineWith = 0;
        int iCurrentLineHeight = 0;
        if (iWidthMode == MeasureSpec.EXACTLY && iHeightMode == MeasureSpec.EXACTLY) {
            measuredWith = iWidthSpecSize;
            measuredHeight = iHeightSpecSize;
        } else {
            // 定义childView的宽度和高度
            int iChildWidth;
            int iChildHeight;
            int childCount = getChildCount();
            List<View> viewList = new ArrayList<>();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                // 计算出childView的宽和高
                measureChild(childView, widthMeasureSpec, heightMeasureSpec);

                MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
                iChildWidth = childView.getMeasuredWidth() + params.leftMargin + params.rightMargin;
                iChildHeight = childView.getMeasuredHeight() + params.topMargin + params.bottomMargin;
                if (iCurrentLineWith + iChildWidth > iWidthSpecSize) {
                    // [1、记录当前行的信息]
                    //1、记录当前行的最大宽度，高度累加
                    measuredWith = Math.max(measuredWith, iCurrentLineWith);
                    measuredHeight += iCurrentLineHeight;
                    //2、将当前行的viewList添加至总的mViewsList，将行高添加至总的行高List
                    mViewLinesList.add(viewList);
                    mLineHeightList.add(iCurrentLineHeight);

                    // [2、记录新一行的信息]
                    //1、重新赋值新一行的宽、高
                    iCurrentLineWith = iChildWidth;
                    iCurrentLineHeight = iChildHeight;
                    // 2、新建一行的viewlist，添加新一行的view
                    viewList = new ArrayList<>(); // Note：这里必须New一个新的List
                    viewList.add(childView);
                } else {
                    // 记录某行内的消息
                    //1、行内宽度的叠加、高度比较
                    iCurrentLineWith += iChildWidth;
                    iCurrentLineHeight = Math.max(iCurrentLineHeight, iChildHeight);

                    // 2、添加至当前行的viewList中
                    viewList.add(childView);
                }

                // [3、如果正好是最后一行需要换行]
                if (i == childCount - 1) {
                    //1、记录当前行的最大宽度，高度累加
                    measuredWith = Math.max(measuredWith, iCurrentLineWith);
                    measuredHeight += iCurrentLineHeight;

                    //2、将当前行的viewList添加至总的mViewsList，将行高添加至总的行高List
                    mViewLinesList.add(viewList);
                    mLineHeightList.add(iCurrentLineHeight);
                }
            }
        }

        // 设置FlowLayout大小
        setMeasuredDimension(measuredWith, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left, top, right, bottom;
        int curTop = 0;
        int curLeft = 0;
        int lineCount = mViewLinesList.size();

        for (int i = 0; i < lineCount; i++) {
            List<View> viewList = mViewLinesList.get(i);
            int lineViewSize = viewList.size();
            for (int j = 0; j < lineViewSize; j++) {
                View childView = viewList.get(j);
                MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();

                left = curLeft + layoutParams.leftMargin;
                top = curTop + layoutParams.topMargin;
                right = left + childView.getMeasuredWidth();
                bottom = top + childView.getMeasuredHeight();
                childView.layout(left, top, right, bottom);
                curLeft += childView.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            }
            curLeft = 0;
            curTop += mLineHeightList.get(i);
        }

        mViewLinesList.clear();
        mLineHeightList.clear();
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
