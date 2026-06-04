package com.weatherly.weather.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherApiService
    @Inject
    constructor(
        private val httpClient: HttpClient,
    ) {
        companion object {
            private const val FORECAST_BASE_URL = "https://api.open-meteo.com/v1"
            private const val GEOCODING_BASE_URL = "https://geocoding-api.open-meteo.com/v1"

            private const val CURRENT_FIELDS =
                "temperature_2m,relative_humidity_2m,apparent_temperature," +
                    "weather_code,wind_speed_10m,pressure_msl,cloud_cover"

            private const val HOURLY_FIELDS =
                "temperature_2m,weather_code"

            private const val DAILY_FIELDS =
                "temperature_2m_max,temperature_2m_min,weather_code," +
                    "precipitation_probability_max,sunrise,sunset,uv_index_max"
        }

        suspend fun getWeatherForecast(
            latitude: Double,
            longitude: Double,
            timezone: String = "auto",
        ): OpenMeteoResponseDto =
            httpClient
                .get("$FORECAST_BASE_URL/forecast") {
                    url {
                        parameters.append("latitude", latitude.toString())
                        parameters.append("longitude", longitude.toString())
                        parameters.append("timezone", timezone)

                        parameters.append("current", CURRENT_FIELDS)
                        parameters.append("hourly", HOURLY_FIELDS)
                        parameters.append("daily", DAILY_FIELDS)
                    }
                }.body()

        suspend fun searchCities(
            query: String,
            count: Int = 10,
        ): OpenMeteoGeocodingResponseDto =
            httpClient
                .get("$GEOCODING_BASE_URL/search") {
                    url {
                        parameters.append("name", query)
                        parameters.append("count", count.toString())
                        parameters.append("language", "ru")
                        parameters.append("format", "json")
                    }
                }.body()
    }
