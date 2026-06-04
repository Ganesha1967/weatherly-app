package com.weatherly.weather.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.weatherly.weather.data.preferences.AppTheme

class WeatherlyColors(
    val backgroundDeepest: Color,
    val backgroundPrimary: Color,
    val backgroundCard: Color,
    val gradientHeaderStart: Color,
    val textAccentPrimary: Color,
    val textGlow: Color,
    val textMuted: Color,
    val actionAccent: Color,
    val glassSurface: Color,
    val glassStroke: Color,
    val wavePrimary: Color,
    val waveSecondary: Color,
)

private val AmethystColors =
    WeatherlyColors(
        backgroundDeepest = AmethystBackgroundDeepest,
        backgroundPrimary = AmethystBackgroundPrimary,
        backgroundCard = AmethystBackgroundCard,
        gradientHeaderStart = AmethystGradientHeaderStart,
        textAccentPrimary = AmethystTextAccentPrimary,
        textGlow = AmethystTextGlow,
        textMuted = AmethystTextMuted,
        actionAccent = AmethystActionAccent,
        glassSurface = AmethystGlassSurface.copy(alpha = 0.4f),
        glassStroke = AmethystGlassStroke.copy(alpha = 0.5f),
        wavePrimary = AmethystWavePrimary,
        waveSecondary = AmethystWaveSecondary,
    )

private val EmeraldColors =
    WeatherlyColors(
        backgroundDeepest = EmeraldBackgroundDeepest,
        backgroundPrimary = EmeraldBackgroundPrimary,
        backgroundCard = EmeraldBackgroundCard,
        gradientHeaderStart = EmeraldGradientHeaderStart,
        textAccentPrimary = EmeraldTextAccentPrimary,
        textGlow = EmeraldTextGlow,
        textMuted = EmeraldTextMuted,
        actionAccent = EmeraldActionAccent,
        glassSurface = EmeraldGlassSurface.copy(alpha = 0.4f),
        glassStroke = EmeraldGlassStroke.copy(alpha = 0.5f),
        wavePrimary = EmeraldWavePrimary,
        waveSecondary = EmeraldWaveSecondary,
    )

private val MidnightColors =
    WeatherlyColors(
        backgroundDeepest = MidnightBackgroundDeepest,
        backgroundPrimary = MidnightBackgroundPrimary,
        backgroundCard = MidnightBackgroundCard,
        gradientHeaderStart = MidnightGradientHeaderStart,
        textAccentPrimary = MidnightTextAccentPrimary,
        textGlow = MidnightTextGlow,
        textMuted = MidnightTextMuted,
        actionAccent = MidnightActionAccent,
        glassSurface = MidnightGlassSurface.copy(alpha = 0.4f),
        glassStroke = MidnightGlassStroke.copy(alpha = 0.5f),
        wavePrimary = MidnightWavePrimary,
        waveSecondary = MidnightWaveSecondary,
    )

private val LocalWeatherlyColors =
    staticCompositionLocalOf<WeatherlyColors> {
        error("No colors provided")
    }

object Weatherly {
    val colors: WeatherlyColors
        @Composable @ReadOnlyComposable
        get() = LocalWeatherlyColors.current
}

val localTheme = mutableStateOf(AppTheme.AMETHYST)

@Composable
fun WeatherlyTheme(
    theme: AppTheme = localTheme.value,
    content: @Composable () -> Unit,
) {
    val colors =
        if (theme == AppTheme.AMETHYST) {
            AmethystColors
        } else if (theme == AppTheme.EMERALD) {
            EmeraldColors
        } else {
            MidnightColors
        }

    MaterialTheme(
        typography = WeatherlyTypography,
    ) {
        CompositionLocalProvider(LocalWeatherlyColors provides colors) {
            content()
        }
    }
}
