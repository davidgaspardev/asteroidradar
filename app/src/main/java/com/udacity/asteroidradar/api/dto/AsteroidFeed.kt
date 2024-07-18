package com.udacity.asteroidradar.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.database.entities.AsteroidEntity
import com.udacity.asteroidradar.domain.Asteroid

/**
 * ```json
 * {
 * 	"links": {
 * 		"next": "http://api.nasa.gov/neo/rest/v1/feed?start_date=2023-11-02&end_date=2023-11-02&detailed=false&api_key=albfdeCpbixPOOkRicEbYNQNdBP3Wzb1Yl19A6sg",
 * 		"prev": "http://api.nasa.gov/neo/rest/v1/feed?start_date=2023-10-31&end_date=2023-10-31&detailed=false&api_key=albfdeCpbixPOOkRicEbYNQNdBP3Wzb1Yl19A6sg",
 * 		"self": "http://api.nasa.gov/neo/rest/v1/feed?start_date=2023-11-01&end_date=2023-11-01&detailed=false&api_key=albfdeCpbixPOOkRicEbYNQNdBP3Wzb1Yl19A6sg"
 * 	},
 * 	"element_count": 7,
 * 	"near_earth_objects": {
 * 		"2023-11-01": [
 * 			{
 * 				"links": {
 * 					"self": "http://api.nasa.gov/neo/rest/v1/neo/2405562?api_key=albfdeCpbixPOOkRicEbYNQNdBP3Wzb1Yl19A6sg"
 * 				},
 * 				"id": "2405562",
 * 				"neo_reference_id": "2405562",
 * 				"name": "405562 (2005 OJ3)",
 * 				"nasa_jpl_url": "http://ssd.jpl.nasa.gov/sbdb.cgi?sstr=2405562",
 * 				"absolute_magnitude_h": 18.23,
 * 				"estimated_diameter": {
 * 					"kilometers": {
 * 						"estimated_diameter_min": 0.6005580277,
 * 						"estimated_diameter_max": 1.3428885744
 * 					},
 * 					"meters": {
 * 						"estimated_diameter_min": 600.5580277247,
 * 						"estimated_diameter_max": 1342.8885744257
 * 					},
 * 					"miles": {
 * 						"estimated_diameter_min": 0.3731693422,
 * 						"estimated_diameter_max": 0.8344320164
 * 					},
 * 					"feet": {
 * 						"estimated_diameter_min": 1970.3347996804,
 * 						"estimated_diameter_max": 4405.8025505189
 * 					}
 * 				},
 * 				"is_potentially_hazardous_asteroid": false,
 * 				"close_approach_data": [
 * 					{
 * 						"close_approach_date": "2023-11-01",
 * 						"close_approach_date_full": "2023-Nov-01 19:26",
 * 						"epoch_date_close_approach": 1698866760000,
 * 						"relative_velocity": {
 * 							"kilometers_per_second": "4.7502698212",
 * 							"kilometers_per_hour": "17100.9713564722",
 * 							"miles_per_hour": "10625.8785631369"
 * 						},
 * 						"miss_distance": {
 * 							"astronomical": "0.3377906226",
 * 							"lunar": "131.4005521914",
 * 							"kilometers": "50532757.646933862",
 * 							"miles": "31399599.5830248156"
 * 						},
 * 						"orbiting_body": "Earth"
 * 					}
 * 				],
 * 				"is_sentry_object": false
 * 			}
 * 		]
 * 	}
 *  ```
 */
