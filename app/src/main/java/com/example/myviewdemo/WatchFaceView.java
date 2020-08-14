package com.example.myviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.SizeUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @Description:
 * @Author: 韩庆凯
 * @CreateDate: 2020/6/12 8:57
 * @UpdateUser:
 * @UpdateDate: 2020/6/12 8:57
 * @UpdateRemark:
 * @Version:
 */
public class WatchFaceView extends View {

    private static final String TAG = "WatchFaceView";
    private int mHourColor;
    private int mMinColor;
    private int mSecondColor;
    private int mScaleColor;
    private int mBgResId;
    private boolean mScaleShow;
    private Paint mSeondPaint;
    private Paint mMinPaint;
    private Paint mHourPaint;
    private Paint mScalaPaint;
    private Bitmap mBackgroundImage = null;
    private int mWidth;
    private int mHeight;
    private Rect srcRect;
    private Rect desRect;
    private Calendar mCalendar;
    public final int innerCircleRadius = SizeUtils.dp2px(5);


    public WatchFaceView(Context context) {
        this(context, null);
    }

    public WatchFaceView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WatchFaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取相关属性
        initAttrs(context, attrs);
        //获取日历
        mCalendar = Calendar.getInstance();
        //设置时区
        mCalendar.setTimeZone(TimeZone.getDefault());
        //创建画笔
        initPaints();
    }

    /**
     * 创建相关画笔
     */
    private void initPaints() {
        //秒针
        mSeondPaint = new Paint();
        mSeondPaint.setColor(mSecondColor);
        mSeondPaint.setStyle(Paint.Style.STROKE);
        mSeondPaint.setStrokeWidth(5f);
        mSeondPaint.setAntiAlias(true);
        //分针
        mMinPaint = new Paint();
        mMinPaint.setColor(mMinColor);
        mMinPaint.setStyle(Paint.Style.STROKE);
        mMinPaint.setStrokeWidth(10f);
        mMinPaint.setAntiAlias(true);
        //时针
        mHourPaint = new Paint();
        mHourPaint.setColor(mHourColor);
        mHourPaint.setStyle(Paint.Style.STROKE);
        mHourPaint.setStrokeCap(Paint.Cap.ROUND);
        mHourPaint.setStrokeWidth(15f);
        mHourPaint.setAntiAlias(true);

        //
        mScalaPaint = new Paint();
        mScalaPaint.setColor(mScaleColor);
        mScalaPaint.setStyle(Paint.Style.STROKE);
        mScalaPaint.setStrokeWidth(3f);
        mScalaPaint.setAntiAlias(true);
    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WatchFaceView);
        mHourColor = typedArray.getColor(R.styleable.WatchFaceView_hourColor, ContextCompat.getColor(getContext(), R.color.hourDefaultColor));
        mMinColor = typedArray.getColor(R.styleable.WatchFaceView_minColor, ColorUtils.getColor(R.color.minDefaultColor));
        mSecondColor = typedArray.getColor(R.styleable.WatchFaceView_secondColor, ColorUtils.getColor(R.color.secondDefaultColor));
        mScaleColor = typedArray.getColor(R.styleable.WatchFaceView_scaleColor, ColorUtils.getColor(R.color.scaleDefaultColor));
        mBgResId = typedArray.getResourceId(R.styleable.WatchFaceView_watchfaceBackground, -1);
        mScaleShow = typedArray.getBoolean(R.styleable.WatchFaceView_scaleShow, true);
        if (mBgResId != -1) {
            mBackgroundImage = BitmapFactory.decodeResource(getResources(), mBgResId);
        }
        typedArray.recycle();
    }

    /**
     * 测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量自己
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //减掉外边距
        int widthTargetSize = widthSize - getPaddingLeft() - getPaddingRight();
        int heightTargetSize = heightSize - getPaddingTop() - getPaddingBottom();
        //判断宽高大小 取小值保证正方形
        int targetSize = Math.min(widthTargetSize, heightTargetSize);
        setMeasuredDimension(targetSize, targetSize);
        initRect();
    }

    private void initRect() {
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        if (mBackgroundImage == null) {
            Log.d(TAG, "mBackground is not null ...");
            return;
        }
        //源坑--从图片中截取，如果和图片大小一样，那么就截取图片所有内容
        srcRect = new Rect();
        srcRect.left = 0;
        srcRect.top = 0;
        srcRect.right = mBackgroundImage.getWidth();
        srcRect.bottom = mBackgroundImage.getHeight();
        //目标坑--要天放源内容的地方
        desRect = new Rect();
        desRect.left = 0;
        desRect.top = 0;
        desRect.right = mWidth;
        desRect.bottom = mHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制背景
        canvas.drawColor(ColorUtils.getColor(R.color.colorPrimary));
        //获取当前时间
        long currentTimeMillis = System.currentTimeMillis();
        mCalendar.setTimeInMillis(currentTimeMillis);
        //绘制刻度盘
        drawScale(canvas);
        int secondValue = mCalendar.get(Calendar.SECOND);
        int radius = (int) (mWidth / 2f);
        if (secondValue==0) {//避免出现跳动和指针覆盖的情况
            //绘制时针
            drawHourLine(canvas, radius);
            //绘制分针
            drawMinLine(canvas, radius);
            //绘制秒针针
            drawSecondLine(canvas, radius);
        }else {
            //绘制秒针针
            drawSecondLine(canvas, radius);
            //绘制分针
            drawMinLine(canvas, radius);
            //绘制时针
            drawHourLine(canvas, radius);
        }
    }

    private void drawSecondLine(Canvas canvas, int radius) {
        int secondValue = mCalendar.get(Calendar.SECOND);
        int secondRadius =  (int) (radius * 0.8f);
        float secondRotate = secondValue * 6f;
        canvas.save();
        canvas.rotate(secondRotate, radius, radius);
        canvas.drawLine(radius, radius - secondRadius, radius, radius - innerCircleRadius, mSeondPaint);
        canvas.restore();
    }

    private void drawMinLine(Canvas canvas, int radius) {
        int minValue = mCalendar.get(Calendar.MINUTE);
        int minRadius =  (int) (radius * 0.75f);
        float minRotate = minValue * 6f;
        canvas.save();
        canvas.rotate(minRotate, radius, radius);
        canvas.drawLine(radius, radius - minRadius, radius, radius - innerCircleRadius, mMinPaint);
        canvas.restore();
    }

    private void drawHourLine(Canvas canvas, int radius) {
        int hourValue = mCalendar.get(Calendar.HOUR);
        int hourRadius = (int) (radius * 0.8f);
        //60分钟是走30度  偏移速度就是1/2度每分钟 偏移角度=当前分钟数*1/2
        float hourOffsetRotate =  mCalendar.get(Calendar.MINUTE) / 2f;
        //时针所在的位置角度=  当前时间*一个小时的度数+偏移的角度
        float hourRotate = hourValue * 30 + hourOffsetRotate;
        //旋转的角度
        canvas.rotate(hourRotate, radius, radius);
        canvas.drawLine(radius, radius - hourRadius, radius, radius - innerCircleRadius, mHourPaint);
    }

    private void drawScale(Canvas canvas) {
        if (mBackgroundImage != null) {
            canvas.drawBitmap(mBackgroundImage, srcRect, desRect, mScalaPaint);
        } else {
            int radius = mWidth / 2;
            //画中心点 小圆圈
            canvas.drawCircle(radius, radius, innerCircleRadius, mScalaPaint);
            //画表盘
            //内环半径
            int innerC = (int) (mWidth / 2 * 0.85f);
            //外环半径
            int outerC = (int) (mWidth / 2 * 0.95f);

            /**
             // 方法-  取大圆环和小圆环中间部分
             for (int i = 0; i < 12; i++) {
             double th = i * Math.PI * 2 / 12;
             //内环
             int innerB = (int) (Math.cos(th) * innerC);
             int innerX = mHeight / 2 - innerB;
             int innerA = (int) (innerC * Math.sin(th));
             int innerY = mWidth / 2 + innerA;
             //外环
             int outerB = (int) (Math.cos(th) * outerC);
             int outerX = mHeight / 2 - outerB;
             int outerA = (int) (outerC * Math.sin(th));
             int outerY = mWidth / 2 + outerA;
             canvas.drawLine(innerX, innerY, outerX, outerY, mScalaPaint);
             } *
             */
            //方法二  思路：先画出一根刻度线 其余通过旋转角度画出，循环形成原型
            canvas.save();
            for (int i = 0; i < 12; i++) {
                //通过坐标获得线段
                canvas.drawLine(radius, radius - outerC, radius, radius - innerC, mScalaPaint);
                //总计12跟线，每个需要从中心点开始旋转30度
                canvas.rotate(30, radius, radius);
            }
            canvas.restore();
        }
    }

    private boolean isUpdate = false;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isUpdate = true;
        post(new Runnable() {
            @Override
            public void run() {
                if (isUpdate) {
                    invalidate();
                    postDelayed(this, 1000);
                } else {
                    removeCallbacks(this);
                }
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isUpdate = false;
    }
}
