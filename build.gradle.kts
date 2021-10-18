// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.qingkai.version") apply (false)
}
buildscript {
    val kotlinVersion by extra("1.4.32")
    val android by extra("com.android.tools.build:gradle:4.1.2")
    val hilt by extra("com.google.dagger:hilt-android-gradle-plugin:2.29.1-alpha")
    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath(android)
        classpath(kotlin(module = "gradle-plugin", version = kotlinVersion))
//        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath (hilt)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()

    }
}

subprojects {
    // 子项目统一应用插件
    project.apply(plugin = "com.qingkai.version")
    when (name) {
        //判断如果module是app则引入com.android.application插件
        "app" -> apply(plugin = "com.android.application")
        //如果不是则引入com.android.library插件，这里可以灵活编写
        else -> apply(plugin = "com.android.library")
    }
}
tasks.register<Delete>("clean") {
    delete(buildDir)
}
