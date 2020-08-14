package com.example.myviewdemo.jetpack.gallery

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

/**
 *
 * @Description:
 * @Author:         韩庆凯
 * @CreateDate:     2020/8/7 11:38
 * @UpdateUser:
 * @UpdateDate:     2020/8/7 11:38
 * @UpdateRemark:
 * @Version:
 */
class VollySingleton private constructor(context: Context) {

    companion object {
        private var INSTANCE: VollySingleton? = null
        fun getInstance(context: Context) =
                INSTANCE ?: synchronized(this) {
                    VollySingleton(context).also {
                        INSTANCE = it
                    }
                }
    }

    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }
}