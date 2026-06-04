package com.weatherly.weather.ui.components.analytics

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.weatherly.weather.ui.components.glassCard
import com.weatherly.weather.ui.theme.BodySmallStyle
import com.weatherly.weather.ui.theme.TitleSmallStyle
import com.weatherly.weather.ui.theme.Weatherly

@Composable
fun WeatherInsightCard(
    title: String,
    description: String,
    icon: @Composable () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .glassCard(20)
                .border(
                    1.dp,
                    Weatherly.colors.actionAccent.copy(alpha = 0.2f),
                    RoundedCornerShape(20.dp),
                ).padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                icon()
                Column {
                    Text(
                        text = title,
                        style = TitleSmallStyle.copy(color = Color.White),
                    )
                    Text(
                        text = description,
                        style = BodySmallStyle.copy(color = Color.White.copy(alpha = 0.6f)),
                        modifier = Modifier.padding(top = 2.dp),
                    )
                }
            }
        }
    }
}
