package com.udacity.asteroidradar

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.udacity.asteroidradar.work.FetchDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidRadarApplication: Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()

        loadWorkers()
    }

    private fun loadWorkers() {
        applicationScope.launch {
            Log.d(LOG_TAG, "Setting up work constraints")
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .build()

            Log.d(LOG_TAG, "Creating periodic work request")
            val fetchDataWorkerRequest = PeriodicWorkRequestBuilder<FetchDataWorker>(
                    7,
                    TimeUnit.DAYS
                )
                .setConstraints(constraints)
                .build()

            Log.d(LOG_TAG, "Enqueuing periodic work request")
            WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
                FetchDataWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                fetchDataWorkerRequest
            )
            Log.d(LOG_TAG, "Periodic work request enqueued")
        }
    }

    companion object {
        const val LOG_TAG = "AsteroidRadarApp"
    }
}