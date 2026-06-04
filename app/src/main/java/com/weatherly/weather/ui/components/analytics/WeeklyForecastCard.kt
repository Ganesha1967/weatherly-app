package com.weatherly.weather.ui.components.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weatherly.weather.ui.components.glassCard
import com.weatherly.weather.ui.theme.Weatherly
import com.weatherly.weather.viewmodel.analytics.WeeklyForecastUi

@Composable
fun WeeklyForecastCard(
    forecastData: List<WeeklyForecastUi>,
    modifier: Modifier = Modifier,
) {
    if (forecastData.isEmpty()) {
        return
    }

    val minExpectedTemp = -20f
    val maxExpectedTemp = 40f
    val tempRange = maxExpectedTemp - minExpectedTemp

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .glassCard(24)
                .padding(20.dp),
    ) {
        forecastData.forEach { item ->
            ForecastRow(
                item = item,
                minExpectedTemp = minExpectedTemp,
                tempRange = tempRange,
            )

            if (item != forecastData.last()) {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun ForecastRow(
    item: WeeklyForecastUi,
    minExpectedTemp: Float,
    tempRange: Float,
    modifier: Modifier = Modifier,
) {
    val avgTemp = (item.minTemp + item.maxTemp) / 2f
    val progress = ((avgTemp - minExpectedTemp) / tempRange).coerceIn(0f, 1f)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ForecastRowLeading(item = item)

        ForecastProgressBar(
            progress = progress,
            modifier = Modifier.weight(1f),
        )

        Text(
            text = "${item.maxTemp}°",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            modifier = Modifier.width(30.dp),
        )
    }
}

@Composable
private fun ForecastRowLeading(
    item: WeeklyForecastUi,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = item.date.toString(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(alpha = 0.7f),
            )
        }
        Box(
            modifier =
                Modifier
                    .size(20.dp)
                    .padding(horizontal = 2.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text =
                    when (item.weatherType) {
                        "sun" -> "☀️"
                        "cloud-sun" -> "⛅"
                        "cloud" -> "☁️"
                        "rain" -> "🌧️"
                        else -> "☀️"
                    },
                fontSize = 14.sp,
            )
        }

        Text(
            text = "${item.minTemp}°",
            fontSize = 11.sp,
            color = Color.White.copy(alpha = 0.4f),
            modifier = Modifier.width(26.dp),
        )
    }
}

@Composable
private fun ForecastProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .height(10.dp)
                .padding(horizontal = 4.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Weatherly.colors.backgroundPrimary),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth(progress)
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors =
                                listOf(
                                    Weatherly.colors.wavePrimary,
                                    Weatherly.colors.waveSecondary,
                                ),
                        ),
                    ),
        )
    }
}
