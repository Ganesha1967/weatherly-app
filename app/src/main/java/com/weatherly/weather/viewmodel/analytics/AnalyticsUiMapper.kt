package com.weatherly.weather.viewmodel.analytics

import com.weatherly.weather.data.preferences.AppPreferences
import com.weatherly.weather.domain.model.WeatherData
import com.weatherly.weather.utils.SunriseCalculator
import com.weatherly.weather.utils.UnitConverter
import javax.inject.Inject

class AnalyticsUiMapper
    @Inject
    constructor() {
        fun map(
            data: WeatherData,
            prefs: AppPreferences,
        ): AnalyticsUiState =
            AnalyticsUiState(
                isLoading = false,
                insight =
                    InsightUi(
                        title = "Текущая погода",
                        description =
                            "Температура воздуха составляет " +
                                "${UnitConverter.convertTemperature(data.current.temp, prefs.useFahrenheit)}" +
                                UnitConverter.getTemperatureUnit(prefs.useFahrenheit),
                    ),
                weeklyForecast =
                    data.dailyForecast.map { forecast ->
                        WeeklyForecastUi(
                            day = forecast.dayName,
                            date = forecast.date.substringAfterLast('-').toIntOrNull() ?: 1,
                            minTemp = UnitConverter.convertTemperature(forecast.tempMin, prefs.useFahrenheit),
                            maxTemp = UnitConverter.convertTemperature(forecast.tempMax, prefs.useFahrenheit),
                            weatherType = forecast.condition.uiKey,
                        )
                    },
                weatherDetails =
                    data.current.run {
                        val windSpeedVal = UnitConverter.convertWindSpeed(windSpeed, prefs.useKmh)
                        val pressureVal = UnitConverter.convertPressure(pressure, prefs.useHpa)

                        WeatherDetailsUi(
                            windSpeed =
                                "${UnitConverter.formatWindSpeed(windSpeed, prefs.useKmh)} " +
                                    UnitConverter.getWindUnit(prefs.useKmh),
                            windDesc = if (windSpeedVal < 5.0) "Слабый" else "Умеренный",
                            windProgress = (windSpeedVal / 20.0).toFloat().coerceIn(0f, 1f),
                            pressure = "$pressureVal ${UnitConverter.getPressureUnit(prefs.useHpa)}",
                            pressureDesc = if (pressure in 1000..1020) "Норма" else "Отклонение",
                            pressureProgress = ((pressure - 980) / 80f).coerceIn(0f, 1f),
                            uvIndex = "${uvIndex.toInt()} (Низ.)",
                            uvProgress = (uvIndex / 10f).coerceIn(0f, 1f),
                            precipitation = "$precipitation мм",
                            precipProgress = (precipitation.toFloat() / 10f).coerceIn(0f, 1f),
                        )
                    },
                sunriseSunset =
                    data.current.run {
                        SunriseSunsetUi(
                            sunrise = sunrise,
                            sunset = sunset,
                            noon = SunriseCalculator.calculateNoonTime(sunrise, sunset),
                            duration = SunriseCalculator.calculateDaylightDuration(sunrise, sunset),
                            progress = SunriseCalculator.calculateDayProgress(sunrise, sunset, data.city.timezone),
                        )
                    },
            )
    }
