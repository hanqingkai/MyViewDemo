package com.example.myviewdemo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 *
 * @Description:
 * @Author:         韩庆凯
 * @CreateDate:     2020/9/27 15:57
 * @UpdateUser:
 * @UpdateDate:     2020/9/27 15:57
 * @UpdateRemark:
 * @Version:
 */
class JiKeLikeView : View {

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }

    val paint: Paint = Paint()
    val bitmap: Bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.jike_like_unselected)


    var isLike = false

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.isAntiAlias = true
        canvas!!.save()
        if (!isLike) {
            canvas.drawBitmap(bitmap, 200f, 200f, paint)
        } else {
            canvas.drawBitmap(bitmap, 200f, 200f, paint)
        }
        canvas.restore()
    }

}