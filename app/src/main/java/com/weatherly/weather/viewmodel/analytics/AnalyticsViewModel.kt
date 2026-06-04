package com.weatherly.weather.viewmodel.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherly.weather.data.preferences.AppState
import com.weatherly.weather.domain.model.WeatherData
import com.weatherly.weather.domain.usecase.AnalyticsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel
    @Inject
    constructor(
        private val observeAnalyticsUseCase: AnalyticsUseCase,
        private val appState: AppState,
        private val mapper: AnalyticsUiMapper,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(AnalyticsUiState(isLoading = true))
        val uiState: StateFlow<AnalyticsUiState> = _uiState.asStateFlow()
        private var lastWeatherData: WeatherData? = null

        init {
            viewModelScope.launch {
                appState.prefs.collect { prefs ->
                    lastWeatherData?.let { data ->
                        _uiState.value = mapper.map(data, prefs)
                    }
                }
            }

            viewModelScope.launch {
                appState.prefs
                    .map { it.cityId }
                    .distinctUntilChanged()
                    .collect { cityId ->
                        loadAnalytics(cityId)
                    }
            }
        }

        fun refreshAnalytics() {
            loadAnalytics(appState.prefs.value.cityId)
        }

        private fun loadAnalytics(cityId: String) {
            viewModelScope.launch {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                        errorMessage = null,
                    )
                }
                val prefs = appState.prefs.value

                observeAnalyticsUseCase(cityId, prefs.cityLat, prefs.cityLon).collect { result ->
                    result
                        .onSuccess { data ->
                            lastWeatherData = data
                            _uiState.value = mapper.map(data, prefs).copy(isLoading = false)
                        }.onFailure { error ->
                            _uiState.update {
                                it.copy(isLoading = false, errorMessage = "Ошибка: ${error.message}")
                            }
                        }
                }
            }
        }
    }
