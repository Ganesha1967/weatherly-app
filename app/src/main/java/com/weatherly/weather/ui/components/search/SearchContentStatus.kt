package com.weatherly.weather.ui.components.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.weatherly.weather.domain.model.CitySearchResult
import com.weatherly.weather.ui.theme.BodySmallStyle
import com.weatherly.weather.ui.theme.Weatherly
import com.weatherly.weather.viewmodel.search.SearchUiState

@Composable
fun SearchContentStatus(
    uiState: SearchUiState,
    favorites: List<CitySearchResult>,
    onCitySelect: (String) -> Unit,
    onFavoriteToggle: (CitySearchResult) -> Unit,
    modifier: Modifier = Modifier,
) {
    val emptyMessage =
        if (uiState.query.isBlank()) {
            "Начните вводить название города"
        } else {
            "Ничего не найдено"
        }

    when {
        uiState.isLoading && uiState.query.isNotBlank() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = Weatherly.colors.actionAccent)
            }
        }

        uiState.errorMessage != null -> {
            Text(
                text = uiState.errorMessage,
                style = BodySmallStyle.copy(color = Color.Red),
                modifier = modifier.padding(16.dp),
            )
        }

        uiState.results.isEmpty() -> {
            Text(
                text = emptyMessage,
                style = BodySmallStyle.copy(color = Weatherly.colors.textMuted),
                modifier = modifier.padding(16.dp),
            )
        }

        else -> {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = modifier.fillMaxWidth(),
            ) {
                items(uiState.results) { city ->
                    SearchResultCard(
                        city = city,
                        isFavorite =
                            favorites.any {
                                it.id == city.id
                            },
                        onClick = { onCitySelect(city.name) },
                        onFavoriteToggle = { onFavoriteToggle(city) },
                    )
                }
            }
        }
    }
}
