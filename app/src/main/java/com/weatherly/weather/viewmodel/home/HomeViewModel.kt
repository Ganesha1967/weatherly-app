package com.weatherly.weather.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherly.weather.data.local.RoomWeatherRepository
import com.weatherly.weather.data.preferences.AppPreferences
import com.weatherly.weather.data.preferences.AppState
import com.weatherly.weather.domain.model.CurrentWeather
import com.weatherly.weather.domain.model.DailyForecast
import com.weatherly.weather.domain.model.HourlyForecast
import com.weatherly.weather.domain.model.WeatherData
import com.weatherly.weather.domain.usecase.GetWeatherUseCase
import com.weatherly.weather.utils.ComfortCalculator
import com.weatherly.weather.utils.UnitConverter
import com.weatherly.weather.utils.formatWeatherDate
import com.weatherly.weather.utils.getClothingTags
import com.weatherly.weather.utils.getMainAdvice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val appState: AppState,
        private val getWeatherUseCase: GetWeatherUseCase,
        private val roomRepo: RoomWeatherRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
        val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

        private val currentWeatherData = MutableStateFlow<WeatherData?>(null)

        init {
            viewModelScope.launch {
                combine(currentWeatherData, appState.prefs) { data, prefs -> data to prefs }
                    .collect { (data, prefs) ->
                        if (data != null) {
                            _uiState.value = mapToUiState(data, prefs)
                        }
                    }
            }

            viewModelScope.launch {
                val cityId = appState.prefs.value.cityId
                val cached = roomRepo.getWeatherCacheData(cityId)
                if (cached != null && currentWeatherData.value == null) {
                    currentWeatherData.value = cached
                }
            }

            viewModelScope.launch {
                appState.prefs
                    .map { it.cityId }
                    .distinctUntilChanged()
                    .collect { newCityId ->
                        currentWeatherData.value = null
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                errorMessage = null,
                                city = "",
                                currentTemp = 0,
                                tempRange = "",
                                weatherDescription = "",
                            )
                        }
                        loadWeather(newCityId)
                    }
            }
        }

        fun refreshWeather() {
            loadWeather(appState.prefs.value.cityId)
        }

        fun loadWeather(cityId: String) {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                val prefs = appState.prefs.value

                getWeatherUseCase(cityId, prefs.cityLat, prefs.cityLon)
                    .onSuccess { data ->
                        currentWeatherData.value = data
                        _uiState.update { it.copy(isLoading = false) }
                    }.onFailure { e ->
                        _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
                    }
            }
        }

        private fun mapToUiState(
            data: WeatherData,
            prefs: AppPreferences,
        ): HomeUiState {
            val current = data.current
            val isF = prefs.useFahrenheit

            return HomeUiState(
                city = data.city.name,
                dateStr = formatWeatherDate(data.city.timezone),
                selectedTheme = prefs.theme,
                currentTemp = UnitConverter.convertTemperature(current.temp, isF),
                tempRange = buildTempRange(current.tempMin, current.tempMax, isF),
                weatherType = current.condition.uiKey,
                weatherDescription = current.description,
                humidity = current.humidity,
                precipitation = current.precipitation,
                timePeriods = mapTimePeriods(data.hourlyForecast, isF),
                recommendation = buildRecommendations(current),
                dailyForecast = mapDailyForecast(data.dailyForecast, isF),
                useFahrenheit = isF,
                isLoading = false,
                errorMessage = null,
            )
        }

        private fun buildTempRange(
            min: Int,
            max: Int,
            isFahrenheit: Boolean,
        ): String {
            val minConverted = UnitConverter.convertTemperature(min, isFahrenheit)
            val maxConverted = UnitConverter.convertTemperature(max, isFahrenheit)
            return "$minConverted° / $maxConverted°"
        }

        private fun mapTimePeriods(
            hourlyForecast: List<HourlyForecast>,
            isFahrenheit: Boolean,
        ): List<TimePeriodUi> {
            val tempUnit = UnitConverter.getTemperatureUnit(isFahrenheit)

            val periods =
                hourlyForecast
                    .take(24)
                    .groupBy { getPeriod(it.time) }

            return listOf(
                "Утро" to periods["morning"],
                "День" to periods["afternoon"],
                "Вечер" to periods["evening"],
                "Ночь" to periods["night"],
            ).mapNotNull { (title, hours) ->
                if (hours.isNullOrEmpty()) {
                    return@mapNotNull null
                }

                val avgTemp = hours.map { it.temp }.average().toInt()
                val convertedTemp =
                    UnitConverter.convertTemperature(
                        avgTemp,
                        isFahrenheit,
                    )

                val icon =
                    when (title) {
                        "Утро" -> "🌄"
                        "День" -> "☀️"
                        "Вечер" -> "🌤️"
                        else -> "🌙"
                    }

                TimePeriodUi(
                    title = title,
                    icon = icon,
                    temp = convertedTemp,
                    tempDisplay = "$convertedTemp°$tempUnit",
                )
            }
        }

        private fun getPeriod(time: String): String {
            val hour = time.substringBefore(":").toIntOrNull() ?: return "night"

            return when (hour) {
                in 6..12 -> "morning"
                in 13..17 -> "afternoon"
                in 18..23 -> "evening"
                else -> "night"
            }
        }

        private fun mapDailyForecast(
            daily: List<DailyForecast>,
            isFahrenheit: Boolean,
        ): List<DailyForecastUi> =
            daily.map { f ->
                val minT = UnitConverter.convertTemperature(f.tempMin, isFahrenheit)
                val maxT = UnitConverter.convertTemperature(f.tempMax, isFahrenheit)

                DailyForecastUi(
                    f.dayName,
                    f.condition.uiKey,
                    minT,
                    maxT,
                    "$minT°/$maxT°",
                )
            }

        private fun buildRecommendations(current: CurrentWeather): RecommendationUi =
            RecommendationUi(
                title = "Рекомендации",
                mainAdvice = getMainAdvice(current),
                comfortLevel = ComfortCalculator.calculateComfortLevel(current),
                tags = getClothingTags(current.temp, current),
            )
    }
