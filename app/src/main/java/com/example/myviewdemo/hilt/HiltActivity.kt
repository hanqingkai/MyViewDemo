package com.example.myviewdemo.hilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myviewdemo.R
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject

@AndroidEntryPoint
class HiltActivity : AppCompatActivity() {
    /**
     * 延迟初始化
     * Hilt注入的字段是不可以声明成private
     */
    @Inject
    lateinit var truck: Truck

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Inject
    lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hilt)
        truck.deliver()
    }
}