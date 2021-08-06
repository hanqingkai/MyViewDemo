package com.example.myviewdemo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.withRotation
import androidx.core.graphics.withTranslation
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 *
 * @Description:  移动坐标系的方式来自定义view 便于用常规思维和方法计算
 * @Author:         韩庆凯
 * @CreateDate:     2020/10/26 13:49
 * @UpdateUser:
 * @UpdateDate:     2020/10/26 13:49
 * @UpdateRemark:
 * @Version:
 */
class MyMoveAxisesView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), LifecycleObserver {

    private var rotatingJob: Job? = null

    private var mWidth: Float = 0.0f
    private var mHeight: Float = 0.0f
    private var mRadius = 0f//半径
    private var mAngle = 10f;

    private val sinWaveSamplePath = Path()

    private val solidLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 5f
        color = ContextCompat.getColor(context, R.color.scaleDefaultColor)
    }
    private val filledCriclePaint = Paint().apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.scaleDefaultColor)
    }

    private val vectorLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 5f
        color = ContextCompat.getColor(context, R.color.colorAccent)
    }
    private val textPaint = Paint().apply {
        textSize = 30f
        typeface = Typeface.DEFAULT_BOLD
        color = ContextCompat.getColor(context, R.color.scaleDefaultColor)
    }
    private val dashedLinePaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        pathEffect = DashPathEffect(floatArrayOf(10f, 10f),  0f)
        color = ContextCompat.getColor(context, R.color.colorYellow)
        strokeWidth = 5f
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w.toFloat();
        mHeight = h.toFloat()
        mRadius = if (w < h / 2) w / 2.toFloat() else h / 4.toFloat()
        mRadius -= 20f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            drawAxises(this)
            drawLabel(this)
            drawDashedCircle(this)
            drawVector(this)
            drawProjections(this)
            drawSinWave(this)
        }

    }

    /**
     * 画坐标系
     */
    private fun drawAxises(canvas: Canvas) {
        /* withTranslation() 移动坐标系到指定位置
            将坐标系移动到  屏幕中心
         */
        canvas.withTranslation(mWidth / 2, mHeight / 2) {
            canvas.drawLine(-mWidth / 2, 0f, mWidth / 2, 0f, solidLinePaint)
            canvas.drawLine(0f, -mHeight / 2, 0f, mHeight / 2, solidLinePaint)
        }

        canvas.withTranslation(mWidth / 2, mHeight * 3 / 4) {
            canvas.drawLine(-mWidth / 2, 0f, mWidth / 2, 0f, solidLinePaint)
        }
    }

    /**
     * 画文字
     */
    private fun drawLabel(canvas: Canvas) {
        canvas.apply {
            drawRect(50f, 50f, 350f, 100f, solidLinePaint)
            drawText("指数函数与旋转矢量", 65f, 85f, textPaint)
        }
    }

    /**
     * 画虚线圆
     */
    private fun drawDashedCircle(canvas: Canvas) {
        //移动坐标中心到 圆心位置
        canvas.withTranslation(mWidth / 2, mHeight / 4 * 3) {
            drawCircle(0f, 0f, mRadius, dashedLinePaint)
        }
    }

    /**
     * 画矢量
     */
    private fun drawVector(canvas: Canvas) {
        //移动画布到坐标中心
        canvas.withTranslation(mWidth / 2, mHeight / 4 * 3) {
            //旋转画布方便画角度使用  矢量是根据角度旋转，不用考虑 线 的起点和终点 固定线转画布
            canvas.withRotation(-mAngle) {
                drawLine(0f, 0f, mRadius, 0f, vectorLinePaint)
            }
        }
    }

    /**
     * 让 mAngle匀速增加  产生旋转
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startRotation() {
        rotatingJob = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                delay(100)
                mAngle += 5f
                invalidate()
            }
        }
    }

    /**
     * 让 mAngle 停止变化 停止旋转
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pauseRotating() {
        rotatingJob?.cancel()
    }

    /**
     * 画移动的两个白色圆点和两条线
     */
    private fun drawProjections(canvas: Canvas) {
        //将画布移动到 中心  画x轴上的点
        canvas.withTranslation(mWidth / 2, mHeight / 2) {
            canvas.drawCircle(mRadius * cos(mAngle.toRadians()), 0f, 10f, filledCriclePaint)
        }
        //3/4处的圆点
        canvas.withTranslation(mWidth / 2, mHeight * 3 / 4) {
            canvas.drawCircle(mRadius * cos(mAngle.toRadians()), 0f, 10f, filledCriclePaint)
        }
        //将画布移动到两个点和半径顶点在一条竖直线上  为了查看方便没有整理
        //先移动原点到3/4处
        canvas.withTranslation(mWidth / 2, mHeight * 3 / 4) {
            //再移动到相交点上
            canvas.withTranslation(mRadius * cos(mAngle.toRadians()), -mRadius * sin(mAngle.toRadians())) {
                //与上边白色圆点的连线
                canvas.drawLine(0f, 0f, 0f, -(mHeight / 4 - mRadius * sin(mAngle.toRadians())), dashedLinePaint)
                //与下边白色圆点的连线
                canvas.drawLine(0f, 0f, 0f, mRadius * sin(mAngle.toRadians()), solidLinePaint)

            }
        }

    }

    /**
     * 画指数曲线  取样本数量 进行连接 产生曲线
     */
    private fun drawSinWave(canvas: Canvas) {
        //将画布移动到 屏幕中心
        canvas.withTranslation(mWidth / 2, mHeight / 2) {
            //样本数量
            val simpleCount = 100
            //样本间隔
            val dy = mHeight / 2 / simpleCount
            sinWaveSamplePath.reset()
            //移动到和白色圆相同起点
            sinWaveSamplePath.moveTo(mRadius * cos(mAngle.toRadians()), 0f)
            repeat(simpleCount) {
                val x = mRadius * cos(it * -0.15 + mAngle.toRadians())
                val y = -dy * it
                //画贝塞尔曲线
                sinWaveSamplePath.quadTo(x.toFloat(), y, x.toFloat(), y)
            }
            drawPath(sinWaveSamplePath, vectorLinePaint)
            //在path上加文字
            drawTextOnPath("hello world", sinWaveSamplePath, 1000f, -20f, textPaint)
        }
    }

    /**
     * 扩展方法：将弧度转化成角度
     */
    private fun Float.toRadians() = this / 180 * PI.toFloat()
}
