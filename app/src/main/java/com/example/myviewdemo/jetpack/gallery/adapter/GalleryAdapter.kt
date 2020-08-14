package com.example.myviewdemo.jetpack.gallery.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.myviewdemo.R
import com.example.myviewdemo.jetpack.gallery.NetworkStatus
import com.example.myviewdemo.jetpack.gallery.Pixabay
import com.example.myviewdemo.jetpack.gallery.fragment.GalleryViewModel
import kotlinx.android.synthetic.main.gallery_cell.view.*
import kotlinx.android.synthetic.main.gallery_footer.view.*

/**
 *
 * @Description:
 * @Author:         韩庆凯
 * @CreateDate:     2020/8/7 15:02
 * @UpdateUser:
 * @UpdateDate:     2020/8/7 15:02
 * @UpdateRemark:
 * @Version:
 */
class GalleryAdapter(private val galleryViewModel: GalleryViewModel) : PagedListAdapter<Pixabay.PhotoItem, RecyclerView.ViewHolder>(DIFFCALLBACK) {
    private var networkStatus: NetworkStatus? = null
    private var hasFooter = false

    //自动重新加载
    init {
        galleryViewModel.retry()
    }

    fun updateNetworkStatus(networkStatus: NetworkStatus?) {
        this.networkStatus = networkStatus
        if (networkStatus === NetworkStatus.INITIAL_LOADING) hideFooter() else showFooter()
    }

    private fun showFooter() {
        if (hasFooter) {
            notifyItemChanged(itemCount - 1)
        } else {
            hasFooter = true
            notifyItemInserted(itemCount - 1)
        }
    }

    private fun hideFooter() {
        if (hasFooter) {
            notifyItemRemoved(itemCount - 1)
        }
        hasFooter = false
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasFooter && position == itemCount - 1) R.layout.gallery_footer else R.layout.gallery_cell
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.gallery_cell -> GalleryViewHolder.newInstance(parent).also { holder ->
                holder.itemView.setOnClickListener {
                    holder.itemView.findNavController().navigate(R.id.pagePhotoFragment,
                            bundleOf(Pair("PHOTO_POSITION", holder.adapterPosition)))
                }
            }
            else -> FooterViewHolder.newInstance(parent).also {
                it.itemView.setOnClickListener {
                    galleryViewModel.retry()
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            R.layout.gallery_footer -> (holder as FooterViewHolder).bindWithNetworkStatus(networkStatus)
            else -> {
                val photoItem = getItem(position) ?: return
                (holder as GalleryViewHolder).bindWithPhotoItem(photoItem)
            }
        }
    }

    object DIFFCALLBACK : DiffUtil.ItemCallback<Pixabay.PhotoItem>() {
        override fun areItemsTheSame(oldItem: Pixabay.PhotoItem, newItem: Pixabay.PhotoItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Pixabay.PhotoItem, newItem: Pixabay.PhotoItem): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        companion object {
            fun newInstance(parent: ViewGroup): GalleryViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_cell, parent, false)
                return GalleryViewHolder(view)
            }
        }

        fun bindWithPhotoItem(photoItem: Pixabay.PhotoItem) {
            itemView.shimmerGalleryLayout.apply {
                setShimmerColor(0x55FFFFFF)
                setShimmerAngle(10)
                startShimmerAnimation()
            }
            Glide.with(itemView)
                    .load(photoItem.previewURL)
                    .placeholder(R.drawable.ic_photo_gray_24)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            return false.also { itemView.shimmerGalleryLayout?.stopShimmerAnimation() }
                        }
                    })
                    .into(itemView.imageView)
        }
    }

    class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        companion object {
            fun newInstance(parent: ViewGroup): FooterViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_footer, parent, false)
                //让footer全部展示，避免只展示半边
                (view.layoutParams as StaggeredGridLayoutManager.LayoutParams).isFullSpan = true
                return FooterViewHolder(view)
            }
        }

        fun bindWithNetworkStatus(networkStatus: NetworkStatus?) {
            with(itemView) {
                when (networkStatus) {
                    NetworkStatus.FAILED -> {
                        textView.text = "点击重试"
                        isClickable = true
                        progressBar.visibility = View.GONE
                    }
                    NetworkStatus.COMPLETED -> {
                        textView.text = "加载完毕"
                        isClickable = false
                        progressBar.visibility = View.GONE
                    }
                    else -> {
                        textView.text = "正在加载"
                        isClickable = false
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}


