package com.udacity.asteroidradar

import android.app.Application
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
            val workManager = WorkManager.getInstance(applicationContext)

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .build()

            val fetchDataWorkerRequest = PeriodicWorkRequestBuilder<FetchDataWorker>(
                7,
                TimeUnit.DAYS
            )
                .setConstraints(constraints)
                .build()

            workManager.enqueueUniquePeriodicWork(
                FetchDataWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                fetchDataWorkerRequest
            )
        }
    }

    companion object {
        const val LOG_TAG = "AsteroidRadarApp"
    }
}