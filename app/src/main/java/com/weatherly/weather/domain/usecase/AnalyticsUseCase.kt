package com.weatherly.weather.domain.usecase

import com.weatherly.weather.data.local.RoomWeatherRepository
import com.weatherly.weather.domain.model.WeatherData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AnalyticsUseCase
    @Inject
    constructor(
        private val getWeatherUseCase: GetWeatherUseCase,
        private val roomRepo: RoomWeatherRepository,
    ) {
        operator fun invoke(
            cityId: String,
            lat: Double,
            lon: Double,
        ): Flow<Result<WeatherData>> =
            flow {
                roomRepo.getWeatherCacheData(cityId)?.let { cachedData ->
                    emit(Result.success(cachedData))
                }

                val networkResult = getWeatherUseCase(cityId, lat, lon)

                networkResult.onSuccess { freshData ->
                    roomRepo.cacheWeather(cityId, freshData)
                }

                emit(networkResult)
            }
    }
