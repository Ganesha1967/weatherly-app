package com.weatherly.weather.data.repositories

import com.weatherly.weather.data.local.RoomWeatherRepository
import com.weatherly.weather.data.mappers.toDomain
import com.weatherly.weather.data.remote.WeatherApiService
import com.weatherly.weather.domain.model.CitySearchResult
import com.weatherly.weather.domain.model.WeatherData
import com.weatherly.weather.domain.repository.WeatherRepository
import kotlinx.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiWeatherRepository
    @Inject
    constructor(
        private val apiService: WeatherApiService,
        private val roomRepo: RoomWeatherRepository,
    ) : WeatherRepository {
        override suspend fun getWeather(
            cityId: String,
            lat: Double,
            lon: Double,
        ): Result<WeatherData> =
            try {
                val dto = apiService.getWeatherForecast(lat, lon)
                val city = roomRepo.getCityById(cityId)

                val weather =
                    dto.toDomain(
                        cityId = cityId,
                        cityName = city?.name.orEmpty(),
                        countryName = city?.country.orEmpty(),
                    )

                roomRepo.cacheWeather(
                    cityId = cityId,
                    data = weather,
                )

                Result.success(weather)
            } catch (e: IOException) {
                val cached = roomRepo.getWeatherCacheData(cityId)

                if (cached != null) {
                    Result.success(cached)
                } else {
                    Result.failure(e)
                }
            }

        override suspend fun searchCities(query: String): Result<List<CitySearchResult>> =
            runCatching {
                apiService
                    .searchCities(query)
                    .results
                    .map { it.toDomain() }
                    .distinctBy { it.name }
            }
    }
