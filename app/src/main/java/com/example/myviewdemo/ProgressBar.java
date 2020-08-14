package com.example.myviewdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.SizeUtils;

/**
 * @Description:
 * @Author: 韩庆凯
 * @CreateDate: 2020/6/15 14:09
 * @UpdateUser:
 * @UpdateDate: 2020/6/15 14:09
 * @UpdateRemark:
 * @Version:
 */
public class ProgressBar extends View {
    public static final int INNER_BACKGROUND_DEFAULT = R.color.colorPrimary;
    public static final int OUTER_BACKGROUND_DEFAULT = R.color.colorAccent;
    public static final int TEXTCOLOR_DEFAULT = R.color.colorAccent;
    public static final int ROUND_WIDTH_DEFAULT = SizeUtils.dp2px(5);
    public static final int TEXTSIZE_DEFAULT = SizeUtils.dp2px(15);
    private int mInnerBg;
    private int mOuterBg;
    private float mRoundWidth;
    private int mTextSize;
    private int mTextColor;
    private Paint mInnerPaint;
    private Paint mOuterPaint;
    private Paint mTextPaint;
    private float mMax = 100;
    private float mProgress = 0;

    public ProgressBar(Context context) {
        this(context, null);
    }

    public ProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //自定义属性
        initAttrs(context, attrs);
        //初始化画笔
        initPaint();

    }

    public float getmMax() {
        return mMax;
    }

    public synchronized void setmMax(float mMax) {
        if (mMax < 0) {
            mMax = 0;
        }
        this.mMax = mMax;
    }

    public float getmProgress() {
        return mProgress;
    }

    public void setmProgress(float mProgress) {
        if (mProgress < 0) {
            mProgress = 0;
        }
        this.mProgress = mProgress;
        invalidate();
    }

    private void initPaint() {
        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(mInnerBg);
        mInnerPaint.setStrokeWidth(mRoundWidth);
        mInnerPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);

        mOuterPaint = new Paint();
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setColor(mOuterBg);
        mOuterPaint.setStrokeWidth(mRoundWidth);
        mOuterPaint.setStyle(Paint.Style.STROKE);
        mOuterPaint.setStrokeCap(Paint.Cap.ROUND);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressBar);
        mInnerBg = typedArray.getColor(R.styleable.ProgressBar_innerBackground, ColorUtils.getColor(INNER_BACKGROUND_DEFAULT));
        mOuterBg = typedArray.getColor(R.styleable.ProgressBar_outerBackground, ColorUtils.getColor(OUTER_BACKGROUND_DEFAULT));
        mRoundWidth = typedArray.getDimensionPixelSize(R.styleable.ProgressBar_roundWidth, ROUND_WIDTH_DEFAULT);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.ProgressBar_progressTextSize, TEXTSIZE_DEFAULT);
        mTextColor = typedArray.getColor(R.styleable.ProgressBar_progressTextColor, ColorUtils.getColor(TEXTCOLOR_DEFAULT));
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //保证是正方形
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int sideLength = Math.min(width, height);
        setMeasuredDimension(sideLength, sideLength);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画内圆
        int radius = getWidth() / 2;
        canvas.drawCircle(radius, radius, radius - mRoundWidth / 2, mInnerPaint);
        //画内圆  --圆弧组成
        RectF oval = new RectF(0 + mRoundWidth / 2, 0 + mRoundWidth / 2, getWidth() - mRoundWidth / 2, getHeight() - mRoundWidth / 2);
        if (mProgress == 0) return;
        float persent = mProgress / mMax;
        canvas.drawArc(oval, 0, persent * 360, false, mOuterPaint);
        //画文字
        String text = (int)(persent * 100) + "%";
        Rect textBounds = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), textBounds);
        int x = getWidth() / 2 - textBounds.width() / 2;
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        int dy = (Math.abs(fontMetrics.bottom) - Math.abs(fontMetrics.top)) - fontMetrics.bottom;
        int baseline = getHeight() / 2 + dy;
        canvas.drawText(text, x, baseline, mTextPaint);
    }
}
