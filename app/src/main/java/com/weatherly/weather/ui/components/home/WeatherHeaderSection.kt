package com.weatherly.weather.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weatherly.weather.R
import com.weatherly.weather.data.preferences.AppTheme
import com.weatherly.weather.ui.components.glassCard
import com.weatherly.weather.ui.theme.BodySmallStyle
import com.weatherly.weather.ui.theme.Weatherly
import com.weatherly.weather.viewmodel.home.HomeUiState

@Composable
fun WeatherHeaderSection(
    state: WeatherHeaderState,
    uiState: HomeUiState,
    modifier: Modifier = Modifier,
) {
    val bgResId =
        when (uiState.selectedTheme) {
            AppTheme.AMETHYST -> R.drawable.weather_bg
            AppTheme.EMERALD -> R.drawable.weather_bg_emerald
            AppTheme.MIDNIGHT -> R.drawable.weather_bg_midnight
        }

    val glowColor = Weatherly.colors.textGlow

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(280.dp),
        contentAlignment = Alignment.Center,
    ) {
        WeatherHeaderContent(
            state = state,
            glowColor = glowColor,
            bgResId = bgResId,
        )
    }
}

@Composable
private fun WeatherHeaderContent(
    state: WeatherHeaderState,
    glowColor: Color,
    bgResId: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .glassCard(20)
                .clip(RoundedCornerShape(20.dp)),
    ) {
        Image(
            painter = painterResource(id = bgResId),
            contentDescription = "Фон блока температуры",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.6f,
        )

        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
        )

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(
                        vertical = 32.dp,
                        horizontal = 16.dp,
                    ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            WeatherTemperatureDisplay(
                weatherType = state.weatherType,
                currentTemp = state.currentTemp,
                glowColor = glowColor,
            )

            Spacer(modifier = Modifier.height(8.dp))

            WeatherDescription(
                description = state.weatherDescription,
                tempRange = state.tempRange,
                precipitation = state.precipitation,
            )
        }
    }
}

@Composable
private fun WeatherTemperatureDisplay(
    weatherType: String,
    currentTemp: Int,
    glowColor: Color,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        WeatherIconWithGlow(
            weatherType = weatherType,
            glowColor = glowColor,
            iconSize = 72.sp,
            containerSize = 80.dp,
        )

        Spacer(modifier = Modifier.padding(end = 8.dp))

        Text(
            text = "$currentTemp°",
            fontSize = 72.sp,
            fontWeight = FontWeight.ExtraLight,
            color = Color.White,
            letterSpacing = (-2).sp,
        )
    }
}

@Composable
private fun WeatherDescription(
    description: String,
    tempRange: String,
    precipitation: Int,
) {
    Text(
        text = description.uppercase(),
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = Color.White,
        letterSpacing = 1.sp,
    )

    Spacer(modifier = Modifier.height(4.dp))

    Text(
        text = "$tempRange  •  💧 $precipitation%",
        style =
            BodySmallStyle.copy(
                color = Weatherly.colors.textMuted,
                fontSize = 12.sp,
            ),
    )
}

@Composable
private fun WeatherIconWithGlow(
    weatherType: String,
    glowColor: Color,
    iconSize: TextUnit = 80.sp,
    containerSize: Dp = 100.dp,
    modifier: Modifier = Modifier,
) {
    Text(
        text = getWeatherIcon(weatherType),
        fontSize = iconSize,
        style =
            TextStyle(
                shadow =
                    Shadow(
                        color = glowColor.copy(alpha = 0.3f),
                        offset = Offset(0f, 0f),
                        blurRadius = 20f,
                    ),
            ),
        modifier = modifier.size(containerSize),
    )
}

@Composable
private fun getWeatherIcon(type: String): String =
    when (type.trim()) {
        "sun" -> "☀️"
        "cloud-sun" -> "⛅"
        "cloud" -> "☁️"
        "rain" -> "🌧️"
        "snow" -> "❄️"
        "fog" -> "🌫️"
        "storm" -> "⛈️"
        else -> "☀️"
    }

data class WeatherHeaderState(
    val currentTemp: Int,
    val tempRange: String,
    val weatherType: String,
    val weatherDescription: String,
    val precipitation: Int,
)
