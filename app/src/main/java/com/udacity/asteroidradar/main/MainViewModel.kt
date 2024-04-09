package com.udacity.asteroidradar.main

import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.API_QUERY_DATE_FORMAT
import com.udacity.asteroidradar.Constants.DEFAULT_END_DATE_DAYS
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat

class MainViewModel : ViewModel() {

    init {
        loadAsteroid()
    }

    private fun loadAsteroid() {
        viewModelScope.launch {
            try {
                val pictureOfDay = AsteroidApi.service.getPictureOfDay()
                Log.d(LOG_TAG, "PICTURE OF DAY: $pictureOfDay")

                val today = Calendar.getInstance()
                val dateEnd = SimpleDateFormat(API_QUERY_DATE_FORMAT).format(today.time)
                today.add(Calendar.DAY_OF_YEAR, -DEFAULT_END_DATE_DAYS) // Subtract 7 days
                val dateStart = SimpleDateFormat(API_QUERY_DATE_FORMAT).format(today.time)

                Log.d(LOG_TAG, "DATE START: $dateStart")
                Log.d(LOG_TAG, "DATE END: $dateEnd")

                val asteroidFeed = AsteroidApi.service.getFeedByRange(dateStart, dateEnd)

                _asteroidList.value = asteroidFeed.getData()
                _pictureOfDay.value = pictureOfDay
            } catch (err: Exception) {
                Log.e(LOG_TAG, err.toString())
            }
        }
    }

    private val _asteroidList = MutableLiveData<List<Asteroid>>()
    val asteroidList: LiveData<List<Asteroid>>
        get() = _asteroidList

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _navigateToAsteroidDetail = MutableLiveData<Asteroid?>()
    val navigateToAsteroidDetail: LiveData<Asteroid?>
        get() = _navigateToAsteroidDetail

    fun onAsteroidItemClicked(asteroid: Asteroid) {
        _navigateToAsteroidDetail.value = asteroid
    }

    fun onAsteroidDetailNavigated() {
        _navigateToAsteroidDetail.value = null
    }

    companion object {
        private const val LOG_TAG = "MainViewModel"
    }
}