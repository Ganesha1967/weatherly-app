package com.weatherly.weather.ui.components.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.weatherly.weather.ui.theme.BodyMediumStyle
import com.weatherly.weather.ui.theme.Weatherly
import com.weatherly.weather.viewmodel.analytics.AnalyticsUiState

@Composable
fun WindPressureSection(
    uiState: AnalyticsUiState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        WeatherDetailCard(
            title = "ВЕТЕР",
            content =
                CardContent(
                    value = uiState.weatherDetails.windSpeed,
                    subtitle = uiState.weatherDetails.windDesc,
                ),
            progress =
                CardProgress(
                    value = uiState.weatherDetails.windProgress,
                    color = Weatherly.colors.waveSecondary,
                ),
            icon = {
                Box(
                    modifier =
                        Modifier
                            .size(28.dp)
                            .background(Weatherly.colors.waveSecondary.copy(alpha = 0.2f), CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("💨", style = BodyMediumStyle)
                }
            },
            modifier = Modifier.weight(1f),
        )
        WeatherDetailCard(
            title = "ДАВЛЕНИЕ",
            content =
                CardContent(
                    value = uiState.weatherDetails.pressure,
                    subtitle = uiState.weatherDetails.pressureDesc,
                ),
            progress =
                CardProgress(
                    value = uiState.weatherDetails.pressureProgress,
                    color = Weatherly.colors.waveSecondary,
                ),
            icon = {
                Box(
                    modifier =
                        Modifier
                            .size(28.dp)
                            .background(Weatherly.colors.wavePrimary.copy(alpha = 0.2f), CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("🌡️", style = BodyMediumStyle)
                }
            },
            modifier = Modifier.weight(1f),
        )
    }
}
