package com.udacity.asteroidradar

object Constants {
    const val API_QUERY_DATE_FORMAT = "yyyy-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"
    const val TOKEN = "ksFHkwFi3fK9HeuBLsIdS8lhiFaHyKmG3JGUHxzm"
}

enum class AsteroidFilter {
    SHOW_WEEK,
    SHOW_TODAY,
    SHOW_SAVED
}