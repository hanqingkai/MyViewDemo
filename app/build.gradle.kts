import com.qingkai.version.*

//plugins {
//    id 'com.android.application'
//    id 'kotlin-android'
//    id 'kotlin-android-extensions'
//    id 'kotlin-kapt'
//    id  'dagger.hilt.android.plugin'
//}

//android {
//    compileSdkVersion 29
//    buildToolsVersion "29.0.3"
//
//    defaultConfig {
//        applicationId "com.example.myviewdemo"
//        minSdkVersion 19
//        targetSdkVersion 29
//        versionCode 1
//        versionName "1.0"
//
//        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
//        buildFeatures {
//            dataBinding = true
//            // for view binding :
//             viewBinding = true
//        }
//        // 这里添加
//        multiDexEnabled true
//    }
//
//    buildTypes {
//        release {
//            minifyEnabled false
//            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
//        }
//    }
//    compileOptions {
//        sourceCompatibility = 1.8
//        targetCompatibility = 1.8
//    }
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
//
//}

//dependencies {
////    implementation fileTree(org.gradle.internal.impldep.bsh.commands.dir: 'libs', include: ['*.jar'])
//
//    implementation 'androidx.appcompat:appcompat:1.2.0'
//    implementation 'androidx.core:core-ktx:1.3.2'
//    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
//    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
//    implementation 'com.github.bumptech.glide:glide:4.11.0'
//    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0'
//    implementation 'androidx.viewpager2:viewpager2:1.0.0'
//    implementation 'androidx.paging:paging-runtime-ktx:2.1.2'
//
//    implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.3.0-beta01'
//    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.3.0-beta01'
//    implementation 'androidx.lifecycle:lifecycle-service:2.3.0-beta01'
//    implementation "org.jetbrains.kotlin:kotlin-stdlib:1.4.32"
//    implementation 'com.google.android.material:material:1.2.1'
//
//    testImplementation 'junit:junit:4.12'
//    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
//    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
//    implementation 'com.jakewharton:butterknife:10.2.1'
//    annotationProcessor 'com.jakewharton:butterknife-compiler:10.2.1'
//    implementation 'com.blankj:utilcodex:1.29.0'
//    implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
//    implementation 'androidx.navigation:navigation-fragment:2.3.1'
//    implementation 'androidx.navigation:navigation-ui:2.3.1'
//    def room_version = "2.2.5"
//    implementation "androidx.room:room-runtime:$room_version"
//    annotationProcessor "androidx.room:room-compiler:$room_version"
//    testImplementation "androidx.room:room-testing:$room_version"
//    implementation 'com.android.volley:volley:1.1.1'
//    implementation 'androidx.swiperefreshlayout:swiperefreshlayout:1.1.0'
//    implementation 'androidx.navigation:navigation-fragment-ktx:2.3.1'
//    implementation 'androidx.navigation:navigation-ui-ktx:2.3.1'
//    implementation 'com.google.code.gson:gson:2.8.6'
//    implementation 'io.supercharge:shimmerlayout:2.1.0'
//    implementation 'com.github.chrisbanes.photoview:library:1.2.4'
//    implementation 'androidx.swiperefreshlayout:swiperefreshlayout:1.1.0'
//    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
//    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
//    implementation 'com.lcodecorex:tkrefreshlayout:1.0.7'
//
//    implementation "com.google.dagger:hilt-android:2.29.1-alpha"
//    kapt "com.google.dagger:hilt-android-compiler:2.29.1-alpha"
//    implementation 'androidx.multidex:multidex:2.0.1'
//}
dependencies {
    implementation(AndroidX.appcompat)
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.constraintlayout)
    implementation(AndroidX.legacy)
    implementation(ThirdParty.glide)
    implementation(ThirdParty.utilcodex)
    implementation(ThirdParty.volley)
    implementation(ThirdParty.photoview)
    implementation(ThirdParty.tkrefreshlayout)
    implementation(ThirdParty.retrofit2)
    implementation(ThirdParty.converterGson)
    implementation(ThirdParty.shimmerlayout)
    implementation(ThirdParty.butterknife)
    implementation(ThirdParty.hilt)
    kapt(ThirdParty.hiltcompiler)
    kapt(ThirdParty.butterknifeCompiler)
    implementation(AndroidX.Lifecycle.viewModelKtx)
    implementation(AndroidX.Lifecycle.liveDataKtx)
    implementation(AndroidX.Lifecycle.lifecycleRuntime)
    implementation(AndroidX.Lifecycle.service)
    implementation(AndroidX.Lifecycle.extensions)
    implementation(AndroidX.viewpager2)
    implementation(AndroidX.Paging.pagingRuntime)
    implementation(AndroidX.Paging.pagingRuntimeKtx)
    implementation(GradlePlugins.kotlinStdlib)
    implementation(AndroidX.material)
    implementation(AndroidX.Navigation.fragment)
    implementation(AndroidX.Navigation.fragmentKtx)
    implementation(AndroidX.Navigation.ui)
    implementation(AndroidX.Navigation.uiKtx)
    implementation(AndroidX.Room.roomKtx)
    kapt(AndroidX.Room.roomCompiler)
    implementation(AndroidX.Room.roomRuntime)
    implementation(AndroidX.Room.roomTesting)
    implementation(AndroidX.swiperefreshlayout)
    implementation(AndroidX.multidex)

}
