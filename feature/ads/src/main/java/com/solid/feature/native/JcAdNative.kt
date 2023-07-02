package com.solid.feature.native

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.solid.feature.AdRequestFactory
import com.solid.feature.AdUnit
import com.solid.feature.ads.R
import com.solid.theme.typography.UIKitTypography
import timber.log.Timber


@Composable
fun JcAdNative(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean,
    adUnit: AdUnit = AdUnit.TestNative,
    onAdFailedToLoad: () -> Unit = {},
    adLayout: @Composable (Boolean, NativeAd) -> Unit = { darkMode, ad ->
        TemplateNativeBanner(modifier, darkMode, ad)
    }
) {
    val context = LocalContext.current
    var nativeAd by remember { mutableStateOf<NativeAd?>(null) }
    var adLoaded by remember { mutableStateOf(false) }

    val adLoader = remember {
        AdLoader.Builder(context, adUnit.unitId)
            .forNativeAd { ad: NativeAd ->
                nativeAd = ad
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Timber.tag("nt.dung").e(adError.message)
                    onAdFailedToLoad()
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    adLoaded = true
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    .build()
            )
            .build()
    }

    DisposableEffect(adUnit) {
        adLoader.loadAd(AdRequestFactory.create())
        onDispose {
            nativeAd?.destroy()
        }
    }

    Box(modifier = modifier.height(80.dp)) {
        if (!adLoaded) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    text = stringResource(R.string.text_ad_placeholder),
                    style = UIKitTypography.Caption1Medium12,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            nativeAd?.let {
                adLayout(isDarkMode, it)
            }
        }
    }
}

@Composable
private fun TemplateNativeBanner(modifier: Modifier, darkMode: Boolean, ad: NativeAd) {
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = {
            TemplateNativeAdView(it).apply {
                render(ad, darkMode)
            }
        },
        update = {
            it.render(ad, darkMode)
        }
    )
}