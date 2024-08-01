package com.udacity.asteroidradar.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.database.AsteroidDatabase
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FetchDataWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    private val service = AsteroidApi.service

    private fun getCurrentWeekDatesFromRange(): Pair<String, String> {
        val endDate = Calendar.getInstance()
        val startDate = Calendar.getInstance()
        startDate.add(Calendar.DAY_OF_YEAR, -Constants.DEFAULT_END_DATE_DAYS)

        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        val startDateFormatted = dateFormat.format(startDate.time)
        val endDateFormatted = dateFormat.format(endDate.time)

        return Pair(startDateFormatted, endDateFormatted)
    }

    override suspend fun doWork(): Result {
        val (startDate, endDate) = getCurrentWeekDatesFromRange()

        return try {
            Log.d(LOG_TAG, "Fetching data from NASA API")
            val asteroidFeed = service.getFeedByRange(startDate, endDate)
            val asteroidEntities = asteroidFeed.asEntities()
            Log.d(LOG_TAG, "Fetched ${asteroidEntities.size} asteroids")

            val asteroidDao = AsteroidDatabase.getInstance(context).asteroidDao()
            asteroidDao.insertAll(*asteroidEntities.toTypedArray())

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