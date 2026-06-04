package com.weatherly.weather.utils

import com.weatherly.weather.domain.model.CurrentWeather
import com.weatherly.weather.viewmodel.home.ComfortLevel

object ComfortCalculator {
    fun calculateComfortLevel(current: CurrentWeather): ComfortLevel {
        var score = 5
        score += getTempScore(current.temp)
        score -= (current.precipitationFloat * 3).toInt()
        score -= getWindScore(current.windSpeed)
        score += getHumidityScore(current.humidity)
        score += getUvScore(current.uvIndex)

        return when {
            score >= 7 -> ComfortLevel.HIGH
            score >= 4 -> ComfortLevel.MEDIUM
            else -> ComfortLevel.LOW
        }
    }

    private fun getTempScore(temp: Int): Int =
        when (temp) {
            in 18..22 -> 3
            in 15..25 -> 2
            in 10..30 -> 1
            in 5..35 -> 0
            else -> -2
        }

    private fun getWindScore(windSpeed: Double): Int =
        when {
            windSpeed < 3.0 -> 0
            windSpeed < 7.0 -> 1
            windSpeed < 12.0 -> 2
            else -> 3
        }

    private fun getHumidityScore(humidity: Int): Int =
        when (humidity) {
            in 40..60 -> 1
            in 30..70 -> 0
            else -> -1
        }

    private fun getUvScore(uvIndex: Float): Int =
        when (uvIndex) {
            in 3f..5f -> 1
            in 1f..7f -> 0
            else -> -1
        }
}
