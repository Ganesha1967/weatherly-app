package com.weatherly.weather.viewmodel.search

import com.weatherly.weather.domain.model.CitySearchResult

data class SearchUiState(
    val query: String = "",
    val results: List<CitySearchResult> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedCityId: String? = null,
)
