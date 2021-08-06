package com.example.myviewdemo.tabao.bean

import com.example.myviewdemo.tabao.api.ApiException

/**
 *
 * @Description:
 * @Author:         韩庆凯
 * @CreateDate:     2020/10/30 15:01
 * @UpdateUser:
 * @UpdateDate:     2020/10/30 15:01
 * @UpdateRemark:
 * @Version:
 */
data class ResultDate<T>(val success: Boolean, val code: Int, val message: String, val data: T) {

    companion object {
        const val CODE_SUCCESS = 10000
    }

    fun apiData(): T {
        if (code == CODE_SUCCESS) {
            return data
        } else {
            throw ApiException(code, message)
        }
    }
}