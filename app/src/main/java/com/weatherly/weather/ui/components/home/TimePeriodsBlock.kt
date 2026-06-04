package com.weatherly.weather.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weatherly.weather.ui.theme.LabelSmallStyle
import com.weatherly.weather.ui.theme.Weatherly
import com.weatherly.weather.viewmodel.home.HomeUiState

@Composable
fun TimePeriodsBlock(
    uiState: HomeUiState,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "ПЕРИОДЫ ДНЯ",
            style = LabelSmallStyle,
            color = Weatherly.colors.waveSecondary,
            letterSpacing = 1.sp,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            uiState.timePeriods.forEach { period ->
                Column(
                    modifier =
                        Modifier
                            .weight(1f)
                            .background(
                                Weatherly.colors.backgroundCard,
                                RoundedCornerShape(16.dp),
                            ).padding(vertical = 16.dp, horizontal = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = period.title,
                        fontSize = 11.sp,
                        color = Weatherly.colors.textMuted,
                    )
                    Text(
                        text = period.icon,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = period.tempDisplay,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                    )
                }
            }
        }
    }
}
