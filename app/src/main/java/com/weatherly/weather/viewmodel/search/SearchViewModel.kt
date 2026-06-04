package com.weatherly.weather.viewmodel.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherly.weather.data.local.RoomWeatherRepository
import com.weatherly.weather.data.preferences.AppState
import com.weatherly.weather.domain.model.CitySearchResult
import com.weatherly.weather.domain.usecase.SearchCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
    @Inject
    constructor(
        private val appState: AppState,
        private val searchCityUseCase: SearchCityUseCase,
        private val roomRepo: RoomWeatherRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(SearchUiState())
        val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()
        val favoritesFlow: StateFlow<List<CitySearchResult>> =
            roomRepo.favoritesFlow

        fun updateQuery(query: String) {
            _uiState.update { it.copy(query = query) }
            searchCities(query)
        }

        fun selectCityByName(cityName: String) {
            val city = _uiState.value.results.firstOrNull { it.name == cityName } ?: return
            selectCity(city)
        }

        fun selectFavoriteCity(city: CitySearchResult) {
            selectCity(city)
        }

        private fun selectCity(city: CitySearchResult) {
            viewModelScope.launch {
                appState.selectCity(city.id, city.name, city.lat, city.lon)
            }
            _uiState.update {
                it.copy(selectedCityId = city.id)
            }
        }

        fun toggleFavorite(city: CitySearchResult) {
            viewModelScope.launch {
                val isFavorite = roomRepo.favoritesFlow.value.any { it.id == city.id }

                if (isFavorite) {
                    roomRepo.removeFavorite(city.id)
                } else {
                    roomRepo.addFavorite(city, city.lat, city.lon, "UTC")
                }
            }
        }

        fun clearSearch() {
            _uiState.update {
                it.copy(
                    query = "",
                    results = emptyList(),
                    selectedCityId = null,
                )
            }
        }

        private fun searchCities(query: String) {
            viewModelScope.launch {
                if (query.isBlank()) {
                    _uiState.update {
                        it.copy(
                            results = emptyList(),
                            isLoading = false,
                        )
                    }
                    return@launch
                }

                _uiState.update {
                    it.copy(
                        isLoading = true,
                        errorMessage = null,
                    )
                }

                searchCityUseCase(query)
                    .onSuccess { cities ->
                        _uiState.update {
                            it.copy(
                                results = cities,
                                isLoading = false,
                            )
                        }
                    }.onFailure { error ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = error.localizedMessage ?: "Ошибка поиска",
                            )
                        }
                    }
            }
        }
    }
