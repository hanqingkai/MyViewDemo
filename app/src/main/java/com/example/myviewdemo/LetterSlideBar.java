package com.example.myviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.SizeUtils;

/**
 * @Description:
 * @Author: 韩庆凯
 * @CreateDate: 2020/6/19 14:20
 * @UpdateUser:
 * @UpdateDate: 2020/6/19 14:20
 * @UpdateRemark:
 * @Version:
 */
public class LetterSlideBar extends View {
    public static final int NORMAL_TEXT_SIZE = SizeUtils.dp2px(15);
    public static final int PRESS_TEXT_SIZE = SizeUtils.dp2px(20);

    private int mLetterTextSizeNormal = NORMAL_TEXT_SIZE;
    private int mLetterTextSizePress = PRESS_TEXT_SIZE;
    private int mLetterTextColorNormal = ColorUtils.getColor(android.R.color.black);
    private int mLetterTextColorPress = ColorUtils.getColor(android.R.color.holo_red_dark);
    private int mLetterBackgroundNormal = ColorUtils.getColor(android.R.color.darker_gray);
    private int mLetterBackgoundPress = ColorUtils.getColor(android.R.color.holo_green_dark);
    private Paint mNormalPaint;
    private Paint mPressPaint;
    private String[] letter = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    private String currentLetter;
    private OnLetterTouchListener mOnLetterTouchListener = null;

    public LetterSlideBar(Context context) {
        this(context, null);
    }

    public LetterSlideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSlideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(mLetterBackgroundNormal);
        //初始化attrs
        initAttrs(context, attrs);
        //初始化画笔
        initPaints();
    }

    private void initPaints() {
        mNormalPaint = new Paint();
        mNormalPaint.setColor(mLetterTextColorNormal);
        mNormalPaint.setTextSize(mLetterTextSizeNormal);
        mNormalPaint.setAntiAlias(true);
        mPressPaint = new Paint();
        mPressPaint.setColor(mLetterTextColorPress);
        mPressPaint.setTextSize(mLetterTextSizePress);
        mPressPaint.setAntiAlias(true);
    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LetterSlideBar);
        mLetterTextSizeNormal = typedArray.getDimensionPixelSize(R.styleable.LetterSlideBar_letterTextSizeNormal, mLetterTextSizeNormal);
        mLetterTextSizePress = typedArray.getDimensionPixelSize(R.styleable.LetterSlideBar_letterTextSizePress, mLetterTextSizePress);
        mLetterTextColorNormal = typedArray.getColor(R.styleable.LetterSlideBar_letterTextColorNormal, mLetterTextColorNormal);
        mLetterTextColorPress = typedArray.getColor(R.styleable.LetterSlideBar_letterTextColorPress, mLetterTextColorPress);
        mLetterBackgroundNormal = typedArray.getColor(R.styleable.LetterSlideBar_letterBackgroundNormal, mLetterBackgroundNormal);
        mLetterBackgoundPress = typedArray.getColor(R.styleable.LetterSlideBar_letterBackgroundPress, mLetterBackgoundPress);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float textWidth = mNormalPaint.measureText(letter[0]);
        int width = (int) (getPaddingLeft() + getPaddingRight() + textWidth);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int singleHeight = (height - getPaddingBottom() - getPaddingTop()) / letter.length;
        for (int i = 0; i < letter.length; i++) {
            if (letter[i].equals(currentLetter)) {
                Paint.FontMetricsInt fontMetricsInt = mPressPaint.getFontMetricsInt();
                int centerY = singleHeight / 2 + singleHeight * i + getPaddingTop();
                int dy = (Math.abs(fontMetricsInt.bottom) - Math.abs(fontMetricsInt.bottom)) / 2 - fontMetricsInt.bottom;
                int y = dy + centerY;
                int x = (int) (width / 2 - mPressPaint.measureText(letter[i]) / 2);//取字母中间的x坐标，避免字母宽度不同展示不居中
                canvas.drawText(letter[i], x, y, mPressPaint);
            } else {
                Paint.FontMetricsInt fontMetricsInt = mNormalPaint.getFontMetricsInt();
                int centerY = singleHeight / 2 + singleHeight * i + getPaddingTop();
                int dy = (Math.abs(fontMetricsInt.bottom) - Math.abs(fontMetricsInt.bottom)) / 2 - fontMetricsInt.bottom;
                int y = dy + centerY;
                int x = (int) (width / 2 - mNormalPaint.measureText(letter[i]) / 2);//取字母中间的x坐标，避免字母宽度不同展示不居中
                canvas.drawText(letter[i], x, y, mNormalPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setBackgroundColor(mLetterBackgoundPress);
            case MotionEvent.ACTION_MOVE:
                //当前触摸的位置
                float currentY = event.getY();
                //当前字母位置=currentY/字母高度
                int letterHeight = (getHeight() - getPaddingBottom() - getPaddingTop()) / letter.length;
                int currentPosition = (int) (currentY / letterHeight);
                if (currentPosition < 0) currentPosition = 0;
                if (currentPosition > letter.length - 1) currentPosition = letter.length - 1;
                //取出当前被触摸的字符
                currentLetter = letter[currentPosition];
                break;
            case MotionEvent.ACTION_UP:
                currentLetter = "";
                setBackgroundColor(mLetterBackgroundNormal);
                break;
        }
        if (mOnLetterTouchListener != null) {
            mOnLetterTouchListener.touch(currentLetter);
        }

        invalidate();
        return true;
    }

    public void setOnLetterTouchListener(OnLetterTouchListener mOnLetterTouchListener) {
        this.mOnLetterTouchListener = mOnLetterTouchListener;
    }

    public interface OnLetterTouchListener {
        void touch(String letter);
    }
}
