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
import com.weatherly.weather.ui.theme.BodyMediumStyle
import com.weatherly.weather.ui.theme.BodySmallStyle
import com.weatherly.weather.ui.theme.LabelSmallStyle
import com.weatherly.weather.ui.theme.Weatherly
import com.weatherly.weather.viewmodel.analytics.SunriseSunsetUi

@Composable
fun SunriseSunsetCard(
    data: SunriseSunsetUi,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .glassCard(24)
                .padding(20.dp),
    ) {
        CardHeader(data = data)

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TimelineProgress(progress = data.progress)

            Spacer(modifier = Modifier.height(12.dp))

            TimeLabels(data = data)
        }
    }
}

@Composable
private fun CardHeader(
    data: SunriseSunsetUi,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "ВОСХОД И ЗАКАТ",
            style = LabelSmallStyle,
            color = Weatherly.colors.textMuted,
        )
        Text(
            text = "Световой день: ${data.duration}",
            fontSize = 10.sp,
            lineHeight = 14.sp,
            color = Color.White.copy(alpha = 0.5f),
        )
    }
}

@Composable
private fun TimelineProgress(
    progress: Float,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = "☀️", fontSize = 24.sp)
            Text(text = "🌙", fontSize = 24.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Weatherly.colors.backgroundPrimary),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth(progress.coerceIn(0f, 1f))
                        .height(12.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            Brush.horizontalGradient(
                                colors =
                                    listOf(
                                        Weatherly.colors.actionAccent,
                                        Weatherly.colors.textGlow,
                                    ),
                            ),
                        ),
            )
        }
    }
}

@Composable
private fun TimeLabels(
    data: SunriseSunsetUi,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = data.sunrise,
            style = BodyMediumStyle.copy(fontWeight = FontWeight.SemiBold),
            color = Color.White,
        )
        Text(
            text = data.noon,
            style = BodySmallStyle,
            color = Color.White.copy(alpha = 0.6f),
        )
        Text(
            text = data.sunset,
            style = BodyMediumStyle.copy(fontWeight = FontWeight.SemiBold),
            color = Color.White,
        )
    }
}
