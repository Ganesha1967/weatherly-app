package com.weatherly.weather.viewmodel.home

import com.weatherly.weather.data.preferences.AppTheme

data class HomeUiState(
    val city: String = "Москва",
    val dateStr: String = "",
    val selectedTheme: AppTheme = AppTheme.AMETHYST,
    val currentTemp: Int = 0,
    val tempRange: String = "",
    val weatherType: String = "sun",
    val weatherDescription: String = "",
    val humidity: Int = 0,
    val precipitation: Int = 0,
    val timePeriods: List<TimePeriodUi> = emptyList(),
    val recommendationTitle: String = "Рекомендации",
    val recommendation: RecommendationUi = RecommendationUi(),
    val recommendationText: String = "",
    val recommendationTags: List<String> = emptyList(),
    val dailyForecast: List<DailyForecastUi> = emptyList(),
    val useFahrenheit: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

data class TimePeriodUi(
    val title: String,
    val icon: String,
    val temp: Int,
    val tempDisplay: String,
)

data class RecommendationUi(
    val title: String = "Рекомендации",
    val mainAdvice: String = "",
    val comfortLevel: ComfortLevel = ComfortLevel.MEDIUM,
    val tags: List<String> = emptyList(),
)

enum class ComfortLevel {
    LOW,
    MEDIUM,
    HIGH,
}

data class DailyForecastUi(
    val dayName: String,
    val icon: String,
    val tempMin: Int,
    val tempMax: Int,
    val tempRange: String,
)
