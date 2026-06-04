package com.weatherly.weather.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.weatherly.weather.data.preferences.AppState
import com.weatherly.weather.ui.components.settings.AboutSection
import com.weatherly.weather.ui.components.settings.ThemeSection
import com.weatherly.weather.ui.components.settings.UnitsSection

@Composable
fun SettingsScreen(
    appState: AppState,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ThemeSection(appState = appState)
        UnitsSection(appState = appState)
        AboutSection()
    }
}
