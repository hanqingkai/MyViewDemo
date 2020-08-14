package com.example.myviewdemo.jetpack.gallery

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.android.volley.Request.Method.GET
import com.android.volley.Response
import com.android.volley.VolleyLog.TAG
import com.android.volley.toolbox.StringRequest
import com.blankj.utilcode.util.GsonUtils

/**
 *
 * @Description:
 * @Author:         韩庆凯
 * @CreateDate:     2020/8/11 11:31
 * @UpdateUser:
 * @UpdateDate:     2020/8/11 11:31
 * @UpdateRemark:
 * @Version:
 */
enum class NetworkStatus {
    INITIAL_LOADING,
    LOADING,
    LOADED,
    FAILED,
    COMPLETED
}

class PixabayDataSource(private val context: Context) : PageKeyedDataSource<Int, Pixabay.PhotoItem>() {
    //用于保存失败的状态，恢复或重试时使用
    var retry: (() -> Any)? = null
    private val _networkStatus = MutableLiveData<NetworkStatus>()
    val networkStatus: LiveData<NetworkStatus> = _networkStatus

    private val queryKey = arrayOf("cat", "dog", "car", "beauty", "phone", "computer", "flower", "animal").random()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Pixabay.PhotoItem>) {
        retry = null   //清空存储，避免误操作
        _networkStatus.postValue(NetworkStatus.INITIAL_LOADING)
        val url = "https://pixabay.com/api/?key=17282436-ef21c5c065a31c22ba7358e9e&q=${queryKey.random()}&image_type=photo&per_page=20&page=1"
        StringRequest(GET, url, Response.Listener {

            val dataList = GsonUtils.fromJson(it, Pixabay::class.java).hits.toList()
            callback.onResult(dataList, null, 2)
            _networkStatus.postValue(NetworkStatus.LOADED)
        }, Response.ErrorListener {
            Log.d(TAG, "loadInitial: $it")
            _networkStatus.postValue(NetworkStatus.FAILED)
            //保存状态
            retry = { loadInitial(params, callback) }
        }).also {
            VollySingleton.getInstance(context).requestQueue.add(it)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Pixabay.PhotoItem>) {
        retry = null
        _networkStatus.postValue(NetworkStatus.LOADING)
        val url = "https://pixabay.com/api/?key=17282436-ef21c5c065a31c22ba7358e9e&q=${queryKey.random()}&image_type=photo&per_page=20&page=${params.key}"
        StringRequest(GET, url, Response.Listener {
            val dataList = GsonUtils.fromJson(it, Pixabay::class.java).hits.toList()
            callback.onResult(dataList, params.key + 1)
            _networkStatus.postValue(NetworkStatus.LOADED)
        }, Response.ErrorListener {
            if (it.toString() == "com.android.volley.ClientError") {
                //没有更多了
                _networkStatus.postValue(NetworkStatus.COMPLETED)
            } else {
                retry = { loadAfter(params, callback) }
                Log.d(TAG, "loadInitial: $it")
                _networkStatus.postValue(NetworkStatus.FAILED)
            }
        }).also {
            VollySingleton.getInstance(context).requestQueue.add(it)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Pixabay.PhotoItem>) {

    }
}