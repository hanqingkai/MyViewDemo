package com.example.myviewdemo.hilt

import com.example.myviewdemo.hilt.interfaces.Engine
import com.example.myviewdemo.hilt.qualifier.BindElectricEngine
import com.example.myviewdemo.hilt.qualifier.BindGasEngine
import javax.inject.Inject

/**
 *
 * @Description: @Inject constructor()通知可以使用了
 * @Author:         韩庆凯
 * @CreateDate:     2020/11/24 9:38
 * @UpdateUser:
 * @UpdateDate:     2020/11/24 9:38
 * @UpdateRemark:
 * @Version:
 */
class Truck @Inject constructor(  val driver: Driver) {
    @BindGasEngine
    @Inject
    lateinit var gasEngine: Engine

    @BindElectricEngine
    @Inject
    lateinit var electricEngine: Engine

    fun deliver() {
        gasEngine.start()
        electricEngine.start()
        println("Truck is delivering cargo. driven by $driver")
        gasEngine.shutdown()
        electricEngine.shutdown()
    }
}