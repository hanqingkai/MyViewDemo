buildscript {
    repositories {
        jcenter()
        google()
    }
    dependencies {
        // 由于使用的 Kotlin 须要须要添加 Kotlin 插件
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
    }
}

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    // 须要添加 jcenter 不然会提示找不到 gradlePlugin
    jcenter()
    google()
}


dependencies {
    compileOnly(gradleApi())
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
    compileOnly("com.android.tools.build:gradle:4.1.1")
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

gradlePlugin {
    plugins {
        create("version") {
            // 在 app 模块须要经过 id 引用这个插件
            id = "com.qingkai.version"
            // 实现这个插件的类的路径
            implementationClass = "com.qingkai.version.VersionConfigPlugin"
        }
    }
}