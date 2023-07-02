package com.solid.remoteconfig

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class RemoteConfigFetchWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val remoteConfig: RemoteConfig
) : CoroutineWorker(context, workerParameters) {

    companion object {

        fun execute(context: Context) {
            val request = OneTimeWorkRequest.Builder(RemoteConfigFetchWorker::class.java)
                .build()
            WorkManager.getInstance(context).enqueueUniqueWork(
                "RemoteConfigFetchWorker",
                ExistingWorkPolicy.REPLACE,
                request
            )
        }
    }

    override suspend fun doWork(): Result {
        remoteConfig.fetch()
        return Result.success()
    }
}
