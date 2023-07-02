@file:Suppress("MemberVisibilityCanBePrivate")


object Build {
    const val applicationId = "com.solid.app"
    const val admobAppId = "ca-app-pub-3940256099942544~3347511713"
    const val versionMajor = 0
    const val versionMinor = 0
    const val versionPatch = 0
    const val versionBuild = 1

    const val compileSdk = 33
    const val minSdk = 26
    const val targetSdk = 31
    const val versionCode = versionMajor * 10000 + versionMinor * 1000 + versionPatch * 100 + versionBuild
    const val versionName = "$versionMajor.$versionMinor.$versionPatch.$versionBuild"

    const val proguard_android = "proguard-android.txt"
    const val proguard_common = "proguard-common.txt"
    const val proguard_specific = "proguard-specific.txt"
}