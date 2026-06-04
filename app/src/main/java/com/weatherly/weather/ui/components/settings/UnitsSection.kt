package com.weatherly.weather.ui.components.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.weatherly.weather.data.preferences.AppState
import com.weatherly.weather.ui.components.glassCard
import com.weatherly.weather.ui.theme.LabelSmallStyle
import com.weatherly.weather.ui.theme.Weatherly
import kotlinx.coroutines.launch

@Composable
fun UnitsSection(
    appState: AppState,
    modifier: Modifier = Modifier,
) {
    val prefs = appState.prefs.collectAsState().value
    val scope = rememberCoroutineScope()

    val mutedColor = Weatherly.colors.textMuted

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .glassCard(24)
                .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Text(
            text = "ЕДИНИЦЫ ИЗМЕРЕНИЯ",
            style = LabelSmallStyle,
            color = mutedColor,
        )

        SettingSegmentedControl(
            title = "Температура",
            options = listOf("°C", "°F"),
            selected = if (prefs.useFahrenheit) 1 else 0,
            onOptionSelected = {
                scope.launch {
                    appState.update {
                        copy(useFahrenheit = !useFahrenheit)
                    }
                }
            },
        )

        SettingSegmentedControl(
            title = "Давление",
            options = listOf("гПа", "мм рт. ст."),
            selected = if (prefs.useHpa) 0 else 1,
            onOptionSelected = {
                scope.launch {
                    appState.update {
                        copy(useHpa = !useHpa)
                    }
                }
            },
        )

        SettingSegmentedControl(
            title = "Скорость ветра",
            options = listOf("км/ч", "м/с"),
            selected = if (prefs.useKmh) 0 else 1,
            onOptionSelected = {
                scope.launch {
                    appState.update {
                        copy(useKmh = !useKmh)
                    }
                }
            },
        )
    }
}
