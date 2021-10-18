package com.qingkai.version

import com.android.build.api.dsl.BuildFeatures
import com.android.build.gradle.*
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.qingkai.version.Testing.androidTestImplementation
import com.qingkai.version.Testing.testImplementation
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.plugin.KotlinAndroidPluginWrapper
import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 *author  : huitao
 *e-mail  : pig.huitao@gmail.com
 *date    : 2021/5/12 20:52
 *desc    :
 *version :
 */
class VersionConfigPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.config(project)
    }


    private fun PluginContainer.config(project: Project) {
        whenObjectAdded {
            when (this) {
                is AppPlugin -> {
                    //公共插件
                    project.configCommonPlugin()
                    //公共android配置
                    project.extensions.getByType<AppExtension>().applyAppCommons(project)
                    //公共依赖
                    project.configAppDependencies()
                }

                is LibraryPlugin -> {
                    //公共插件
                    project.configCommonPlugin()
                    //公共android配置
                    project.extensions.getByType<LibraryExtension>().applyLibraryCommons(project)
                    //公共依赖
                    project.configLibraryDependencies()
                }

                is KotlinAndroidPluginWrapper -> {
                    //根据 project build.gradle.kts 中的配置动态设置 kotlinVersion
                    project.getKotlinPluginVersion()?.also { kotlinVersion ->
                        GradlePlugins.kotlinVersion = kotlinVersion
                    }
                }
            }
        }
    }


    ///library module 公共依赖
    private fun Project.configLibraryDependencies() {
        dependencies.apply {
            add(api, fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
            add(implementation, GradlePlugins.kotlinStdlib)
            //如果module 的 name 不是common则引入common模块，不加次判断，活报错，提示我们common
            //module自己引入自己，相当于递归了
//            if (name != "common") {
//                add(api, project(":common"))
//            }
            //引入autoService服务，主要为后续的组件化开发做准备，Kotlin中要使用kapt方式
            add(kapt, ThirdParty.autoService)
            add(compileOnly, ThirdParty.autoService)
            configTestDependencies()
        }
    }

    //app module
    private fun Project.configAppDependencies() {
        dependencies.apply {
            add(implementation, fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
            //添加library工程，不用在build中编写
            add(implementation, GradlePlugins.kotlinStdlib)
//            add(implementation, project(":home"))
//            add(implementation, project(":project"))
//            add(implementation, project(":square"))
//            add(implementation, project(":officialaccount"))
//            add(implementation, (project(":login")))
//            add(implementation, project(":common"))
//            add(implementation, project(":mine"))
            configTestDependencies()
        }
    }


    /// test 依赖配置
    private fun DependencyHandler.configTestDependencies() {
        testImplementation(Testing.testLibraries)
        androidTestImplementation(Testing.androidTestLibraries)
    }


    ///kotlin插件
    private fun Project.configCommonPlugin() {
//         id 'com.android.application'
//    id 'kotlin-android'
//    id 'kotlin-android-extensions'
//    id 'kotlin-kapt'
//    id  'dagger.hilt.android.plugin'

        //添加android  或者 kotlin插件
        plugins.apply("kotlin-android")
        plugins.apply("dagger.hilt.android.plugin")
        plugins.apply("kotlin-kapt")
        //已启用 使用viewBinding
        plugins.apply("kotlin-android-extensions")
    }


    ///app module 配置项 此处固定了applicationId
    private fun AppExtension.applyAppCommons(project: Project) {
        defaultConfig {
            applicationId = BuildConfig.applicationId
        }
        applyBaseCommons(project)
    }


    /// app module 配置项
    private fun LibraryExtension.applyLibraryCommons(project: Project) {
        applyBaseCommons(project)
    }


    ///配置android闭包下的公共环境
    private fun BaseExtension.applyBaseCommons(project: Project) {
        compileSdkVersion(BuildConfig.compileSdkVersion)
        buildToolsVersion(BuildConfig.buildToolsVersion)
        defaultConfig {
            minSdkVersion(BuildConfig.minSdkVersion)
            targetSdkVersion(BuildConfig.targetSdkVersion)
            multiDexEnabled = true

            versionCode = BuildConfig.versionCode
            versionName = BuildConfig.versionName
            testInstrumentationRunner = BuildConfig.runner

            manifestPlaceholders(
                mapOf(
                    jPushPackageName to BuildConfig.applicationId,
                    jPushAppKey to "7e88bec095030e8ced40654c",
                    jPushChannel to "developer-default"
                )
            )
            buildFeatures.viewBinding=true
            dataBinding.isEnabled()
//            BuildFeatures {
//                buildFeatures.viewBinding = true
//                dataBinding.isEnabled = true
//            }
        }
        signingConfigs {
            /* register(BuildConfig.release) {
                 keyAlias = "bear"
                 keyPassword = "BEARSELLER"
                 storePassword = "bearseller"
                 storeFile = File("bearSeller.jks")
             }*/

            /*  register(BuildConfig.debug) {
                  keyAlias("bear")
                  keyPassword("BEARSELLER")
                  storePassword("bearseller")
                  storeFile(File("../bearSeller.jks"))
              }*/
        }

        buildTypes {
            /*getByName(BuildConfig.release) {
                isMinifyEnabled = false
                signingConfig = signingConfigs.getByName(BuildConfig.release)
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }

            getByName(BuildConfig.debug) {
                signingConfig = signingConfigs.getByName(BuildConfig.debug)
            }*/
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        buildFeatures.viewBinding = true
        dataBinding {
            isEnabled = true
        }
        project.tasks.withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

}


