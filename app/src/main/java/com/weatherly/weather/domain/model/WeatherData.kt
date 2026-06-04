package com.weatherly.weather.domain.model

data class WeatherData(
    val city: City,
    val current: CurrentWeather,
    val dailyForecast: List<DailyForecast>,
    val hourlyForecast: List<HourlyForecast>,
)

data class CurrentWeather(
    val temp: Int,
    val tempMin: Int,
    val tempMax: Int,
    val feelsLike: Int,
    val condition: WeatherCondition,
    val description: String,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Double,
    val precipitation: Int,
    val precipitationFloat: Float,
    val uvIndex: Float,
    val cloudCover: Int,
    val sunrise: String,
    val sunset: String,
)

data class CitySearchResult(
    val id: String,
    val name: String,
    val country: String,
    val region: String? = null,
    val lat: Double = 0.0,
    val lon: Double = 0.0,
)

data class DailyForecast(
    val dayName: String,
    val date: String,
    val icon: String,
    val tempMin: Int,
    val tempMax: Int,
    val condition: WeatherCondition,
    val precipitationChance: Int,
)

data class HourlyForecast(
    val time: String,
    val icon: String,
    val temp: Int,
    val precipitationChance: Int,
)

data class City(
    val id: String,
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val timezone: String,
)
