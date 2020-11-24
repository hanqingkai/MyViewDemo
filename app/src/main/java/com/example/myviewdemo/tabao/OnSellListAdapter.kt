package com.example.myviewdemo.tabao

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myviewdemo.R
import com.example.myviewdemo.tabao.bean.OnSellData
import kotlinx.android.synthetic.main.item_onselllist.view.*

class OnSellListAdapter : RecyclerView.Adapter<OnSellListAdapter.OnSellViewHolder>() {

    private val mContentList = arrayListOf<OnSellData.TbkDgOptimusMaterialResponse.ResultList.MapData>()

    class OnSellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnSellViewHolder {
        return OnSellViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_onselllist, parent, false))
    }

    override fun onBindViewHolder(holder: OnSellViewHolder, position: Int) {
        mContentList[position].run {
            holder.itemView.apply {
                tv_title.text = title
                tv_offprise.text = String.format("￥%.2f", zk_final_price.toFloat() - coupon_amount)
                Glide.with(context).load("https:$pict_url").into(iv_cover)
            }
        }
    }

    override fun getItemCount() = mContentList.size


    fun setData(contentList: List<OnSellData.TbkDgOptimusMaterialResponse.ResultList.MapData>) {
        mContentList.clear()
        mContentList.addAll(contentList)
        notifyDataSetChanged()
    }

}
