package com.example.myviewdemo

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import java.lang.Math.acos
import java.lang.Math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 *
 * @Description: 网易云音乐宇宙尘埃特效练习
 * @Author:         韩庆凯
 * @CreateDate:     2020/10/9 11:33
 * @UpdateUser:
 * @UpdateDate:     2020/10/9 11:33
 * @UpdateRemark:
 * @Version:
 */
class DimpleView : View {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val paint = Paint()
    private var centerX: Float = 0f;
    private var centerY: Float = 0f;
    private var particleList = mutableListOf<Particle>()

    private var animator = ValueAnimator.ofFloat(0f, 1f)

    var path = Path()
    private val pathMeasure = PathMeasure()//路径，用于测量扩散圆某一处的X,Y值
    private var pos = FloatArray(2) //扩散圆上某一点的x,y
    private val tan = FloatArray(2)//扩散圆上某一点切线

    init {
        animator.duration = 2000
        animator.repeatCount = -1
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener {
            updateParticle(it.animatedValue as Float)
            invalidate()//重绘
        }

    }

    private fun updateParticle(value: Float) {
            particleList.forEach {particle->
                if(particle.offset >particle.maxOffset){
                    particle.offset=0f
                    particle.speed= (Random.nextInt(10)+5).toFloat()
                }
                particle.alpha= ((1f - particle.offset / particle.maxOffset)  * 225f).toInt()
                particle.x = (centerX+ cos(particle.angle) * (280f + particle.offset)).toFloat()
                if (particle.y > centerY) {
                    particle.y = (sin(particle.angle) * (280f + particle.offset) + centerY).toFloat()
                } else {
                    particle.y = (centerY - sin(particle.angle) * (280f + particle.offset)).toFloat()
                }
                particle.offset += particle.speed.toInt()
            }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = (w / 2).toFloat()
        centerY = (h / 2).toFloat()
        path.addCircle(centerX, centerY, 280f, Path.Direction.CCW)
        pathMeasure.setPath(path, false) //添加path
        var nextX = 0f
        var nextY = 0f
        var speed = 0f

        var angle=0.0
        var offSet=0
        var maxOffset=0

        for (i in 0..2000) {
            //初始化Y值，这里是以起始点作为最低值，最大距离作为最大值
            //按比例测量路径上每一点的值
            pathMeasure.getPosTan(i / 2000f * pathMeasure.length, pos, tan)
            nextX = pos[0] + Random.nextInt(6) - 3f //X值随机偏移
            nextY = pos[1] + Random.nextInt(6) - 3f//Y值随机偏移
            speed = Random.nextInt(2) + 2f //速度从5-15不等

            //反余弦函数可以得到角度值，是弧度
            angle = kotlin.math.acos(((pos[0] - centerX) / 280f).toDouble())
            offSet = Random.nextInt(200)
            maxOffset = Random.nextInt(200)
            particleList.add(
                    Particle(nextX , nextY, 2f, speed , 100, maxOffset.toFloat(),offSet.toFloat(), angle))
        }
        animator.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //画他一个圆粒子
        paint.isAntiAlias = true
        paint.color = Color.WHITE
        particleList.forEachIndexed { _, particle ->
            if (particle.offset > 5f) {
                paint.alpha = ((1f - particle.offset / particle.maxOffset) * 0.8 * 225f).toInt()
                canvas.drawCircle(particle.x, particle.y, particle.radius, paint)
            } else {
                paint.alpha = 225
            }
            canvas.drawCircle(particle.x, particle.y, particle.radius, paint)
        }
    }

}

/**
 * 定义粒子
 */
class Particle(
        var x: Float,//X坐标
        var y: Float,//Y坐标
        var radius: Float,//半径
        var speed: Float,//速度
        var alpha: Int,//透明度

        var maxOffset: Float = 300f,//最大移动距离,一旦移动的距离超过了这个值，我们就让它回到起点

        var offset: Float,//当前移动距离
        var angle: Double//粒子角度
)