package com.example.myviewdemo.jetpack.gallery.fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import androidx.paging.toLiveData
import com.example.myviewdemo.jetpack.gallery.PixabayDataSourceFactory

class GalleryViewModel(application: Application) : AndroidViewModel(application) {
   private val factory = PixabayDataSourceFactory(application)
    val pageLiveData = factory.toLiveData(20)
    val networkStatus = Transformations.switchMap(factory.pixabayDataSource) {it.networkStatus}

    fun resetQuery() {
        //invalidate()无效livedata  paging会重新刷新数据
        pageLiveData.value?.dataSource?.invalidate()
    }
    fun retry() {
        factory.pixabayDataSource.value?.retry?.invoke()
    }
}