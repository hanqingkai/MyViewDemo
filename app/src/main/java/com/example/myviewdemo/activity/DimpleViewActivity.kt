package com.example.myviewdemo.activity

import android.animation.ObjectAnimator
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ImageViewTarget
import com.example.myviewdemo.R
import com.example.myviewdemo.databinding.ActivityDimpleViewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DimpleViewActivity : AppCompatActivity() {

    private lateinit var dimpleViewBinding: ActivityDimpleViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dimpleViewBinding = ActivityDimpleViewBinding.inflate(layoutInflater)
        setContentView(dimpleViewBinding.rootLayout)

        val objectAnimator = ObjectAnimator
                .ofFloat(dimpleViewBinding.musicAvatar, View.ROTATION, 0f, 360f)
        objectAnimator.interpolator = LinearInterpolator()
        objectAnimator.duration = 6000
        objectAnimator.repeatCount = -1


        lifecycleScope.launch(Dispatchers.Main) {
            loadImage()
            dimpleViewBinding.musicAvatar.setOnClickListener {
                objectAnimator.start()
            }
        }
    }

    private suspend fun loadImage() {
        withContext(Dispatchers.IO) {
            Glide.with(this@DimpleViewActivity)
                    .load(R.mipmap.ic_music1)
                    .circleCrop()
                    .into(object : ImageViewTarget<Drawable>(dimpleViewBinding.musicAvatar) {
                        override fun setResource(resource: Drawable?) {
//                            dimpleViewBinding.musicAvatar.setImageDrawable(resource)
                        }
                    })

        }
    }

}