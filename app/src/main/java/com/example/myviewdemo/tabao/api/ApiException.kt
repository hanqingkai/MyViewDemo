package com.example.myviewdemo.tabao.api

import java.lang.RuntimeException

/**
 *
 * @Description:
 * @Author:         韩庆凯
 * @CreateDate:     2020/10/30 15:06
 * @UpdateUser:
 * @UpdateDate:     2020/10/30 15:06
 * @UpdateRemark:
 * @Version:
 */
data class ApiException(val code: Int, override val message: String?) : RuntimeException() {
}