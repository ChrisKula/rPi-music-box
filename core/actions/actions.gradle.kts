plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinExtensions)
}

androidExtensions {
    isExperimental = true
}

dependencies {
    implementation(Dependencies.kotlinStdLib)
}