@JsonClass(generateAdapter = true)
data class AsteroidFeed(
    @Json(name = "links") val links: Links,
    @Json(name = "element_count") val elementCount: Int,
    @Json(name = "near_earth_objects") val nearEarthObjects: Map<String, List<NearEarthObject>>
) {
    /**
     * ```json
     * {
     *      "links": {
     *         "next": "http://api.nasa.gov/neo/rest/v1/feed?start_date=2023-11-02&end_date=2023-11-02&detailed=false&api_key=albfdeCpbixPOOkRicEbYNQNdBP3Wzb1Yl19A6sg",
     *         "prev": "http://api.nasa.gov/neo/rest/v1/feed?start_date=2023-10-31&end_date=2023-10-31&detailed=false&api_key=albfdeCpbixPOOkRicEbYNQNdBP3Wzb1Yl19A6sg",
     *         "self": "http://api.nasa.gov/neo/rest/v1/feed?start_date=2023-11-01&end_date=2023-11-01&detailed=false&api_key=albfdeCpbixPOOkRicEbYNQNdBP3Wzb1Yl19A6sg"
     *     }
     *  }
     *  ```
     */
    data class Links (
        @Json(name = "next") val next: String?,
        @Json(name = "prev") val prev: String?,
        @Json(name = "self") val self: String
    )

    /**
     * ```json
     * {
     *      "links": {
     *          "self": "http://api.nasa.gov/neo/rest/v1/neo/2405562?api_key=albfdeCpbixPOOkRicEbYNQNdBP3Wzb1Yl19A6sg"
     *      },
     *      "id": "2405562",
     *      "neo_reference_id": "2405562",
     *      "name": "405562 (2005 OJ3)",
     *      "nasa_jpl_url": "http://ssd.jpl.nasa.gov/sbdb.cgi?sstr=2405562",
     *      "absolute_magnitude_h": 18.23,
     *      "estimated_diameter": {
     *          "kilometers": {
     *              "estimated_diameter_min": 0.6005580277,
     *              "estimated_diameter_max": 1.3428885744
     *          },
     *          "meters": {
     *              "estimated_diameter_min": 600.5580277247,
     *              "estimated_diameter_max": 1342.8885744257
     *          },
     *          "miles": {
     *              "estimated_diameter_min": 0.3731693422,
     *              "estimated_diameter_max": 0.8344320164
     *          },
     *          "feet": {
     *              "estimated_diameter_min": 1970.3347996804,
     *              "estimated_diameter_max": 4405.8025505189
     *          }
     *      },
     *      "is_potentially_hazardous_asteroid": false,
     *      "close_approach_data": [
     *          {
     *              "close_approach_date": "2023-11-01",
     *              "close_approach_date_full": "2023-Nov-01 19:26",
     *              "epoch_date_close_approach": 1698866760000,
     *              "relative_velocity": {
     *                  "kilometers_per_second": "4.7502698212",
     *                  "kilometers_per_hour": "17100.9713564722",
     *                  "miles_per_hour": "10625.8785631369"
     *              },
     *              "miss_distance": {
     *                  "astronomical": "0.3377906226",
     *                  "lunar": "131.4005521914",
     *                  "kilometers": "50532757.646933862",
     *                  "miles": "31399599.5830248156"
     *              },
     *              "orbiting_body": "Earth"
     *          }
     *      ],
     *      "is_sentry_object": false
     *  }
     *  ```
     */
    data class NearEarthObject (
        @Json(name = "id") val id: Long,
        @Json(name = "name") val name: String,
        @Json(name = "absolute_magnitude_h") val absoluteMagnitudeH: Double,
        @Json(name = "estimated_diameter") val estimatedDiameter: EstimatedDiameter,
        @Json(name = "close_approach_data") val closeApproachData: List<CloseApproachData>,
        @Json(name = "is_potentially_hazardous_asteroid") val isPotentiallyHazardousAsteroid: Boolean
    ) {

        data class EstimatedDiameter(
            @Json(name = "kilometers") val kilometers: Kilometers
        ) {

            data class Kilometers(
                @Json(name = "estimated_diameter_max") val estimatedDiameterMax: Double
            )
        }

        data class CloseApproachData(
            @Json(name = "relative_velocity") val relativeVelocity: RelativeVelocity,
            @Json(name = "miss_distance") val missDistance: MissDistance
        ) {

            data class RelativeVelocity(
                @Json(name = "kilometers_per_second") val kilometersPerSecond: Double
            )

            data class MissDistance(
                @Json(name = "astronomical") val astronomical: Double
            )
        }
    }

    fun asDomainModels(): List<Asteroid> {
        val asteroids = mutableListOf<Asteroid>()

        nearEarthObjects.forEach { (date, nearEarthObjects) ->
            nearEarthObjects.forEach { nearEarthObject ->
                asteroids.add(
                    Asteroid(
                        id = nearEarthObject.id,
                        codename = nearEarthObject.name,
                        closeApproachDate = date,
                        absoluteMagnitude = nearEarthObject.absoluteMagnitudeH,
                        estimatedDiameter = nearEarthObject.estimatedDiameter.kilometers.estimatedDiameterMax,
                        relativeVelocity = nearEarthObject.closeApproachData.first().relativeVelocity.kilometersPerSecond,
                        distanceFromEarth = nearEarthObject.closeApproachData.first().missDistance.astronomical,
                        isPotentiallyHazardous = nearEarthObject.isPotentiallyHazardousAsteroid
                    )
                )
            }
        }

        return asteroids
    }

    fun asEntities(): List<AsteroidEntity> {
        val asteroidEntities = mutableListOf<AsteroidEntity>()

        nearEarthObjects.forEach { (date, nearEarthObjects) ->
            nearEarthObjects.forEach { nearEarthObject ->
                asteroidEntities.add(
                    AsteroidEntity(
                        id = nearEarthObject.id,
                        codename = nearEarthObject.name,
                        closeApproachDate = date,
                        absoluteMagnitude = nearEarthObject.absoluteMagnitudeH,
                        estimatedDiameter = nearEarthObject.estimatedDiameter.kilometers.estimatedDiameterMax,
                        relativeVelocity = nearEarthObject.closeApproachData.first().relativeVelocity.kilometersPerSecond,
                        distanceFromEarth = nearEarthObject.closeApproachData.first().missDistance.astronomical,
                        isPotentiallyHazardous = nearEarthObject.isPotentiallyHazardousAsteroid
                    )
                )
            }
        }

        return asteroidEntities
    }
}
