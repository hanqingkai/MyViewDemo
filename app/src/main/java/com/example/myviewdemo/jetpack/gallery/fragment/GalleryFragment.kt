package com.example.myviewdemo.jetpack.gallery.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.myviewdemo.R
import com.example.myviewdemo.databinding.GalleryFragmentBinding
import com.example.myviewdemo.jetpack.gallery.NetworkStatus
import com.example.myviewdemo.jetpack.gallery.adapter.GalleryAdapter
import kotlinx.android.synthetic.main.gallery_fragment.*

class GalleryFragment : Fragment() {
    private lateinit var viewBinding: GalleryFragmentBinding
    private val galleryViewModel by activityViewModels<GalleryViewModel>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewBinding = GalleryFragmentBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        val galleryAdapter = GalleryAdapter(galleryViewModel)
        with(viewBinding){
            recyclerView.apply {
                adapter = galleryAdapter
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            }
            swipeLayoutGallery.setOnRefreshListener {
                galleryViewModel.resetQuery()
            }
            galleryViewModel.pageLiveData.observe(viewLifecycleOwner, Observer {
                galleryAdapter.submitList(it)
            })
            galleryViewModel.networkStatus.observe(viewLifecycleOwner, Observer {
                galleryAdapter.updateNetworkStatus(it)
                swipeLayoutGallery.isRefreshing = it == NetworkStatus.INITIAL_LOADING
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.gallery_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.swipeIndicator -> {
                swipeLayoutGallery.isRefreshing = true
                galleryViewModel.resetQuery()
            }
            R.id.menuRetry -> {
                galleryViewModel.retry()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}