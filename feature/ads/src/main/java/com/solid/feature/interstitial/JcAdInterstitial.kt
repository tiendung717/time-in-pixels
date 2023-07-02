package com.solid.feature.interstitial

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.solid.common.network.ConnectionState
import com.solid.common.network.connectivityState
import com.solid.feature.AdRequestFactory
import com.solid.feature.AdUnit
import timber.log.Timber

@Composable
fun JcAdInterstitial(
    adUnit: AdUnit = AdUnit.TestInterstitial,
    show: Boolean,
    onAdFailedToLoad: () -> Unit,
    onAdFailedToShow: () -> Unit,
    onAdClosed: () -> Unit
) {
    val activity = LocalContext.current as ComponentActivity
    var ad: InterstitialAd? by remember { mutableStateOf(null) }
    val connectionState by connectivityState()

    val fullContentCallback = remember {
        object : FullScreenContentCallback() {
            override fun onAdClicked() {
                super.onAdClicked()
                onAdClosed()
            }

            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                ad = null
                onAdClosed()
            }

            override fun onAdFailedToShowFullScreenContent(error: AdError) {
                super.onAdFailedToShowFullScreenContent(error)
                Timber.tag("nt.dung").e(error.message)
                ad = null
                onAdFailedToShow()
            }
        }
    }

    DisposableEffect(show, connectionState) {
        if (connectionState == ConnectionState.Available) {
            InterstitialAd.load(
                activity,
                adUnit.unitId,
                AdRequestFactory.create(),
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(error: LoadAdError) {
                        Timber.tag("nt.dung").e(error.message)
                        ad = null
                        onAdFailedToLoad()
                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        Timber.tag("nt.dung").e("Ad loaded!!!")
                        ad = interstitialAd
                        ad?.fullScreenContentCallback = fullContentCallback
                    }
                })
        }

        onDispose {
            ad = null
        }
    }

    LaunchedEffect(ad, show) {
        if (show) {
            ad?.show(activity)
        }
    }
}
