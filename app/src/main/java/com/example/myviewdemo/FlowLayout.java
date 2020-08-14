package com.example.myviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: 韩庆凯
 * @CreateDate: 2020/6/5 9:45
 * @UpdateUser:
 * @UpdateDate: 2020/6/5 9:45
 * @UpdateRemark:
 * @Version:
 */
public class FlowLayout extends ViewGroup {
    public static final int DEFAULT_LINE = -1;
    public static final int DEFAULT_HORZONTAL_MARGIN = SizeUtils.dp2px(5f);
    public static final int DEFAULT_VRETICAL_MARGIN = SizeUtils.dp2px(5f);
    public static final int DEFAULT_BORDER_RADIUS = SizeUtils.dp2px(5f);
    public static final int DEFAULT_TEXT_MAX_Length = -1;
    private int mTextColor;
    private int mBorderColor;
    private float mBorderRadius;
    private int mMaxLines;
    private int mHorizontalMargin;
    private int mVerticalMargin;
    private int mTextMaxLine;
    private List<String> mData = new ArrayList<>();
    private onFlowLayoutTabClickListener mListener = null;


    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }


    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        mMaxLines = typedArray.getInteger(R.styleable.FlowLayout_maxLine, DEFAULT_LINE);
        if (mMaxLines != -1 && mMaxLines < 1) {
            throw new IllegalThreadStateException("mMaxLines can not be less 1.");
        }
        mHorizontalMargin = (int) typedArray.getDimension(R.styleable.FlowLayout_itemHorizontalMargin, DEFAULT_HORZONTAL_MARGIN);
        mVerticalMargin = (int) typedArray.getDimension(R.styleable.FlowLayout_itemVerticalMargin, DEFAULT_VRETICAL_MARGIN);
        mTextMaxLine = typedArray.getInteger(R.styleable.FlowLayout_textMaxLength, DEFAULT_TEXT_MAX_Length);
        mTextColor = typedArray.getColor(R.styleable.FlowLayout_textColor, getResources().getColor(R.color.textGray));
        mBorderColor = typedArray.getColor(R.styleable.FlowLayout_borderColor, getResources().getColor(R.color.textGray));
        mBorderRadius = typedArray.getDimensionPixelSize(R.styleable.FlowLayout_borderRadius, DEFAULT_BORDER_RADIUS);
        typedArray.recycle();
    }

    public void setData(List<String> data) {
        mData.clear();
        mData.addAll(data);
        //根据数据创建子view并添加进来
        setupChildern();

    }

    private void setupChildern() {
        //清空原有view
        removeAllViews();
        //添加view
        for (String text : mData) {
            TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.flowlayout_text_tab, this, false);
//            TextView textView  =new TextView(getContext());
            if (mTextMaxLine!=DEFAULT_TEXT_MAX_Length) {
                textView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_TEXT_MAX_Length)});
            }
            textView.setText(text);
            textView.setOnClickListener(view -> {
                if (mListener != null) {
                    mListener.onTabClickListener(text);
                }
            });
            addView(textView);
        }
    }

    private List<List<View>> mLines = new ArrayList<>();

    /**
     * 这两个值来自于父控件，包含值和模式
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //最外层parent控件的宽高  逐级自动向内测量子view
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int parentWidthSpec = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeightSpec = MeasureSpec.getSize(heightMeasureSpec);

        //测量子view
        int childCount = getChildCount();
        if (childCount == 0) return;
        // 不适用父控件的宽高，自己确定子view的宽高
        // AT_MOST：最大宽,高度度和父控件一样宽,高
        int childWidthSpaec = MeasureSpec.makeMeasureSpec(parentWidthSpec, MeasureSpec.AT_MOST);
        int childHeightSpaec = MeasureSpec.makeMeasureSpec(parentHeightSpec, MeasureSpec.AT_MOST);
        //先清空，避免重复添加
        mLines.clear();
        //添加默认行
        List<View> line = new ArrayList<>();
        mLines.add(line);
        for (int i = 0; i < childCount; i++) {
            //拿到子view，确定添加到哪一行去
            View child = getChildAt(i);
            //严谨一点，万一有不显示的子view
            if (child.getVisibility() != VISIBLE) {
                continue;
            }
            //测量子view
            measureChild(child, childWidthSpaec, childHeightSpaec);

            //判断将要添加的view是否可以添加到当前行
            boolean canBeAdd = checkChildCanBeAdd(line, child, parentWidthSpec);
            if (!canBeAdd) {
                //超出设置的最大行就不再添加
                if (mMaxLines != -1 && mLines.size() >= mMaxLines) {
                    break;
                }
                //上边那行已容不下新的子view了，就新创建一行
                //上边不让我挤，我就自己开辟一行把
                line = new ArrayList<>();
                mLines.add(line);
            }
            line.add(child);
        }
        //根据所有子view的高计算flowlayout的高
        //取出第一个子view的高度确定一个每一个子view的高度
        View childAt = getChildAt(0);
        int childHeight = childAt.getMeasuredWidth();
        //flowlayout的高度=一个的高度*数量
        int parentHeightTargetSize = childHeight * mLines.size() + (mLines.size() + 1) * mVerticalMargin
                //加上设置控件的上下padding
                + getPaddingTop() + getPaddingBottom();
        //测量自己
        setMeasuredDimension(widthMeasureSpec, parentHeightTargetSize);
    }

    private boolean checkChildCanBeAdd(List<View> line, View child, int parentWidthSpec) {
        int measuredWidth = child.getMeasuredWidth();
        int totalWidth = mHorizontalMargin + getPaddingLeft();
        for (View view : line) {
            totalWidth += view.getMeasuredWidth() + mHorizontalMargin + getPaddingRight();
        }
        totalWidth += measuredWidth + mHorizontalMargin;
        //已有的所以子view的宽度+将要添加的view超出了最外层父控件的宽度，则需要换行添加,否则直接添加
        //当前行已容不下新的子view了，去下一行把
        return totalWidth <= parentWidthSpec;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child = getChildAt(0);
        //getpaddingleft： 加上外部属性设置的padding
        int currentLeft = mHorizontalMargin + getPaddingLeft();
        int currentRight = mHorizontalMargin + getPaddingLeft();
        int currentTop = mVerticalMargin + getPaddingTop();
        int currentBottom = child.getMeasuredHeight() + mVerticalMargin + getPaddingTop();
        for (List<View> mLine : mLines) {
            for (View view : mLine) {
                //布局每一行
                int width = view.getMeasuredWidth();
                currentRight += width;
                //判断tab是否超出了最右侧边界
                if (currentRight > getMeasuredWidth() - mHorizontalMargin) {
                    currentRight = getMeasuredWidth() - mHorizontalMargin;
                }
                view.layout(currentLeft, currentTop, currentRight, currentBottom);
                currentLeft = currentRight + mHorizontalMargin;
                currentRight += mHorizontalMargin;
            }
            currentLeft = mHorizontalMargin + getPaddingLeft();
            currentRight = mHorizontalMargin + getPaddingLeft();
            currentBottom += child.getMeasuredHeight() + mVerticalMargin;
            currentTop += child.getMeasuredHeight() + mVerticalMargin;
        }
    }

    public int getmTextColor() {
        return mTextColor;
    }

    public void setmTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public int getmBorderColor() {
        return mBorderColor;
    }

    public void setmBorderColor(int mBorderColor) {
        this.mBorderColor = mBorderColor;
    }

    public float getmBorderRadius() {
        return mBorderRadius;
    }

    public void setmBorderRadius(float mBorderRadius) {
        this.mBorderRadius = mBorderRadius;
    }

    public int getmMaxLines() {
        return mMaxLines;
    }

    public void setmMaxLines(int mMaxLines) {
        this.mMaxLines = mMaxLines;
    }

    public float getmHorizontalMargin() {
        return mHorizontalMargin;
    }

    public void setHorizontalMargin(int mHorizontalMargin) {
        this.mHorizontalMargin = SizeUtils.dp2px(mHorizontalMargin);
    }

    public float getmVerticalMargin() {
        return mVerticalMargin;
    }

    public void setVerticalMargin(int mVerticalMargin) {
        this.mVerticalMargin = SizeUtils.dp2px(mVerticalMargin);
    }

    public int getmTextMaxLine() {
        return mTextMaxLine;
    }

    public void setmTextMaxLine(int mTextMaxLine) {
        this.mTextMaxLine = mTextMaxLine;
    }

    public void setOnFlowLayoutTabClickListener(onFlowLayoutTabClickListener listener) {
        this.mListener = listener;
    }

    public interface onFlowLayoutTabClickListener {
        void onTabClickListener(String text);
    }
}
