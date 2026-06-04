package com.weatherly.weather.data.preferences

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences

fun Preferences.toAppPreferences(): AppPreferences =
    AppPreferences(
        cityId = this[PreferencesKeys.CITY_ID] ?: AppPreferences.DEFAULTS.CITY_ID,
        cityName = this[PreferencesKeys.CITY_NAME] ?: AppPreferences.DEFAULTS.CITY_NAME,
        cityLat = this[PreferencesKeys.CITY_LAT] ?: AppPreferences.DEFAULTS.CITY_LAT,
        cityLon = this[PreferencesKeys.CITY_LON] ?: AppPreferences.DEFAULTS.CITY_LON,
        theme = this[PreferencesKeys.THEME]?.toTheme() ?: AppTheme.AMETHYST,
        useFahrenheit = this[PreferencesKeys.USE_FAHRENHEIT] ?: false,
        useKmh = this[PreferencesKeys.USE_KMH] ?: false,
        useHpa = this[PreferencesKeys.USE_HPA] ?: false,
    )

fun MutablePreferences.fromAppPreferences(p: AppPreferences) {
    this[PreferencesKeys.CITY_ID] = p.cityId
    this[PreferencesKeys.CITY_NAME] = p.cityName
    this[PreferencesKeys.CITY_LAT] = p.cityLat
    this[PreferencesKeys.CITY_LON] = p.cityLon
    this[PreferencesKeys.THEME] = p.theme.name
    this[PreferencesKeys.USE_FAHRENHEIT] = p.useFahrenheit
    this[PreferencesKeys.USE_KMH] = p.useKmh
    this[PreferencesKeys.USE_HPA] = p.useHpa
}

internal fun String.toTheme(): AppTheme = AppTheme.entries.firstOrNull { it.name == this } ?: AppTheme.AMETHYST
