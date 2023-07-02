package deps

object AndroidX : Dependency() {

    object Versions {
        const val coreKtx = "1.7.0"
        const val workManager = "2.7.0-alpha05"
        const val material = "1.6.1"
        const val dataStore = "1.0.0"
    }

    private const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private const val material = "com.google.android.material:material:${Versions.material}"
    private const val workManager = "androidx.work:work-runtime-ktx:${Versions.workManager}"
    private const val dataStorePreference = "androidx.datastore:datastore-preferences:${Versions.dataStore}"
    private const val dataStoreProto = "androidx.datastore:datastore:${Versions.dataStore}"
    private const val location = "com.google.android.gms:play-services-location:20.0.0"
    private const val geofire = "com.firebase:geofire-android-common:3.1.0"
    private const val inAppUpdate = "com.google.android.play:app-update-ktx:2.0.1"
    private const val inAppReview = "com.google.android.play:review-ktx:2.0.1"
    private const val splashScreen = "androidx.core:core-splashscreen:1.0.0-beta02"


    override fun implementations() = listOf<String>(
        coreKtx,
        material,
        dataStorePreference,
        dataStoreProto,
        inAppUpdate,
        inAppReview,
        splashScreen,
        workManager
    )
}