package com.example.myviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @Description:
 * @Author: 韩庆凯
 * @CreateDate: 2020/6/17 9:22
 * @UpdateUser:
 * @UpdateDate: 2020/6/17 9:22
 * @UpdateRemark:
 * @Version:
 */
public class ShapeView extends View {


    enum Shape {
        Circle, Square, Triangle
    }

    private Paint mPaint;
    private Shape mCurrentShape = Shape.Circle;

    public Shape getmCurrentShape() {
        return mCurrentShape;
    }

    public void setmCurrentShape(Shape mCurrentShape) {
        this.mCurrentShape = mCurrentShape;
        invalidate();
    }


    public ShapeView(Context context) {
        this(context, null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
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
        switch (mCurrentShape) {
            case Circle:
                drawCircle(canvas);
                break;
            case Square:
                drawSquare(canvas);
                break;
            case Triangle:
                drawTriangle(canvas);
                break;
            default:
                break;

        }
    }

    public void exCheange() {
        switch (mCurrentShape) {
            case Circle:
                mCurrentShape = Shape.Square;
                invalidate();
                break;
            case Square:
                mCurrentShape = Shape.Triangle;
                invalidate();
                break;
            case Triangle:
                mCurrentShape = Shape.Circle;
                invalidate();
                break;
            default:
                break;

        }

    }

    private Path mPath;

    private void drawTriangle(Canvas canvas) {
        mPaint.setColor(Color.RED);
        if (mPath == null) {
            mPath = new Path();
            float width = getWidth();
            mPath.moveTo(width / 2, 0);
            //求出高度，保证是等边三角形
            float height = (float) Math.sqrt(Math.pow(width, 2) - Math.pow(width / 2, 2));
            mPath.lineTo(0, height);
            mPath.lineTo(width, height);
            mPath.close();
        }
        canvas.drawPath(mPath, mPaint);
    }

    private void drawSquare(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
    }

    private void drawCircle(Canvas canvas) {
        int center = getWidth() / 2;
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(center, center, center, mPaint);

    }
}
