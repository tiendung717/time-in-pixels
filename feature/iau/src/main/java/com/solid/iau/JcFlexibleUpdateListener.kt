package com.solid.iau

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.InstallStatus

@Composable
fun JcFlexibleUpdateListener(onDownloadProgress: (Float) -> Unit, onUpdateDownloaded: () -> Unit) {
    JcFlexibleUpdateProgress(
        onDownloadProgress = onDownloadProgress,
        onUpdateDownloaded = onUpdateDownloaded
    )
}

@Composable
fun JcFlexibleUpdateProgress(
    onDownloadProgress: (Float) -> Unit,
    onUpdateDownloaded: () -> Unit
) {
    val context = LocalContext.current
    val updateManager = remember {
        AppUpdateManagerFactory.create(context)
    }

    DisposableEffect(Unit) {
        val listener = InstallStateUpdatedListener { state ->
            when (state.installStatus()) {
                InstallStatus.DOWNLOADING -> {
                    val bytesDownloaded = state.bytesDownloaded()
                    val totalBytesToDownload = state.totalBytesToDownload()
                    val progress = bytesDownloaded.toFloat().div(totalBytesToDownload)
                    onDownloadProgress(progress)
                }
                InstallStatus.DOWNLOADED -> {
                    onUpdateDownloaded()
                }
                else -> {

                }
            }
        }

        updateManager.registerListener(listener)

        onDispose {
            updateManager.unregisterListener(listener)
        }
    }
}