package com.weatherly.weather.data.preferences

data class AppPreferences(
    val cityId: String = DEFAULTS.CITY_ID,
    val cityName: String = DEFAULTS.CITY_NAME,
    val cityLat: Double = DEFAULTS.CITY_LAT,
    val cityLon: Double = DEFAULTS.CITY_LON,
    val theme: AppTheme = AppTheme.AMETHYST,
    val useFahrenheit: Boolean = false,
    val useKmh: Boolean = false,
    val useHpa: Boolean = false,
    val timeFormat: String = "24",
    val showNotifications: Boolean = true,
    val backgroundSync: Boolean = true,
    val lockScreen: Boolean = false,
    val statusBar: Boolean = true,
    val weatherRadar: Boolean = false,
) {
    object DEFAULTS {
        const val CITY_ID = "524901"
        const val CITY_NAME = "Москва"
        const val CITY_LAT = 55.7558
        const val CITY_LON = 37.6173
    }
}
