package com.weatherly.weather.ui.components.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ThemeCircle(
    color: Color,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val borderWidth =
        if (isActive) {
            2.dp
        } else {
            0.dp
        }

    val borderColor =
        if (isActive) {
            Color.White
        } else {
            Color.Transparent
        }

    Box(
        modifier =
            modifier
                .size(36.dp)
                .background(color, CircleShape)
                .border(
                    width = borderWidth,
                    color = borderColor,
                    shape = CircleShape,
                ).clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        if (isActive) {
            Box(
                modifier =
                    Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(Color.White),
            )
        }
    }
}
