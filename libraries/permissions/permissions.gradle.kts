plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinExtensions)
}

dependencies {
    implementation(Dependencies.AndroidX.appCompat)

    implementation(Dependencies.kotlinStdLib)

    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.testRunner)
    androidTestImplementation(Dependencies.espresso)
}
