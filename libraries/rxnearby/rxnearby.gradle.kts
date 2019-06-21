plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinExtensions)
}

dependencies {
    implementation(project(":libraries:commons"))

    implementation(Dependencies.kotlinStdLib)

    implementation(Dependencies.nearby)

    implementation(Dependencies.rxJava2)
    implementation(Dependencies.rxJava2Android)
    implementation(Dependencies.rxJava2Kotlin)

    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.testRunner)
    androidTestImplementation(Dependencies.espresso)
}
