object AndroidConfig {
    private const val versionMajor = 0
    private const val versionMinor = 1
    private const val versionPatch = 0

    const val applicationId = "com.christiankula.rpimusicbox"
    const val compileSdkVersion = 28
    const val targetSdkVersion = 28
    const val minSdkVersion = 21

    const val buildToolsVersion = "28.0.3"

    const val versionCode = versionMajor * 1000 + versionMinor * 100 + versionPatch * 10
    const val versionName = "$versionMajor.$versionMinor.$versionPatch"

    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}
