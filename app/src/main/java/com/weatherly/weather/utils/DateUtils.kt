package com.weatherly.weather.utils

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val navigationFormatter =
    DateTimeFormatter.ofPattern("dd.MM.yy, EEE", Locale.forLanguageTag("ru"))

private val weatherFormatter =
    DateTimeFormatter.ofPattern("d MMM, EEE", Locale.forLanguageTag("ru"))

fun formatNavigationDate(): String =
    ZonedDateTime
        .now()
        .format(navigationFormatter)
        .lowercase()

fun formatWeatherDate(timezone: String = "UTC"): String {
    val zoneId =
        runCatching { ZoneId.of(timezone) }
            .getOrDefault(ZoneId.of("UTC"))

    return ZonedDateTime
        .now(zoneId)
        .format(weatherFormatter)
}
