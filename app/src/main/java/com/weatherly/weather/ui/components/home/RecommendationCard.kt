package com.weatherly.weather.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weatherly.weather.ui.theme.BodySmallStyle
import com.weatherly.weather.ui.theme.LabelSmallStyle
import com.weatherly.weather.ui.theme.Weatherly
import com.weatherly.weather.viewmodel.home.ComfortLevel
import com.weatherly.weather.viewmodel.home.HomeUiState

@Composable
fun RecommendationCard(
    uiState: HomeUiState,
    modifier: Modifier = Modifier,
) {
    val rec = uiState.recommendation

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .background(
                    Weatherly.colors.backgroundCard,
                    RoundedCornerShape(24.dp),
                ).padding(20.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = "✦",
                    fontSize = 16.sp,
                    color = Weatherly.colors.waveSecondary,
                )

                Text(
                    text = rec.title.uppercase(),
                    style = LabelSmallStyle,
                    color = Weatherly.colors.waveSecondary,
                    letterSpacing = 1.sp,
                )
            }

            ComfortIndicator(level = rec.comfortLevel)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = rec.mainAdvice,
            style =
                BodySmallStyle.copy(
                    color = Color.White.copy(alpha = 0.95f),
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                ),
        )

        if (rec.tags.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            RecommendationTags(tags = rec.tags)
        }
    }
}

@Composable
private fun RecommendationTags(
    tags: List<String>,
    modifier: Modifier = Modifier,
) {
    val tagStyle =
        LabelSmallStyle.copy(
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 12.sp,
        )

    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        tags.forEach { tag ->
            Text(
                text = tag,
                style = tagStyle,
                modifier =
                    Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Weatherly.colors.backgroundPrimary.copy(alpha = 0.8f))
                        .padding(
                            horizontal = 14.dp,
                            vertical = 8.dp,
                        ),
            )
        }
    }
}

@Composable
private fun ComfortIndicator(
    level: ComfortLevel,
    modifier: Modifier = Modifier,
) {
    val color =
        when (level) {
            ComfortLevel.HIGH -> Color(0xFF4ADE80)
            ComfortLevel.MEDIUM -> Weatherly.colors.actionAccent
            ComfortLevel.LOW -> Color(0xFFEF4444)
        }

    val label =
        when (level) {
            ComfortLevel.HIGH -> "Отлично"
            ComfortLevel.MEDIUM -> "Нормально"
            ComfortLevel.LOW -> "Некомфортно"
        }

    val activeDots =
        when (level) {
            ComfortLevel.HIGH -> 3
            ComfortLevel.MEDIUM -> 2
            ComfortLevel.LOW -> 1
        }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier,
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
            repeat(3) { index ->
                Box(
                    modifier =
                        Modifier
                            .size(6.dp)
                            .background(
                                if (index < activeDots) {
                                    color
                                } else {
                                    Color.White.copy(alpha = 0.15f)
                                },
                                RoundedCornerShape(3.dp),
                            ),
                )
            }
        }
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = color,
        )
    }
}
