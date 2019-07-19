plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinExtensions)
    kotlin(Plugins.kapt)
}


android {
    defaultConfig {
        vectorDrawables.useSupportLibrary = true
    }
}

androidExtensions {
    isExperimental = true
}

dependencies {
    implementation(project(":core:design"))

    implementation(project(":libraries:commons"))
    implementation(project(":libraries:androidcommons"))
    api(project(":libraries:rxnearby"))
    api(project(":libraries:permissions"))

    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.lifecycleExtensions)

    implementation(Dependencies.Dagger2.dagger)
    kapt(Dependencies.Dagger2.compiler)

    implementation(Dependencies.Dagger2.androidSupport)
    kapt(Dependencies.Dagger2.androidProcessor)

    implementation(Dependencies.kotlinStdLib)

    implementation(Dependencies.rxJava2)
    implementation(Dependencies.rxJava2Android)
    implementation(Dependencies.rxJava2Kotlin)

    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.testRunner)
    androidTestImplementation(Dependencies.espresso)
}
