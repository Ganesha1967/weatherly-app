package com.weatherly.weather.ui.components.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.weatherly.weather.data.preferences.AppState
import com.weatherly.weather.data.preferences.AppTheme
import com.weatherly.weather.ui.components.glassCard
import com.weatherly.weather.ui.theme.AmethystActionAccent
import com.weatherly.weather.ui.theme.EmeraldActionAccent
import com.weatherly.weather.ui.theme.LabelSmallStyle
import com.weatherly.weather.ui.theme.MidnightActionAccent
import com.weatherly.weather.ui.theme.Weatherly
import kotlinx.coroutines.launch

@Composable
fun ThemeSection(
    appState: AppState,
    modifier: Modifier = Modifier,
) {
    val prefs = appState.prefs.collectAsState().value
    val scope = rememberCoroutineScope()

    val mutedColor = Weatherly.colors.textMuted

    val amethystColor = AmethystActionAccent
    val emeraldColor = EmeraldActionAccent
    val midnightColor = MidnightActionAccent

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .glassCard(24)
                .padding(24.dp),
    ) {
        Text(
            text = "ВЫБОР ТЕМЫ",
            style = LabelSmallStyle,
            color = mutedColor,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ThemeCircle(
                color = amethystColor,
                isActive = prefs.theme == AppTheme.AMETHYST,
                onClick = {
                    scope.launch {
                        appState.update {
                            copy(theme = AppTheme.AMETHYST)
                        }
                    }
                },
            )

            ThemeCircle(
                color = emeraldColor,
                isActive = prefs.theme == AppTheme.EMERALD,
                onClick = {
                    scope.launch {
                        appState.update {
                            copy(theme = AppTheme.EMERALD)
                        }
                    }
                },
            )

            ThemeCircle(
                color = midnightColor,
                isActive = prefs.theme == AppTheme.MIDNIGHT,
                onClick = {
                    scope.launch {
                        appState.update {
                            copy(theme = AppTheme.MIDNIGHT)
                        }
                    }
                },
            )
        }
    }
}
