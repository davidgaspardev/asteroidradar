package com.udacity.asteroidradar.repositories

import android.content.Context
import android.util.Log
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.entities.asDomainModel
import com.udacity.asteroidradar.domain.Asteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object AsteroidRepository {
    private val LOG_TAG = "AsteroidRepository"
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

    suspend fun getCurrentWeek(context: Context): List<Asteroid> {
        val (startDate, endDate) = getCurrentWeekDatesFromRange()

        return getByDateRange(context, startDate, endDate)
    }

    suspend fun getByDateRange(context: Context, startDate: String, endDate: String): List<Asteroid> = withContext(Dispatchers.IO) {
        Log.d(LOG_TAG, "DATE START: $startDate")
        Log.d(LOG_TAG, "DATE END: $endDate")

        val asteroidFeed = service.getFeedByRange(startDate, endDate)
        val asteroidEntities = asteroidFeed.asEntities()

        val asteroidDao = AsteroidDatabase.getInstance(context).asteroidDao()
        asteroidDao.insertAll(*asteroidEntities.toTypedArray())

        return@withContext asteroidDao.getByDateRange(startDate, endDate).asDomainModel()
    }

    suspend fun getToday(context: Context): List<Asteroid> = withContext(Dispatchers.IO) {
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        val today = dateFormat.format(Calendar.getInstance().time)

        return@withContext getByDateRange(context, today, today)
    }

    suspend fun getAll(context: Context): List<Asteroid> = withContext(Dispatchers.IO) {
        val asteroidDao = AsteroidDatabase.getInstance(context).asteroidDao()
        return@withContext asteroidDao.getAll().asDomainModel()
    }
}