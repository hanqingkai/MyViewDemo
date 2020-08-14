package com.example.myviewdemo.jetpack.gallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myviewdemo.R
import com.example.myviewdemo.jetpack.gallery.Pixabay
import kotlinx.android.synthetic.main.page_photo_layout.view.*

/**
 *
 * @Description:
 * @Author:         韩庆凯
 * @CreateDate:     2020/8/10 15:18
 * @UpdateUser:
 * @UpdateDate:     2020/8/10 15:18
 * @UpdateRemark:
 * @Version:
 */
class PagerPhotoListAdapter : ListAdapter<Pixabay.PhotoItem, MyViewHolder>(DIFFCALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.page_photo_layout, parent, false).apply {
            return MyViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(holder.itemView)
                .load(getItem(position).previewURL)
                .placeholder(R.drawable.ic_photo_gray_24)
                .into(holder.itemView.pagePhoto)
    }

    object DIFFCALLBACK : DiffUtil.ItemCallback<Pixabay.PhotoItem>() {
        override fun areItemsTheSame(oldItem: Pixabay.PhotoItem, newItem: Pixabay.PhotoItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Pixabay.PhotoItem, newItem: Pixabay.PhotoItem): Boolean {
            return oldItem == newItem
        }
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)