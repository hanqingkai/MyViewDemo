package com.example.myviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;

/**
 * @Description:
 * @Author: 韩庆凯
 * @CreateDate: 2020/6/8 15:46
 * @UpdateUser:
 * @UpdateDate: 2020/6/8 15:46
 * @UpdateRemark:
 * @Version:
 */
public class KeyPadView extends ViewGroup {

    public static final int DEFAULT_ROW = 4;
    public static final int DEFAULT_COLUMN = 3;
    public static final int DEFAULT_ITEM_MARGIN = SizeUtils.dp2px(2);


    private int mNumColor;
    private float mNumSize;
    private int mItemPressBg;
    private int mItemNormalBg;

    private int row = DEFAULT_ROW;
    private int column = DEFAULT_COLUMN;
    private int mItemMargin = DEFAULT_ITEM_MARGIN;
    private OnItemClickListener mListener = null;

    public KeyPadView(Context context) {
        this(context, null);
    }

    public KeyPadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyPadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        setUpView();
    }

    private void setUpView() {
        removeAllViews();
        for (int i = 0; i < 11; i++) {
            TextView item = new TextView(getContext());
            //设置所需属性
            if (i == 10) {
                item.setTag(true);
                item.setText("x");
            } else {
                item.setTag(false);
                item.setText(String.valueOf(i));
            }
            item.setTextColor(mNumColor);
            item.setTextSize(TypedValue.COMPLEX_UNIT_PX, mNumSize);
            item.setGravity(Gravity.CENTER);
            item.setBackground(providerItemBg());
            item.setOnClickListener(view -> {
                if (mListener != null) {
                    boolean isDelete= (boolean) view.getTag();
                    if (!isDelete){
                        String num = ((TextView) view).getText().toString();
                        mListener.onItemClick(Integer.parseInt(num));
                    }else {
                        mListener.onDeleteClick();
                    }

                }
            });
            addView(item);
        }

    }

    private Drawable providerItemBg() {
        StateListDrawable bg = new StateListDrawable();
        //按下去的bg
        GradientDrawable pressDrawable = new GradientDrawable();
        pressDrawable.setColor(getResources().getColor(R.color.key_item_color_press));
        pressDrawable.setCornerRadius(SizeUtils.dp2px(5));
        bg.addState(new int[]{android.R.attr.state_pressed}, pressDrawable);
        GradientDrawable normalDrawable = new GradientDrawable();
        normalDrawable.setColor(getResources().getColor(R.color.key_item_color));
        normalDrawable.setCornerRadius(SizeUtils.dp2px(5));
        bg.addState(new int[]{}, normalDrawable);
        return bg;
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.KeyPadView);
        mNumColor = typedArray.getColor(R.styleable.KeyPadView_numColor, getResources().getColor(android.R.color.white));
        mNumSize = typedArray.getDimensionPixelSize(R.styleable.KeyPadView_numSize, 16);
        mItemMargin = typedArray.getDimensionPixelSize(R.styleable.KeyPadView_itemMargin, 5);
        mItemNormalBg = typedArray.getColor(R.styleable.KeyPadView_itemNormalBg, mItemNormalBg);
        mItemPressBg = typedArray.getColor(R.styleable.KeyPadView_itemPressBg, mItemPressBg);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int horizontalPadding = getPaddingLeft() + getPaddingRight();
        int verticalPadding = getPaddingTop() + getPaddingBottom();

        //三行四列布局
        //控件的宽高
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //每个控件的宽  减掉margin时需要注意
        int perItemWidth = (width - (column + 1) * mItemMargin - horizontalPadding) / column;
        //每个控件的高
        int perItemHeight = (height - (row + 1) * mItemMargin - verticalPadding) / row;
        //创造出每个控件宽高，设置展开模式
        int normalWidthSpec = MeasureSpec.makeMeasureSpec(perItemWidth, MeasureSpec.EXACTLY);
        //删除键宽度占两份
        int deleteWidthSpec = MeasureSpec.makeMeasureSpec(perItemWidth * 2, MeasureSpec.EXACTLY);
        int heightSpec = MeasureSpec.makeMeasureSpec(perItemHeight, MeasureSpec.EXACTLY);
        //测量每一个子view
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.measure((boolean) view.getTag() ? deleteWidthSpec : normalWidthSpec, heightSpec);
        }
        //测量自己
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int left = mItemMargin + paddingLeft, top, right, bottom;
        for (int i = 0; i < getChildCount(); i++) {
            View item = getChildAt(i);
            //求当前item再第几行，列
            int rowIndex = i / column;
            int columnIndex = i % column;
            if (columnIndex == 0) {
                left = mItemMargin + paddingLeft;
            }
            top = rowIndex * item.getMeasuredHeight() + mItemMargin * (rowIndex + 1) + paddingTop;
            right = left + item.getMeasuredWidth();
            bottom = top + item.getMeasuredHeight();
            item.layout(left, top, right, bottom);
            left += item.getMeasuredWidth() + mItemMargin;
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int num);
        void onDeleteClick();
    }

    public int getmNumColor() {
        return mNumColor;
    }

    public void setmNumColor(int mNumColor) {
        this.mNumColor = mNumColor;
    }

    public float getmNumSize() {
        return mNumSize;
    }

    public void setmNumSize(float mNumSize) {
        this.mNumSize = mNumSize;
    }

    public int getmItemPressBg() {
        return mItemPressBg;
    }

    public void setmItemPressBg(int mItemPressBg) {
        this.mItemPressBg = mItemPressBg;
    }

    public int getmItemNormalBg() {
        return mItemNormalBg;
    }

    public void setmItemNormalBg(int mItemNormalBg) {
        this.mItemNormalBg = mItemNormalBg;
    }

    public int getmItemMargin() {
        return mItemMargin;
    }

    public void setmItemMargin(int mItemMargin) {
        this.mItemMargin = mItemMargin;
    }
}
