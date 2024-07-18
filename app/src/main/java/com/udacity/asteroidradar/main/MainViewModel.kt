package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.AsteroidFilter
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.repositories.AsteroidRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(application: Application) : AndroidViewModel(application) {

    init {
        loadAsteroid()
    }

    private fun loadAsteroid() {
        viewModelScope.launch {
            try {
                val pictureOfDay = AsteroidApi.service.getPictureOfDay()
                Log.d(LOG_TAG, "PICTURE OF DAY: $pictureOfDay")

                val asteroidRepo = AsteroidRepository
                val asteroids = asteroidRepo.getCurrentWeek(getApplication())

                Log.d(LOG_TAG, "ASTEROIDS: ${asteroids.size}")
                _asteroidList.value = asteroids
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

    fun updateFilter(filter: AsteroidFilter) {
        viewModelScope.launch {
            try {
                val asteroidRepo = AsteroidRepository
                val asteroids = when (filter) {
                    AsteroidFilter.SHOW_WEEK -> asteroidRepo.getCurrentWeek(getApplication())
                    AsteroidFilter.SHOW_TODAY -> asteroidRepo.getToday(getApplication())
                    AsteroidFilter.SHOW_SAVED -> asteroidRepo.getAll(getApplication())
                }

                _asteroidList.value = asteroids
            } catch (err: Exception) {
                Log.e(LOG_TAG, err.toString())
            }
        }
    }

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