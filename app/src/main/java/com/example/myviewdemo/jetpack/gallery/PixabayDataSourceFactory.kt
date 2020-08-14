package com.example.myviewdemo.jetpack.gallery

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

/**
 *
 * @Description:
 * @Author:         韩庆凯
 * @CreateDate:     2020/8/11 13:45
 * @UpdateUser:
 * @UpdateDate:     2020/8/11 13:45
 * @UpdateRemark:
 * @Version:
 */
open class PixabayDataSourceFactory(private val context: Context) : DataSource.Factory<Int, Pixabay.PhotoItem>() {
    private val _pixabayDataSource = MutableLiveData<PixabayDataSource>()
    val pixabayDataSource: LiveData<PixabayDataSource> = _pixabayDataSource

    override fun create(): DataSource<Int, Pixabay.PhotoItem> {
        return PixabayDataSource(context).also {
            _pixabayDataSource.postValue(it)
        }
    }
}