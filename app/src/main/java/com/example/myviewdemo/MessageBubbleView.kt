package com.example.myviewdemo

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

/**
 *
 * @Description: 仿QQ消息拖拽气泡
 * @Author:         韩庆凯
 * @CreateDate:     2020/9/4 13:42
 * @UpdateUser:
 * @UpdateDate:     2020/9/4 13:42
 * @UpdateRemark:
 * @Version:
 */
fun dp2px(dip: Int, resources: Resources): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip.toFloat(), resources.displayMetrics).toInt()
}

class MessageBubbleView : View {
    //固定位置 大小变化的圆
    private var mFixationPoint: PointF = PointF()

    //跟随手指拖拽的圆
    private var mDragPoint: PointF = PointF()

    private var mDragRadius: Int = dp2px(10, resources)
    private var mFixationRadiusMax: Int = dp2px(7, resources)
    private var mFixationRadiusMin: Int = dp2px(3, resources)
    private var mFixationRadius: Int = dp2px(7, resources)
    private val mPaint: Paint = Paint()

    init {
        mPaint.apply {
            color = Color.RED
            isAntiAlias = true
            isDither = true
        }
    }

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }


    override fun onDraw(canvas: Canvas?) {
        //跟随手指的圆
        canvas?.drawCircle(mDragPoint.x, mDragPoint.y, mDragRadius.toFloat(), mPaint)
        //画固定位置的圆，根据距离变大而变小，直到固定大小消失（不画了）
        //两个圆之间的距离
        val distance: Double = getDistance(mDragPoint, mFixationPoint)
        mFixationRadius = (mFixationRadiusMax - distance / 14).toInt()
        if (mFixationRadius > mFixationRadiusMin) {
            canvas?.drawCircle(mFixationPoint.x, mFixationPoint.y, mFixationRadius.toFloat(), mPaint)
        }
    }

    /**
     * 获取两个圆之间的距离
     */
    private fun getDistance(point1: PointF, point2: PointF): Double = Math.sqrt(Math.pow(point1.x.toDouble() - point2.x.toDouble(), 2.0) - Math.pow(point1.y.toDouble() - point2.y, 2.0))

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                //指定当前位置
                val downX = event.x
                val downY = event.y
                initPoint(downX, downY)
            }
            MotionEvent.ACTION_MOVE -> {
                val moveX = event.x
                val moveY = event.y
                updateDragPoint(moveX, moveY)
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        invalidate()
        return true
    }

    /**
     * 更新拖拽点的位置
     */
    private fun updateDragPoint(moveX: Float, moveY: Float) {
        mDragPoint.x = moveX
        mDragPoint.y = moveY
    }

    /**
     * 初始化位置
     */
    private fun initPoint(downX: Float, downY: Float) {
        mFixationPoint = PointF(downX, downY)
        mDragPoint = PointF(downX, downY)
    }


}