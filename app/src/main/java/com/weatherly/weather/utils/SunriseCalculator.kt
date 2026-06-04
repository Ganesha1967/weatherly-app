package com.weatherly.weather.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.Locale

object SunriseCalculator {
    fun calculateDayProgress(
        sunrise: String,
        sunset: String,
        timezone: String,
    ): Float {
        val zone =
            runCatching { TimeZone.of(timezone) }
                .getOrDefault(TimeZone.UTC)

        val riseMinutes = sunrise.toMinutes()
        val setMinutes = sunset.toMinutes()

        if (riseMinutes == null || setMinutes == null) {
            return 0.7f
        }

        val now = Clock.System.now().toLocalDateTime(zone)
        val currentMinutes = now.hour * 60 + now.minute

        return when {
            currentMinutes <= riseMinutes -> {
                0f
            }

            currentMinutes >= setMinutes -> {
                1f
            }

            else -> {
                (
                    (currentMinutes - riseMinutes).toFloat() /
                        (setMinutes - riseMinutes)
                ).coerceIn(0f, 1f)
            }
        }
    }

    fun calculateNoonTime(
        sunrise: String,
        sunset: String,
    ): String {
        val riseMinutes = sunrise.toMinutes()
        val setMinutes = sunset.toMinutes()

        if (riseMinutes == null || setMinutes == null) {
            return "Полдень 12:45"
        }

        val noonMinutes = (riseMinutes + setMinutes) / 2

        return "Полдень %02d:%02d".format(
            Locale.getDefault(),
            noonMinutes / 60,
            noonMinutes % 60,
        )
    }

    fun calculateDaylightDuration(
        sunrise: String,
        sunset: String,
    ): String {
        val riseMinutes = sunrise.toMinutes()
        val setMinutes = sunset.toMinutes()

        if (riseMinutes == null || setMinutes == null) {
            return "~15 ч 40 мин"
        }

        val duration = setMinutes - riseMinutes

        return "~${duration / 60} ч ${duration % 60} мин"
    }

    private fun String.toMinutes(): Int? {
        val parts = split(":")
        if (parts.size != 2) return null

        val hours = parts[0].toIntOrNull()
        val minutes = parts[1].toIntOrNull()

        val result =
            if (hours != null && minutes != null) {
                hours * 60 + minutes
            } else {
                null
            }

        return result
    }
}
