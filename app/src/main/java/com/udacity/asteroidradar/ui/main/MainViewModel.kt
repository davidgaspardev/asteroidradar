package com.udacity.asteroidradar.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.udacity.asteroidradar.AsteroidFilter
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.repositories.AsteroidRepository
import com.udacity.asteroidradar.work.FetchDataWorker
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val asteroidRepo = AsteroidRepository

    init {
        loadAsteroid()
        setupBackground()
    }

    private fun loadAsteroid(forceSync: Boolean = false) {
        viewModelScope.launch {
            try {
                if (forceSync) {
                    asteroidRepo.sync(getApplication())
                }

                val pictureOfDay = AsteroidApi.service.getPictureOfDay()
                val asteroids = asteroidRepo.getCurrentWeek(getApplication())

                _pictureOfDay.value = pictureOfDay
                _asteroidList.value = asteroids
            } catch (err: Exception) {
                Log.e(LOG_TAG, err.toString())
            }
        }
    }

    private fun setupBackground() {
        val workManager = WorkManager.getInstance(getApplication())
        val workInfosLiveData = workManager.getWorkInfosForUniqueWorkLiveData(
            FetchDataWorker.WORK_NAME
        )

        workInfosLiveData.observeForever { workInfos ->
            Log.d(LOG_TAG, "WorkInfos: $workInfos")
            if (workInfos.isNotEmpty()) {
                val isWorkFinished = workInfos.first().state.isFinished

                loadAsteroid(!isWorkFinished)
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