package com.example.myviewdemo.tabao.api

import com.example.myviewdemo.tabao.bean.OnSellData
import com.example.myviewdemo.tabao.bean.ResultDate
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *
 * @Description:
 * @Author:         韩庆凯
 * @CreateDate:     2020/10/30 14:32
 * @UpdateUser:
 * @UpdateDate:     2020/10/30 14:32
 * @UpdateRemark:
 * @Version:
 */
interface ApiService {
    companion object {
        const val BASE_URL = "https://api.sunofbeach.net/shop/"
    }

    @GET("onSell/{page}")
    suspend fun getOnSellList(@Path("page") page: Int): ResultDate<OnSellData>

}