package com.example.myviewdemo.tabao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myviewdemo.tabao.api.LoadState
import com.example.myviewdemo.tabao.bean.OnSellData
import kotlinx.coroutines.launch

/**
 *
 * @Description:
 * @Author:         韩庆凯
 * @CreateDate:     2020/10/30 13:44
 * @UpdateUser:
 * @UpdateDate:     2020/10/30 13:44
 * @UpdateRemark:
 * @Version:
 */
class OnSellViewModel : ViewModel() {

    private val _contentList = MutableLiveData<MutableList<OnSellData.TbkDgOptimusMaterialResponse.ResultList.MapData>>()
    val contentList: LiveData<MutableList<OnSellData.TbkDgOptimusMaterialResponse.ResultList.MapData>> = _contentList

    var loadState = MutableLiveData<LoadState>()

    private val onSellRepository by lazy { OnSellRepository() }

    companion object {
        const val DEFAULT_PAGE = 1
    }

    private var mCurrentPage = DEFAULT_PAGE
    private var isLoadMore = false
    fun loadContent() {
        isLoadMore = false
        loadState.value = LoadState.LOADING
        this.listContentByPage(mCurrentPage)
    }

    fun loadMore() {
        mCurrentPage++
        isLoadMore = true
        loadState.value = LoadState.LOADMORE_LOADING
        this.listContentByPage(mCurrentPage)
    }

    private fun listContentByPage(page: Int) {
        viewModelScope.launch {
            try {
                val onSellList = onSellRepository.getOnSellList(page)
                _contentList.value = onSellList.tbk_dg_optimus_material_response.result_list.map_data
                val oldValue = contentList.value
                        ?: mutableListOf()
                oldValue.addAll(onSellList.tbk_dg_optimus_material_response.result_list.map_data)
                _contentList.value = oldValue
                if (onSellList.tbk_dg_optimus_material_response.result_list.map_data.isEmpty()) {
                    loadState.value = if (isLoadMore) LoadState.LOADMORE_EMPTY else LoadState.EMPTY
                } else {
                    loadState.value = LoadState.SUCCESS
                }
                println("result:$_contentList")
            } catch (e: Exception) {
                mCurrentPage--
                if (e is NullPointerException)
                    loadState.value = LoadState.LOADMORE_EMPTY
                else
                    loadState.value = if (isLoadMore) LoadState.LOADMORE_ERROR else LoadState.ERROR
            }
        }
    }


}