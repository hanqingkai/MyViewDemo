package com.example.myviewdemo.hilt.engine

import com.example.myviewdemo.hilt.interfaces.Engine
import javax.inject.Inject

/**
 *
 * @Description: 燃油汽车发动机
 * @Author:         韩庆凯
 * @CreateDate:     2020/11/24 10:58
 * @UpdateUser:
 * @UpdateDate:     2020/11/24 10:58
 * @UpdateRemark:
 * @Version:
 */
class GasEngine @Inject constructor() : Engine {

    override fun start() {
        println("Gas engine is start.")
    }

    override fun shutdown() {
        println("Gas engine is shutdown.")
    }
}