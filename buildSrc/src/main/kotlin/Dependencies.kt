object Dependencies {

    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:${Versions.AndroidX.appCompat}"
        const val ktxCore = "androidx.core:core-ktx:${Versions.AndroidX.ktxCore}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.constraintLayout}"
        const val lifecycleExtensions =
                "androidx.lifecycle:lifecycle-extensions:${Versions.AndroidX.lifecycleExtensions}"
    }

    object Dagger2 {
        const val dagger = "com.google.dagger:dagger:${Versions.dagger2}"
        const val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger2}"

        const val androidSupport = "com.google.dagger:dagger-android-support:${Versions.dagger2}"
        const val androidProcessor = "com.google.dagger:dagger-android-processor:${Versions.dagger2}"
    }

    const val materialComponents = "com.google.android.material:material:${Versions.materialComponents}"

    const val nearby = "com.google.android.gms:play-services-nearby:${Versions.googlePlayServices}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    // RxJava2
    const val rxJava2 = "io.reactivex.rxjava2:rxjava:${Versions.rxJava2}"
    const val rxJava2Android = "io.reactivex.rxjava2:rxandroid:${Versions.rxJava2Android}"
    const val rxJava2Kotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxJava2Kotlin}"

    // Testing
    const val junit = "junit:junit:${Versions.junit}"

    // Android testing
    const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}
