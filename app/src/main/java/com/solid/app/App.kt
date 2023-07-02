package com.solid.app

import android.app.Application
import com.solid.app.BuildConfig
import com.solid.feature.ads.AdInitializer
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupLog()
        setupAds()
    }

    private fun setupLog() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupAds() {
        AdInitializer.init(this)
    }
}