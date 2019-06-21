plugins {
    id(Plugins.javaLib)
    kotlin(Plugins.kotlin)
}

dependencies {
    implementation(Dependencies.kotlinStdLib)

    implementation(Dependencies.rxJava2)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
