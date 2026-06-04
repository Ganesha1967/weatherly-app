package com.weatherly.weather.utils

import java.util.Locale

object UnitConverter {
    fun convertTemperature(
        celsius: Int,
        useFahrenheit: Boolean,
    ): Int =
        if (useFahrenheit) {
            (celsius * 9 / 5) + 32
        } else {
            celsius
        }

    fun convertWindSpeed(
        mps: Double,
        useKmh: Boolean,
    ): Double =
        if (useKmh) {
            mps * 3.6
        } else {
            mps
        }

    fun convertPressure(
        hpa: Int,
        useHpa: Boolean,
    ): Int =
        if (useHpa) {
            hpa
        } else {
            (hpa * 0.750062).toInt()
        }

    fun formatWindSpeed(
        mps: Double,
        useKmh: Boolean,
    ): String =
        "%.1f".format(
            Locale.getDefault(),
            convertWindSpeed(mps, useKmh),
        )

    fun getTemperatureUnit(useFahrenheit: Boolean): String =
        if (useFahrenheit) {
            "F"
        } else {
            "C"
        }

    fun getWindUnit(useKmh: Boolean): String =
        if (useKmh) {
            "км/ч"
        } else {
            "м/с"
        }

    fun getPressureUnit(useHpa: Boolean): String =
        if (useHpa) {
            "гПа"
        } else {
            "мм рт.ст."
        }
}
