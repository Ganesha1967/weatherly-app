package com.weatherly.weather.ui.components.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.weatherly.weather.ui.theme.BodySmallStyle
import com.weatherly.weather.ui.theme.LabelSmallStyle
import com.weatherly.weather.ui.theme.Weatherly

@Composable
fun SettingSegmentedControl(
    title: String,
    options: List<String>,
    selected: Int,
    onOptionSelected: (Int) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title.uppercase(),
            style = LabelSmallStyle.copy(color = Color.White),
            modifier = Modifier.padding(bottom = 8.dp),
        )

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Weatherly.colors.glassSurface)
                    .border(
                        1.dp,
                        Weatherly.colors.glassStroke,
                        RoundedCornerShape(10.dp),
                    ),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            options.forEachIndexed { index, option ->
                SegmentButton(
                    option = option,
                    isSelected = index == selected,
                    onClick = { onOptionSelected(index) },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun SegmentButton(
    option: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor =
        if (isSelected) {
            Weatherly.colors.actionAccent
        } else {
            Color.Transparent
        }

    val textColor =
        if (isSelected) {
            Weatherly.colors.backgroundPrimary
        } else {
            Color.White.copy(alpha = 0.8f)
        }

    val fontWeight =
        if (isSelected) {
            FontWeight.SemiBold
        } else {
            FontWeight.Normal
        }

    Box(
        modifier =
            modifier
                .padding(1.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(backgroundColor)
                .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = option,
            style =
                BodySmallStyle.copy(
                    color = textColor,
                    fontWeight = fontWeight,
                ),
            modifier = Modifier.padding(vertical = 8.dp),
        )
    }
}
