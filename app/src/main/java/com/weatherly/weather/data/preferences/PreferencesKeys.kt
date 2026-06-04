package com.weatherly.weather.data.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val CITY_ID = stringPreferencesKey("cityId")
    val CITY_NAME = stringPreferencesKey("cityName")
    val CITY_LAT = doublePreferencesKey("cityLat")
    val CITY_LON = doublePreferencesKey("cityLon")

    val THEME = stringPreferencesKey("theme")

    val USE_FAHRENHEIT = booleanPreferencesKey("useFahrenheit")
    val USE_KMH = booleanPreferencesKey("useKmh")
    val USE_HPA = booleanPreferencesKey("useHpa")
}
