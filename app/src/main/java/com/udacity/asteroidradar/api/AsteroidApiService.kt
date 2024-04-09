package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.dto.AsteroidFeed
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val TOKEN = "albfdeCpbixPOOkRicEbYNQNdBP3Wzb1Yl19A6sg"

interface AsteroidApiService {
    @GET("neo/rest/v1/feed")
    suspend fun getFeedByRange(
        @Query("start_date") dateStart: String,
        @Query("end_date") dateEnd: String,
        @Query("api_key") apiKey: String = TOKEN
    ): AsteroidFeed

    @GET("planetary/apod")
    suspend fun getPictureOfDay(
        @Query("api_key") apiKey: String = TOKEN
    ): PictureOfDay
}

object AsteroidApi {
    private val retrofit: Retrofit by lazy {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        retrofit
    }

    val service: AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }
}
