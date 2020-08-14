package com.example.myviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.view.MarginLayoutParamsCompat;

import com.blankj.utilcode.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: 韩庆凯
 * @CreateDate: 2020/6/23 10:20
 * @UpdateUser:
 * @UpdateDate: 2020/6/23 10:20
 * @UpdateRemark:
 * @Version:
 */
public class TagLayout extends ViewGroup {
    //总共有多少行，每行里有多少个item
    private List<List<View>> lineList = new ArrayList<>();


    public TagLayout(Context context) {
        this(context, null);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //自定义属性
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight;
        int childCount = getChildCount();//获取到子view的数量
        int lineWidth = 0;
        //先清空再添加，避免重复添加
        lineList.clear();
        //每行的item数量
        List<View> itemList = new ArrayList<>();
        lineList.add(itemList);
        for (int i = 0; i < childCount; i++) {//遍历所有子view 并测量
            View itemView = getChildAt(i);
            //单个item的最大宽高最大为父控件大小
            measureChild(itemView, widthMeasureSpec, heightMeasureSpec);
            //测量完item的大小 判断是否需要换行
            //所有item(带边距)的宽度+下一个item的宽度>父控件的宽度 换行
            MarginLayoutParams lp = (MarginLayoutParams) itemView.getLayoutParams();
            int marginWidth = lp.leftMargin + lp.rightMargin;
            //单个item的宽度
            int itemWidth = itemView.getMeasuredWidth() + marginWidth;
            if (lineWidth + itemWidth > parentWidth - getPaddingRight() - getPaddingLeft()) {
                //放不下就添加一行
                itemList = new ArrayList<>();
                itemList.add(itemView);
                lineList.add(itemList);
                lineWidth = itemWidth + getPaddingRight() + getPaddingLeft();
            } else {
                itemList.add(itemView);
                //不需要换行就累加起来所有item的宽度，用于判断
                lineWidth += itemWidth;
            }

        }
        //父控件的高度=所有item的高度+item们的上边边距+父控件的上下padding
        MarginLayoutParams lp = (MarginLayoutParams) getChildAt(0).getLayoutParams();
        parentHeight = lineList.size() * getChildAt(0).getMeasuredHeight()
                + getPaddingBottom() + getPaddingTop() + lineList.size() * (lp.topMargin + lp.bottomMargin);
        setMeasuredDimension(parentWidth + getPaddingLeft() + getPaddingRight(), parentHeight);
    }

    /**
     * 让ViewGroup能够支持margin属性
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        MarginLayoutParams lp = (MarginLayoutParams) getChildAt(0).getLayoutParams();
        int left = getPaddingLeft() + lp.leftMargin, top = getPaddingTop() + lp.topMargin, right = 0, bottom;
        for (int i = 0; i < lineList.size(); i++) {
            for (int j = 0; j < lineList.get(i).size(); j++) {
                View item = lineList.get(i).get(j);
//                MarginLayoutParams itemLp = (MarginLayoutParams) item.getLayoutParams();
                right = left + item.getMeasuredWidth();
                bottom = top + item.getMeasuredHeight();
                LogUtils.i(i + "left:" + left);
                LogUtils.i(i + "top:" + top);
                LogUtils.i(i + "right:" + right);
                LogUtils.i(i + "bottom:" + bottom);
                item.layout(left, top, right, bottom);
                left += item.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }
            left = getPaddingLeft() + lp.leftMargin;
            top += getChildAt(0).getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
        }
    }

}
