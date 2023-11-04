package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel : ViewModel() {

    init {
        loadAsteroid()
    }

    private fun loadAsteroid() {
        viewModelScope.launch {
            try {
                val asteroidFeed = AsteroidApi.asteroidService.getFeedByRange("2023-11-01", "2023-11-07")
                Log.d(LOG_TAG, asteroidFeed.getData().toString())

                _asteroidList.value = asteroidFeed.getData()
            } catch (err: Exception) {
                Log.e(LOG_TAG, err.toString())
            }
        }
    }

    private val _asteroidList = MutableLiveData<List<Asteroid>>()
    val asteroidList: LiveData<List<Asteroid>>
        get() = _asteroidList

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