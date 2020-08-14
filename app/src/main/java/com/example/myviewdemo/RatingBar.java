package com.example.myviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.SizeUtils;

/**
 * @Description: 功能不完善，根据需要自己添加功能和修复bug
 * @Author: 韩庆凯
 * @CreateDate: 2020/6/17 13:59
 * @UpdateUser:
 * @UpdateDate: 2020/6/17 13:59
 * @UpdateRemark:
 * @Version:
 */
public class RatingBar extends View {

    private Bitmap mStarNormal;
    private Bitmap mStarFocus;
    private int mStarNum = 5;
    private int mCurrentStar;
    private int startSpace = SizeUtils.dp2px(5);

    public RatingBar(Context context) {
        this(context, null);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        int normal = typedArray.getResourceId(R.styleable.RatingBar_starNormal, R.mipmap.img_collect_normal);
        mStarNormal = BitmapFactory.decodeResource(getResources(), normal);
        int focus = typedArray.getResourceId(R.styleable.RatingBar_starFocus, R.mipmap.img_collect_press);
        mStarFocus = BitmapFactory.decodeResource(getResources(), focus);
        mStarNum = typedArray.getInteger(R.styleable.RatingBar_starNum, 5);
        typedArray.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightPadding = getPaddingBottom() + getPaddingTop();
        int widthPadding = getPaddingLeft() + getPaddingRight();
        int height = mStarNormal.getHeight() + heightPadding;
        int width = mStarNormal.getWidth() * mStarNum + startSpace * (mStarNum - 1) + widthPadding;
        setMeasuredDimension(width, height);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < mStarNum; i++) {
            if (mCurrentStar > i) {
                canvas.drawBitmap(mStarFocus,
                        getPaddingLeft() + i * mStarNormal.getWidth() + i * startSpace, getPaddingTop(), null);
            } else {
                canvas.drawBitmap(mStarNormal,
                        getPaddingLeft() + i * mStarNormal.getWidth() + i * startSpace, getPaddingTop(), null);
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float x = event.getX();
            //触摸的事哪个星
            int currentStar = (int) (x / mStarNormal.getWidth() + 1);
            if (currentStar < 0) currentStar = 0;
            if (currentStar > mStarNum) currentStar = mStarNum;
            if (currentStar == mCurrentStar) return true;//在同一个star范围内，不需要再重新绘制
            mCurrentStar = currentStar;
            invalidate();
        }
        return true;
    }
}
