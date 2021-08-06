package com.example.myviewdemo.myapp

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import dagger.hilt.android.HiltAndroidApp

/**
 *
 * @Description:
 * @Author:         韩庆凯
 * @CreateDate:     2020/11/24 9:34
 * @UpdateUser:
 * @UpdateDate:     2020/11/24 9:34
 * @UpdateRemark:
 * @Version:
 */
@HiltAndroidApp
class APP : Application() {
    override fun attachBaseContext(context: Context?) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }

}