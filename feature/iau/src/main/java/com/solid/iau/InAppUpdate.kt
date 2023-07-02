package com.solid.iau

import android.app.Activity
import android.content.Intent
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.ActivityResult
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability

object InAppUpdate {

    private const val REQUEST_IAU_CODE = 1000

    private fun Int.isFlexPriority() = this in 2..3

    private fun Int.isImmediatePriority() = this in 4..5

    fun checkUpdate(activity: Activity) {
        val updateManager = AppUpdateManagerFactory.create(activity)

        // Returns an intent object that you use to check for an update.
        val updateInfoTask = updateManager.appUpdateInfo

        // Checks that the platform will allow the specified type of update.
        updateInfoTask.addOnSuccessListener { updateInfo ->
            val updateAvailable = updateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            val updateInProgress = updateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
            val priority = updateInfo.updatePriority()
            val allowImmediateUpdate = updateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            val allowFlexibleUpdate = updateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)

            when {
                updateInProgress -> {
                    if (priority.isImmediatePriority() && allowImmediateUpdate) {
                        startUpdate(
                            updateManager = updateManager,
                            activity = activity,
                            updateInfo = updateInfo,
                            updateType = AppUpdateType.IMMEDIATE
                        )
                    }
                }

                updateAvailable -> {
                    when {
                        priority.isImmediatePriority() && allowImmediateUpdate -> {
                            startUpdate(
                                updateManager = updateManager,
                                activity = activity,
                                updateInfo = updateInfo,
                                updateType = AppUpdateType.IMMEDIATE
                            )
                        }
                        priority.isFlexPriority() && allowFlexibleUpdate -> {
                            startUpdate(
                                updateManager = updateManager,
                                activity = activity,
                                updateInfo = updateInfo,
                                updateType = AppUpdateType.FLEXIBLE
                            )
                        }
                    }
                }

                else -> {

                }
            }
        }
    }

    fun onResume(updateManager: AppUpdateManager, activity: Activity, onUpdateDownloaded: () -> Unit) {
        // Returns an intent object that you use to check for an update.
        val updateInfoTask = updateManager.appUpdateInfo

        // Checks that the platform will allow the specified type of update.
        updateInfoTask.addOnSuccessListener { updateInfo ->
            val updateInProgress = updateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
            val priority = updateInfo.updatePriority()
            val allowImmediateUpdate = updateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            val allowFlexibleUpdate = updateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)

            when {
                updateInProgress -> {
                    if (priority.isImmediatePriority() && allowImmediateUpdate) {
                        startUpdate(
                            updateManager = updateManager,
                            activity = activity,
                            updateInfo = updateInfo,
                            updateType = AppUpdateType.IMMEDIATE
                        )
                    }
                }

                updateInfo.installStatus() == InstallStatus.DOWNLOADED -> {
                    onUpdateDownloaded()
                }

                else -> {

                }
            }
        }
    }

    private fun startUpdate(updateManager: AppUpdateManager, activity: Activity, updateInfo: AppUpdateInfo, @AppUpdateType updateType: Int) {
        updateManager.startUpdateFlowForResult(
            // Pass the intent that is returned by 'getAppUpdateInfo()'.
            updateInfo,
            // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
            updateType,
            // The current activity making the update request.
            activity,
            // Include a request code to later monitor this update request.
            REQUEST_IAU_CODE
        )
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IAU_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {

                }
                Activity.RESULT_CANCELED -> {

                }
                ActivityResult.RESULT_IN_APP_UPDATE_FAILED -> {

                }
            }
        }
    }
}