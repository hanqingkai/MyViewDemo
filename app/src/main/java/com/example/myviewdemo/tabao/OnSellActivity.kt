package com.example.myviewdemo.tabao

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.example.myviewdemo.R
import com.example.myviewdemo.dp2px
import com.example.myviewdemo.tabao.api.LoadState
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import kotlinx.android.synthetic.main.activity_recommend.*
import kotlinx.android.synthetic.main.loading_error.*

class OnSellActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(OnSellViewModel::class.java) }
    private val mAdapter by lazy { OnSellListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend)
        initView()
        initObserver()
    }

    private fun initObserver() {
        viewModel.apply {
            contentList.observe(this@OnSellActivity, Observer {
                mAdapter.setData(it)
            })
            loadState.observe(this@OnSellActivity, Observer {
                hideAll()
                when (it!!) {
                    LoadState.LOADING -> loadingStart.visibility = View.VISIBLE
                    LoadState.EMPTY -> loadingEmpty.visibility = View.VISIBLE
                    LoadState.ERROR -> loadingError.visibility = View.VISIBLE
                    else -> {
                        refreshLayout.visibility = View.VISIBLE
                        refreshLayout.finishLoadmore()
                    }
                }
                when (it) {
                    LoadState.LOADMORE_ERROR -> {
                        ToastUtils.showShort("网络错误，请稍候重试！")
                    }
                    LoadState.LOADMORE_EMPTY -> {
                        ToastUtils.showShort("没有更多了...")
                    }

                }

            })
        }.loadContent()
    }

    private fun initView() {
        refreshLayout.run {
            setEnableLoadmore(true)
            setEnableRefresh(false)
            setEnableOverScroll(true)
            setOnRefreshListener(object : RefreshListenerAdapter() {
                override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                    super.onLoadMore(refreshLayout)
                    viewModel.loadMore()
                }

            })
        }
        contentListRV.run {
            layoutManager = LinearLayoutManager(this@OnSellActivity)
            adapter = mAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    outRect.apply {
                        val padding = dp2px(4, resources)
                        top = padding
                        bottom = padding
                        left = padding
                        right = padding
                    }
                }
            })
        }
        reload.setOnClickListener { viewModel.loadContent() }
    }

    private fun hideAll() {
        refreshLayout.visibility = View.GONE
        loadingEmpty.visibility = View.GONE
        loadingError.visibility = View.GONE
        loadingStart.visibility = View.GONE
    }
}

