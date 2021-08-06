package com.example.myviewdemo.tabao.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
 * @Description:创建Retrofit 以提供API Service
 * @Author:         韩庆凯
 * @CreateDate:     2020/10/30 14:29
 * @UpdateUser:
 * @UpdateDate:     2020/10/30 14:29
 * @UpdateRemark:
 * @Version:
 */
object RetrofitClient {
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .callTimeout(30, TimeUnit.SECONDS)
            .build()

    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    val apiService = retrofit.create(ApiService::class.java)

}