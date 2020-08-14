package com.example.myviewdemo;

import android.app.DirectAction;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * @Description:
 * @Author: 韩庆凯
 * @CreateDate: 2020/6/2 15:17
 * @UpdateUser:
 * @UpdateDate: 2020/6/2 15:17
 * @UpdateRemark:
 * @Version:
 */
public class ColorTrackTextView extends androidx.appcompat.widget.AppCompatTextView {
    private Paint mOriginPaint, mChangePaint;
    private float mCurrentProgress = 0f;
    private Direction direction = Direction.LEFT_TO_RIGHT;

    //朝向
    public enum Direction {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT
    }

    public ColorTrackTextView(Context context) {
        this(context, null);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context, attrs);
    }

    private void initPaint(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);
        int orignColor = typedArray.getColor(R.styleable.ColorTrackTextView_orignColor, getTextColors().getDefaultColor());
        int changeColor = typedArray.getColor(R.styleable.ColorTrackTextView_changeColor, getTextColors().getDefaultColor());
        mOriginPaint = getPaintByColor(orignColor);
        mChangePaint = getPaintByColor(changeColor);

        typedArray.recycle();
    }

    /**
     * 根据颜色获取画笔
     */
    private Paint getPaintByColor(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setTextSize((getTextSize()));
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int middle = (int) (mCurrentProgress * getWidth());
        if (direction == Direction.LEFT_TO_RIGHT) {
            drawText(canvas, mOriginPaint, 0, middle);
            drawText(canvas, mChangePaint, middle, getWidth());
        } else {
            drawText(canvas, mOriginPaint, getWidth() - middle, getWidth());
            drawText(canvas, mChangePaint, 0, getWidth() - middle);
        }

    }

    private void drawText(Canvas canvas, Paint paint, int start, int end) {
        canvas.save();
        //绘制不变色字
        Rect rect = new Rect(start, 0, end, getHeight());
        canvas.clipRect(rect);

        String text = getText().toString();
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        //获取字体宽度
        int x = getWidth() / 2 - bounds.width() / 2;
        //获取baseline
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(text, x, baseLine, paint);
        canvas.restore();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setCurrentProgress(float currentProgress) {
        this.mCurrentProgress = currentProgress;
        invalidate();
    }
}
