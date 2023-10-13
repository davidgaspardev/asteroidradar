package com.udacity.asteroidradar

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Asteroid(
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) : Parcelable

val asteroids = arrayOf(
    Asteroid(1, "Asteroid1", "2023-10-01", 20.3, 4.2, 25.5, 1.2e6, false),
    Asteroid(2, "Asteroid2", "2023-10-02", 22.1, 3.1, 18.5, 2.2e6, true),
    Asteroid(3, "Asteroid3", "2023-10-03", 21.2, 2.2, 28.5, 1.1e6, false),
    Asteroid(4, "Asteroid4", "2023-10-04", 19.3, 5.1, 16.5, 2.8e6, true),
    Asteroid(5, "Asteroid5", "2023-10-05", 18.1, 3.3, 23.5, 3.2e6, false),
    Asteroid(6, "Asteroid6", "2023-10-06", 23.3, 4.7, 14.5, 2.9e6, true),
    Asteroid(7, "Asteroid7", "2023-10-07", 17.5, 3.8, 15.5, 1.4e6, false),
    Asteroid(8, "Asteroid8", "2023-10-08", 24.4, 6.2, 22.5, 3.5e6, true),
    Asteroid(9, "Asteroid9", "2023-10-09", 23.1, 2.6, 21.5, 1.7e6, false),
    Asteroid(10, "Asteroid10", "2023-10-10", 19.9, 4.1, 17.5, 2.1e6, false),
    Asteroid(11, "Asteroid11", "2023-10-11", 22.7, 3.6, 24.5, 1.6e6, false),
    Asteroid(12, "Asteroid12", "2023-10-12", 21.6, 3.9, 19.5, 1.5e6, true),
    Asteroid(13, "Asteroid13", "2023-10-13", 20.5, 2.7, 20.5, 3.3e6, false),
    Asteroid(14, "Asteroid14", "2023-10-14", 18.7, 3.4, 16.5, 2.4e6, true),
    Asteroid(15, "Asteroid15", "2023-10-15", 19.8, 5.6, 23.5, 1.3e6, true),
    Asteroid(16, "Asteroid16", "2023-10-16", 24.2, 4.5, 18.5, 3.1e6, true)
)