package com.weatherly.weather.ui.components.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.weatherly.weather.domain.model.CitySearchResult
import com.weatherly.weather.ui.theme.LabelSmallStyle
import com.weatherly.weather.ui.theme.Weatherly

@Composable
fun FavoritesSection(
    favorites: List<CitySearchResult>,
    onSelectCity: (CitySearchResult) -> Unit,
    onToggleFavorite: (CitySearchResult) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = "ИЗБРАННЫЕ ГОРОДА",
            style = LabelSmallStyle,
            color = Weatherly.colors.textMuted,
        )
        Spacer(modifier = Modifier.height(12.dp))

        favorites.forEachIndexed { index, city ->
            FavoriteItem(
                city = city,
                onClick = { onSelectCity(city) },
                onFavoriteToggle = { onToggleFavorite(city) },
            )

            if (index < favorites.lastIndex) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
