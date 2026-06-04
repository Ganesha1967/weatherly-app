package com.weatherly.weather.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenMeteoResponseDto(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val current: OpenMeteoCurrentDto,
    val hourly: OpenMeteoHourlyDto,
    val daily: OpenMeteoDailyDto,
)

@Serializable
data class OpenMeteoCurrentDto(
    val time: String,
    @SerialName("temperature_2m")
    val temperature: Double,
    @SerialName("relative_humidity_2m")
    val humidity: Int,
    @SerialName("apparent_temperature")
    val apparentTemperature: Double,
    @SerialName("weather_code")
    val weatherCode: Int,
    @SerialName("wind_speed_10m")
    val windSpeed: Double,
    @SerialName("pressure_msl")
    val pressure: Double,
    @SerialName("cloud_cover")
    val cloudCover: Int,
)

@Serializable
data class OpenMeteoHourlyDto(
    val time: List<String>,
    @SerialName("temperature_2m")
    val temperatures: List<Double>,
    @SerialName("weather_code")
    val weatherCode: List<Int>,
)

@Serializable
data class OpenMeteoDailyDto(
    val time: List<String>,
    @SerialName("temperature_2m_min")
    val minTemperatures: List<Double>,
    @SerialName("temperature_2m_max")
    val maxTemperatures: List<Double>,
    @SerialName("weather_code")
    val weatherCode: List<Int>,
    @SerialName("precipitation_probability_max")
    val precipitationProbability: List<Int>,
    val sunrise: List<String>,
    val sunset: List<String>,
    @SerialName("uv_index_max")
    val uvIndex: List<Double>,
)

@Serializable
data class OpenMeteoGeocodingResponseDto(
    val results: List<GeocodingResultDto> = emptyList(),
)

@Serializable
data class GeocodingResultDto(
    val id: Long,
    val name: String,
    val country: String? = null,
    val admin1: String? = null,
    val latitude: Double,
    val longitude: Double,
    val timezone: String? = null,
)
