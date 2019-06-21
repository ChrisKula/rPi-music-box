plugins {
    id(Plugins.androidApplication)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinExtensions)
    kotlin(Plugins.kapt)
}

android {
    defaultConfig {
        applicationId = AndroidConfig.applicationId

        versionCode = AndroidConfig.versionCode
        versionName = AndroidConfig.versionName

        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        getByName("debug") {
            val debugSuffix = ".debug"

            versionNameSuffix = debugSuffix
            applicationIdSuffix = debugSuffix
        }
    }
}

androidExtensions {
    isExperimental = true
}

dependencies {
    implementation(project(":libraries:commons"))
    implementation(project(":libraries:androidcommons"))
    implementation(project(":libraries:rxnearby"))

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
