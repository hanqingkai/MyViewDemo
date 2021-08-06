package com.example.myviewdemo.hilt.interfaces

import dagger.Module

/**
 *
 * @Description:  发动机的功能接口
 * @Author:         韩庆凯
 * @CreateDate:     2020/11/24 10:57
 * @UpdateUser:
 * @UpdateDate:     2020/11/24 10:57
 * @UpdateRemark:
 * @Version:
 */
interface Engine {
    fun start()
    fun shutdown()
}