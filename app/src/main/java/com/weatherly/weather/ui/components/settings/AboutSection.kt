package com.weatherly.weather.ui.components.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.weatherly.weather.ui.components.glassCard
import com.weatherly.weather.ui.theme.BodySmallStyle
import com.weatherly.weather.ui.theme.HeadlineLargeStyle
import com.weatherly.weather.ui.theme.LabelSmallStyle
import com.weatherly.weather.ui.theme.Weatherly

@Composable
fun AboutSection(modifier: Modifier = Modifier) {
    val mutedColor = Weatherly.colors.textMuted

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .glassCard(24)
                .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Weatherly",
            style = HeadlineLargeStyle.copy(color = Color.White),
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = "Версия 1.0.0",
            style = LabelSmallStyle.copy(color = mutedColor),
            modifier = Modifier.padding(top = 2.dp),
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Умный погодный компаньон с персонализированными рекомендациями и аналитикой в реальном времени",
            style = BodySmallStyle.copy(color = Color.White.copy(alpha = 0.85f)),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp),
        )

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalDivider(
            modifier =
                Modifier
                    .fillMaxWidth(0.6f)
                    .height(1.dp)
                    .background(mutedColor.copy(alpha = 0.2f)),
            thickness = DividerDefaults.Thickness,
            color = DividerDefaults.color,
        )
    }
}
