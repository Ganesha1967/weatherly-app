package com.weatherly.weather.ui.components.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.weatherly.weather.R
import com.weatherly.weather.domain.model.CitySearchResult
import com.weatherly.weather.ui.theme.BodyMediumStyle
import com.weatherly.weather.ui.theme.BodySmallStyle
import com.weatherly.weather.ui.theme.Weatherly

@Composable
fun FavoriteItem(
    city: CitySearchResult,
    onClick: () -> Unit,
    onFavoriteToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Weatherly.colors.glassSurface)
                .border(
                    1.dp,
                    Weatherly.colors.glassStroke,
                    RoundedCornerShape(12.dp),
                ).clickable(onClick = onClick)
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp,
                ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = city.name,
                style = BodyMediumStyle.copy(color = Color.White),
            )
            Text(
                text = city.country,
                style = BodySmallStyle.copy(color = Weatherly.colors.textMuted),
            )
        }

        IconButton(
            onClick = onFavoriteToggle,
            modifier = Modifier.size(24.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_favorite_filled),
                contentDescription = "Убрать из избранного",
                tint = Weatherly.colors.actionAccent,
                modifier = Modifier.size(18.dp),
            )
        }
    }
}
