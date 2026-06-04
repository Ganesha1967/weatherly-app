package com.weatherly.weather.domain.usecase

import com.weatherly.weather.domain.model.WeatherData
import com.weatherly.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase
    @Inject
    constructor(
        private val repository: WeatherRepository,
    ) {
        suspend operator fun invoke(
            cityId: String,
            lat: Double,
            lon: Double,
        ): Result<WeatherData> = repository.getWeather(cityId, lat, lon)
    }
