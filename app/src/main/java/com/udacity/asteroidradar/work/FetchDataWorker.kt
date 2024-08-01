package com.udacity.asteroidradar.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.repositories.AsteroidRepository
import retrofit2.HttpException

class FetchDataWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    private val asteroidRepo = AsteroidRepository

    override suspend fun doWork(): Result {
        return try {
            asteroidRepo.sync(context)

            Result.success()
        } catch (exception: HttpException) {
            Log.w(LOG_TAG, "Error fetching data", exception)

            Result.retry()
        } catch (exception: Exception) {
            Log.e(LOG_TAG, "Error fetching data", exception)

            Result.failure()
        }
    }

    companion object {
        const val LOG_TAG = "FetchDataWorker"
        const val WORK_NAME = "FetchDataWorker"
    }
}