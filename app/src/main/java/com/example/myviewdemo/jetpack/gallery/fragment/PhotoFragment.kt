package com.example.myviewdemo.jetpack.gallery.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.myviewdemo.R
import com.example.myviewdemo.jetpack.gallery.Pixabay
import kotlinx.android.synthetic.main.fragment_photo.*


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class PhotoFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shimmerLayoutPhoto.apply {
            setShimmerColor(0x55FFFFFF)
            setShimmerAngle(10)
            startShimmerAnimation()
        }
        Glide.with(requireContext())
                .load(arguments?.getParcelable<Pixabay.PhotoItem>("PHOTO")?.previewURL)
                .placeholder(R.drawable.ic_photo_gray_24)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        return false.also {
                            shimmerLayoutPhoto?.stopShimmerAnimation()
                        }
                    }
                })
                .into(photoView)

    }

}