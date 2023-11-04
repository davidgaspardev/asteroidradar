package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.dto.AsteroidFeed
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val TOKEN = "albfdeCpbixPOOkRicEbYNQNdBP3Wzb1Yl19A6sg"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface AsteroidApiService {
    @GET("neo/rest/v1/feed")
    suspend fun getFeedByRange(
        @Query("start_date") dateStart: String,
        @Query("end_date") dateEnd: String,
        @Query("api_key") apiKey: String = TOKEN
    ): AsteroidFeed
}

object AsteroidApi {
    val asteroidService: AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }
}
