package com.example.myviewdemo.hilt.qualifier

import javax.inject.Qualifier

/**
 *
 * @Description:
 * @Author:         韩庆凯
 * @CreateDate:     2020/11/24 14:40
 * @UpdateUser:
 * @UpdateDate:     2020/11/24 14:40
 * @UpdateRemark:
 * @Version:
 */
@Qualifier //给相同类型的类或接口注入不同的实例
@Retention(AnnotationRetention.BINARY)//用于声明注解的作用范围，选择AnnotationRetention.BINARY表示该注解在编译之后会得到保留，但是无法通过反射去访问这个注解
annotation class BindGasEngine {
}