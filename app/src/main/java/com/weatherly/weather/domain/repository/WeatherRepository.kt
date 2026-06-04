package com.weatherly.weather.domain.repository

import com.weatherly.weather.domain.model.CitySearchResult
import com.weatherly.weather.domain.model.WeatherData

interface WeatherRepository {
    suspend fun getWeather(
        cityId: String,
        lat: Double,
        lon: Double,
    ): Result<WeatherData>

    suspend fun searchCities(query: String): Result<List<CitySearchResult>>
}
