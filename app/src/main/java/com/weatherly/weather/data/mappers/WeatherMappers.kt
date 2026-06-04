package com.weatherly.weather.data.mappers

import com.weatherly.weather.data.remote.GeocodingResultDto
import com.weatherly.weather.data.remote.OpenMeteoResponseDto
import com.weatherly.weather.domain.model.City
import com.weatherly.weather.domain.model.CitySearchResult
import com.weatherly.weather.domain.model.CurrentWeather
import com.weatherly.weather.domain.model.DailyForecast
import com.weatherly.weather.domain.model.HourlyForecast
import com.weatherly.weather.domain.model.WeatherCondition
import com.weatherly.weather.domain.model.WeatherData
import java.time.LocalDate

private val DAYS_OF_WEEK =
    arrayOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")

fun GeocodingResultDto.toDomain(): CitySearchResult =
    CitySearchResult(
        id = id.toString(),
        name = name,
        country = country.orEmpty(),
        region = admin1.orEmpty(),
        lat = latitude,
        lon = longitude,
    )

fun OpenMeteoResponseDto.toDomain(
    cityId: String,
    cityName: String,
    countryName: String,
): WeatherData {
    val currentCondition = mapWmoCodeToCondition(current.weatherCode)

    val precipitation = daily.precipitationProbability.firstOrNull() ?: 0

    val currentWeather =
        CurrentWeather(
            temp = current.temperature.toInt(),
            tempMin = daily.minTemperatures.firstOrNull()?.toInt() ?: (current.temperature.toInt() - 3),
            tempMax = daily.maxTemperatures.firstOrNull()?.toInt() ?: (current.temperature.toInt() + 3),
            feelsLike = current.apparentTemperature.toInt(),
            condition = currentCondition,
            description = mapConditionToDescription(currentCondition),
            humidity = current.humidity,
            pressure = current.pressure.toInt(),
            windSpeed = current.windSpeed,
            precipitation = precipitation,
            precipitationFloat = precipitation / 100f,
            uvIndex = daily.uvIndex.firstOrNull()?.toFloat() ?: 0f,
            cloudCover = current.cloudCover,
            sunrise = formatIsoTime(daily.sunrise.firstOrNull()),
            sunset = formatIsoTime(daily.sunset.firstOrNull()),
        )

    val dailyForecast =
        daily.time.mapIndexed { index, date ->
            val condition =
                mapWmoCodeToCondition(daily.weatherCode[index])

            DailyForecast(
                dayName = parseDayOfWeek(date),
                date = date,
                icon = condition.uiKey,
                tempMin = daily.minTemperatures[index].toInt(),
                tempMax = daily.maxTemperatures[index].toInt(),
                condition = condition,
                precipitationChance = daily.precipitationProbability[index],
            )
        }

    val hourlyForecast =
        hourly.time.mapIndexed { index, time ->
            val condition =
                mapWmoCodeToCondition(hourly.weatherCode[index])

            HourlyForecast(
                time = formatIsoTime(time),
                icon = condition.uiKey,
                temp = hourly.temperatures[index].toInt(),
                precipitationChance = 0,
            )
        }

    return WeatherData(
        city =
            City(
                id = cityId,
                name = cityName,
                country = countryName,
                lat = latitude,
                lon = longitude,
                timezone = timezone,
            ),
        current = currentWeather,
        dailyForecast = dailyForecast,
        hourlyForecast = hourlyForecast,
    )
}

private fun mapWmoCodeToCondition(code: Int): WeatherCondition =
    when (code) {
        0 -> WeatherCondition.CLEAR
        1, 2, 3 -> WeatherCondition.PARTLY_CLOUDY
        45, 48 -> WeatherCondition.FOG
        51, 53, 55, 61, 63, 65, 80, 81, 82 -> WeatherCondition.RAIN
        71, 73, 75, 85, 86 -> WeatherCondition.SNOW
        95, 96, 99 -> WeatherCondition.STORM
        else -> WeatherCondition.CLOUDY
    }

private fun mapConditionToDescription(condition: WeatherCondition): String =
    when (condition) {
        WeatherCondition.CLEAR -> "Ясно"
        WeatherCondition.PARTLY_CLOUDY -> "Переменная облачность"
        WeatherCondition.CLOUDY -> "Облачно"
        WeatherCondition.RAIN -> "Дождливо"
        WeatherCondition.SNOW -> "Снег"
        WeatherCondition.FOG -> "Туман"
        WeatherCondition.STORM -> "Гроза"
    }

private fun formatIsoTime(value: String?): String {
    if (value == null || value.length < 16) {
        return "00:00"
    }

    return value.substring(11, 16)
}

private fun parseDayOfWeek(dateStr: String): String =
    try {
        DAYS_OF_WEEK[LocalDate.parse(dateStr).dayOfWeek.ordinal]
    } catch (_: Exception) {
        "--"
    }
