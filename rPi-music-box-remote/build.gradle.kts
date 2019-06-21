plugins {
    id(Plugins.androidApplication)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinExtensions)
    kotlin(Plugins.kapt)
}

android {
    compileSdkVersion(AndroidConfig.compileSdkVersion)

    defaultConfig {
        minSdkVersion(AndroidConfig.minSdkVersion)
        targetSdkVersion(AndroidConfig.targetSdkVersion)

        applicationId = AndroidConfig.applicationId

        versionCode = AndroidConfig.versionCode
        versionName = AndroidConfig.versionName

        testInstrumentationRunner = AndroidConfig.testInstrumentationRunner
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        getByName("debug") {
            val debugSuffix = ".debug"

            versionNameSuffix = debugSuffix
            applicationIdSuffix = debugSuffix
        }

        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(project(":androidcommons"))
    implementation(project(":rxnearby"))

    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.lifecycleExtensions)

    implementation(Dependencies.Dagger2.dagger)
    kapt(Dependencies.Dagger2.compiler)

    implementation(Dependencies.Dagger2.androidSupport)
    kapt(Dependencies.Dagger2.androidProcessor)

    implementation(Dependencies.kotlinStdLib)

    implementation(Dependencies.rxJava2)
    implementation(Dependencies.rxJava2Android)
    implementation(Dependencies.rxJava2Kotlin)

    implementation(Dependencies.timber)

    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.testRunner)
    androidTestImplementation(Dependencies.espresso)
}
