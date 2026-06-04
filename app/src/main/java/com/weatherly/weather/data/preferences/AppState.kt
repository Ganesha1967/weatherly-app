package com.weatherly.weather.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

enum class AppTheme { AMETHYST, EMERALD, MIDNIGHT }

enum class Screen { HOME, SEARCH, ANALYTICS, SETTINGS }

@Singleton
class AppState
    @Inject
    constructor(
        @param:ApplicationContext private val context: Context,
    ) {
        private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

        val prefs: StateFlow<AppPreferences> =
            context.dataStore.data
                .map { it.toAppPreferences() }
                .stateIn(
                    scope = scope,
                    started = SharingStarted.Eagerly,
                    initialValue = AppPreferences(),
                )

        suspend fun update(transform: AppPreferences.() -> AppPreferences) {
            context.dataStore.edit { preferences ->
                val current = preferences.toAppPreferences()
                val updated = current.transform()
                preferences.fromAppPreferences(updated)
            }
        }

        suspend fun selectCity(
            id: String,
            name: String,
            lat: Double,
            lon: Double,
        ) {
            update {
                copy(cityId = id, cityName = name, cityLat = lat, cityLon = lon)
            }
        }
    }
