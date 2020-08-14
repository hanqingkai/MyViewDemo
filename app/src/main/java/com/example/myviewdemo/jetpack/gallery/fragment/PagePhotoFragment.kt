package com.example.myviewdemo.jetpack.gallery.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.myviewdemo.R
import com.example.myviewdemo.jetpack.gallery.adapter.PagerPhotoListAdapter
import kotlinx.android.synthetic.main.fragment_page_photo.*


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class PagePhotoFragment : Fragment() {
    //将viewmodel提升到activity层面 使Fragment可以共享同一个viewmodel
    val galleryViewModel by activityViewModels<GalleryViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_page_photo, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = PagerPhotoListAdapter()
        viewpager2.adapter = adapter
        galleryViewModel.pageLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            viewpager2.setCurrentItem(arguments?.getInt("PHOTO_POSITION") ?: 0, false)
        })
        viewpager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                photoTag.text = "${position + 1}/${galleryViewModel.pageLiveData.value?.size}"
            }
        })

    }
}