package com.example.myviewdemo.tabao

import com.example.myviewdemo.tabao.api.RetrofitClient

/**
 *
 * @Description:
 * @Author:         韩庆凯
 * @CreateDate:     2020/10/30 14:03
 * @UpdateUser:
 * @UpdateDate:     2020/10/30 14:03
 * @UpdateRemark:
 * @Version:
 */
class OnSellRepository {

    suspend fun getOnSellList(page: Int) =
            RetrofitClient.apiService.getOnSellList(page).apiData()

}