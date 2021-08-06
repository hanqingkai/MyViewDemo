package com.example.myviewdemo.hilt.module

import com.example.myviewdemo.hilt.engine.ElectricEngine
import com.example.myviewdemo.hilt.engine.GasEngine
import com.example.myviewdemo.hilt.interfaces.Engine
import com.example.myviewdemo.hilt.qualifier.BindElectricEngine
import com.example.myviewdemo.hilt.qualifier.BindGasEngine
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 *
 * @Description:
 * @Author:         韩庆凯
 * @CreateDate:     2020/11/24 11:05
 * @UpdateUser:
 * @UpdateDate:     2020/11/24 11:05
 * @UpdateRemark:
 * @Version:
 */
@Module //@Module注解，表示这一个用于提供依赖注入实例的模块
@InstallIn(ActivityComponent::class)
abstract class EngineModule {

    //提供Engine接口所需要的实例
    @BindGasEngine
    @Binds
    abstract fun bindGasEngine(gasEngine: GasEngine): Engine

    @BindElectricEngine
    @Binds
    abstract fun bindElectricEngine(electricEngine: ElectricEngine): Engine
}