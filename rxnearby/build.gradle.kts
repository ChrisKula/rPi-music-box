plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinExtensions)
}

android {
    compileSdkVersion(AndroidConfig.compileSdkVersion)

    defaultConfig {
        minSdkVersion(AndroidConfig.minSdkVersion)
        targetSdkVersion(AndroidConfig.targetSdkVersion)

        testInstrumentationRunner = AndroidConfig.testInstrumentationRunner
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(project(":commons"))

    implementation(Dependencies.kotlinStdLib)

    implementation(Dependencies.nearby)

    implementation(Dependencies.rxJava2)
    implementation(Dependencies.rxJava2Android)
    implementation(Dependencies.rxJava2Kotlin)

    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.testRunner)
    androidTestImplementation(Dependencies.espresso)
}
