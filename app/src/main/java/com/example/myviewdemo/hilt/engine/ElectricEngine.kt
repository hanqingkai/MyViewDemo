package com.example.myviewdemo.hilt.engine

import com.example.myviewdemo.hilt.interfaces.Engine
import javax.inject.Inject

/**
 *
 * @Description: 电动汽车发动机
 * @Author:         韩庆凯
 * @CreateDate:     2020/11/24 11:00
 * @UpdateUser:
 * @UpdateDate:     2020/11/24 11:00
 * @UpdateRemark:
 * @Version:
 */
class ElectricEngine @Inject constructor() : Engine {

    override fun start() {
        println("Electric engine is start.")
    }

    override fun shutdown() {
        println("Electric engine is shutdown.")
    }
}