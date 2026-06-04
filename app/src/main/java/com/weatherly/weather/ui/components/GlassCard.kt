package com.weatherly.weather.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.weatherly.weather.ui.theme.Weatherly

@Composable
fun Modifier.glassCard(radius: Int = 24): Modifier {
    val colors = Weatherly.colors
    val shape = RoundedCornerShape(radius.dp)
    return this
        .clip(shape)
        .background(colors.glassSurface)
        .border(width = 1.dp, color = colors.glassStroke, shape = shape)
}
