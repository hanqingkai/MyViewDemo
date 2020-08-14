package com.example.myviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.provider.CalendarContract;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @Description:
 * @Author: 韩庆凯
 * @CreateDate: 2020/6/2 11:13
 * @UpdateUser:
 * @UpdateDate: 2020/6/2 11:13
 * @UpdateRemark:
 * @Version:
 */
public class QQStepView extends View {
    private int mOuterColor = Color.RED;
    private int mInnerColor = Color.BLUE;
    private int mStepWidth = 20;//px
    private int mStepTextSize;
    private int mStepTextColor;
    Paint mOutPaint;
    Paint mInnerPaint;
    Paint mTextPaint;
    private float mStepMax=0;
    private int mCurrentStep=0;

    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        mOuterColor = typedArray.getColor(R.styleable.QQStepView_outerColor, mOuterColor);
        mInnerColor = typedArray.getColor(R.styleable.QQStepView_innerColor, mInnerColor);
        mStepWidth = typedArray.getDimensionPixelSize(R.styleable.QQStepView_stepWidth, mStepWidth);
        mStepTextSize = typedArray.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize, mStepTextSize);
        mStepTextColor = typedArray.getColor(R.styleable.QQStepView_stepTextColor, mStepTextColor);
        typedArray.recycle();
        mOutPaint = new Paint();
        mOutPaint.setAntiAlias(true);
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);
        mOutPaint.setStyle(Paint.Style.STROKE);
        mOutPaint.setColor(mOuterColor);
        mOutPaint.setStrokeWidth(mStepWidth);

        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setStrokeWidth(mStepWidth);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mStepTextColor);
        mTextPaint.setTextSize(mStepTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center = getWidth() / 2;
        int radius = getWidth() / 2 - mStepWidth / 2;

        RectF rect = new RectF(mStepWidth / 2, mStepWidth / 2, getWidth() - mStepWidth / 2, getWidth() - mStepWidth / 2);
        canvas.drawArc(rect, 135, 270, false, mOutPaint);
        if (mStepMax == 0) {
            return;
        }
        float sweepAngle = (float) mCurrentStep / mStepMax;
        canvas.drawArc(rect, 135, sweepAngle * 270, false, mInnerPaint);


        String StepText = mCurrentStep + "";
        Rect textBounds = new Rect();
        mTextPaint.getTextBounds(StepText, 0, StepText.length(), textBounds);
        int dx = getWidth() / 2 - textBounds.width() / 2;
        Paint.FontMetricsInt fontMetricsInt=mTextPaint.getFontMetricsInt();
        int dy=fontMetricsInt.bottom/2-fontMetricsInt.top/2-fontMetricsInt.bottom;
        int baseLine=getHeight()/2+dy;
        canvas.drawText(StepText,dx,baseLine,mTextPaint);

    }
    public void  setStepMax(int stepMax){
        this.mStepMax=stepMax;
    }
    public void setCurrentStep(int currentStep){
        this.mCurrentStep=currentStep;
        invalidate();
    }
}
