package com.example.myviewdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 *
 * @Description:
 * @Author:         韩庆凯
 * @CreateDate:     2020/9/8 16:55
 * @UpdateUser:
 * @UpdateDate:     2020/9/8 16:55
 * @UpdateRemark:
 * @Version:
 */
class PathView : View {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val paint: Paint = Paint()
    val path: Path = Path()

    init {
        paint.apply {

        }
        path.apply {
            addArc(200f, 200f, 400f, 400f, -225f, 225f);
            arcTo(400f, 200f, 600f, 400f, -180f, 225f, false);
            lineTo(400f, 542f);
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(path, paint)
    }
}