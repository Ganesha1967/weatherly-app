package com.weatherly.weather.domain.model

enum class WeatherCondition(
    val wmoCode: Int,
    val icon: String,
    val uiKey: String,
) {
    CLEAR(0, "☀️", "sun"),
    PARTLY_CLOUDY(1, "⛅", "cloud-sun"),
    CLOUDY(2, "☁️", "cloud"),
    RAIN(3, "🌧️", "rain"),
    SNOW(4, "❄️", "snow"),
    FOG(5, "🌫️", "fog"),
    STORM(6, "⛈️", "storm"), ;

    companion object {
        fun fromWmoCode(code: Int): WeatherCondition = entries.find { it.wmoCode == code } ?: CLEAR
    }
}
