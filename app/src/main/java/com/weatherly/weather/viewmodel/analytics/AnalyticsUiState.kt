package com.weatherly.weather.viewmodel.analytics

import com.weatherly.weather.domain.model.WeatherData

data class AnalyticsUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val insight: InsightUi = InsightUi(),
    val weeklyForecast: List<WeeklyForecastUi> = emptyList(),
    val weatherDetails: WeatherDetailsUi = WeatherDetailsUi(),
    val sunriseSunset: SunriseSunsetUi = SunriseSunsetUi(),
    val weatherData: WeatherData? = null,
)

data class InsightUi(
    val title: String = "",
    val description: String = "",
    val iconKey: String = "ic_analytics",
)

data class WeeklyForecastUi(
    val day: String,
    val date: Int,
    val minTemp: Int,
    val maxTemp: Int,
    val weatherType: String,
)

data class WeatherDetailsUi(
    val windSpeed: String = "",
    val windDesc: String = "",
    val windProgress: Float = 0f,
    val pressure: String = "",
    val pressureDesc: String = "",
    val pressureProgress: Float = 0f,
    val uvIndex: String = "",
    val uvProgress: Float = 0f,
    val precipitation: String = "",
    val precipProgress: Float = 0f,
)

data class SunriseSunsetUi(
    val sunrise: String = "",
    val sunset: String = "",
    val noon: String = "",
    val duration: String = "",
    val progress: Float = 0.7f,
)
