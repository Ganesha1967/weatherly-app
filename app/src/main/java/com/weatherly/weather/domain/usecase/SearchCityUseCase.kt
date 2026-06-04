package com.weatherly.weather.domain.usecase

import com.weatherly.weather.domain.model.CitySearchResult
import com.weatherly.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class SearchCityUseCase
    @Inject
    constructor(
        private val repository: WeatherRepository,
    ) {
        suspend operator fun invoke(query: String): Result<List<CitySearchResult>> {
            if (query.isBlank()) {
                return Result.success(emptyList())
            }

            return repository.searchCities(query)
        }
    }
