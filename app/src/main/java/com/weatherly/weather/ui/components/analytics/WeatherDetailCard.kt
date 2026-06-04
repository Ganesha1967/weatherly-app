package com.weatherly.weather.ui.components.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.weatherly.weather.ui.components.glassCard
import com.weatherly.weather.ui.theme.BodyMediumStyle
import com.weatherly.weather.ui.theme.CaptionStyle
import com.weatherly.weather.ui.theme.LabelSmallStyle
import com.weatherly.weather.ui.theme.Weatherly

data class CardProgress(
    val value: Float,
    val color: Color,
    val showPercentage: Boolean = false,
)

data class CardContent(
    val value: String,
    val subtitle: String,
)

@Composable
fun WeatherDetailCard(
    title: String,
    content: CardContent,
    progress: CardProgress,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .glassCard(20)
                .fillMaxHeight()
                .padding(16.dp),
    ) {
        CardHeader(
            title = title,
            icon = icon,
        )
        Spacer(modifier = Modifier.height(12.dp))

        CardBody(
            content = content,
            modifier = Modifier.weight(1f),
        )
        Spacer(modifier = Modifier.height(12.dp))

        CardFooter(progress = progress)
    }
}

@Composable
private fun CardHeader(
    title: String,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icon()
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title.uppercase(),
            style = LabelSmallStyle,
            color = Weatherly.colors.textMuted,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun CardBody(
    content: CardContent,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = content.value,
            style = BodyMediumStyle.copy(fontWeight = FontWeight.SemiBold),
            color = Color.White,
        )
        if (content.subtitle.isNotEmpty()) {
            Text(
                text = content.subtitle,
                style = CaptionStyle,
                modifier = Modifier.padding(top = 2.dp),
            )
        }
    }
}

@Composable
private fun CardFooter(
    progress: CardProgress,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color.White.copy(alpha = 0.1f)),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth(progress.value.coerceIn(0f, 1f))
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(progress.color),
            )
        }
        if (progress.showPercentage) {
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("0%", style = CaptionStyle)
                Text("100%", style = CaptionStyle)
            }
        } else {
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
