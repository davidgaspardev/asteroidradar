package com.udacity.asteroidradar.repositories

import android.content.Context
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

    private val service = AsteroidApi.service

    private fun getDatesFromRange(): Pair<String, String> {
        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()
        endDate.add(Calendar.DAY_OF_YEAR, -Constants.DEFAULT_END_DATE_DAYS)

        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        val startDateFormatted = dateFormat.format(startDate.time)
        val endDateFormatted = dateFormat.format(endDate.time)

        return Pair(startDateFormatted, endDateFormatted)
    }

    suspend fun getFeed(context: Context): List<Asteroid> = withContext(Dispatchers.IO) {
        val (startDate, endDate) = getDatesFromRange()
        val asteroidFeed = service.getFeedByRange(startDate, endDate)
        val asteroidEntities = asteroidFeed.asEntities()

        val asteroidDao = AsteroidDatabase.getInstance(context).asteroidDao()
        asteroidDao.insertAll(*asteroidEntities.toTypedArray())

        return@withContext asteroidDao.getAll().asDomainModel()
    }
}