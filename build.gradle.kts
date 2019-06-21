buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(ClassPaths.androidGradlePlugin)
        classpath(ClassPaths.kotlinPlugin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}
