package com.solid.feature.banner

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Point
import android.os.Build
import android.view.WindowMetrics
import androidx.activity.ComponentActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.solid.common.network.ConnectionState
import com.solid.common.network.connectivityState
import com.solid.feature.AdRequestFactory
import com.solid.feature.AdUnit
import timber.log.Timber
import com.solid.feature.ads.R

@SuppressLint("VisibleForTests")
@Composable
fun JcAdBanner(
    modifier: Modifier,
    ad: AdUnit = AdUnit.TestBanner,
    onAdFailedToLoad: () -> Unit = {}
) {
    val activity = LocalContext.current as ComponentActivity
    val adSize = remember { getAdSize(activity) }
    val connectionState by connectivityState()
    if (connectionState == ConnectionState.Available) {
        var adView: AdView? = remember { null }
        var adLoaded by remember { mutableStateOf(false) }
        Box(
            modifier = modifier.height(adSize.height.dp)
        ) {
            if (!adLoaded) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                        )
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        text = stringResource(R.string.text_ad_placeholder),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }

            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = {
                    adView = AdView(it)
                        .apply {
                            setAdSize(adSize)
                            adUnitId = ad.unitId
                        }.also {
                            it.adListener = object : AdListener() {
                                override fun onAdFailedToLoad(error: LoadAdError) {
                                    Timber.e(error.message)
                                    onAdFailedToLoad()
                                }

                                override fun onAdLoaded() {
                                    super.onAdLoaded()
                                    adLoaded = true
                                }
                            }
                            it.loadAd(AdRequestFactory.create())
                        }
                    adView!!
                }
            )
        }

        DisposableEffect(ad) {
            onDispose {
                adView?.destroy()
            }
        }
    }
}

@SuppressLint("VisibleForTests")
private fun getAdSize(activity: Activity): AdSize {
    val adWidthPixels = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics: WindowMetrics = activity.windowManager.currentWindowMetrics
        val bounds = windowMetrics.bounds
        bounds.width().toFloat()
    } else {
        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        size.x.toFloat()
    }

    val density: Float = activity.resources.displayMetrics.density
    val adWidth = (adWidthPixels / density).toInt()
    return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth)
}