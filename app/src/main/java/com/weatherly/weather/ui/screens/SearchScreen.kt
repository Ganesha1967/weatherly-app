package com.weatherly.weather.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.weatherly.weather.ui.components.search.FavoritesSection
import com.weatherly.weather.ui.components.search.SearchContentStatus
import com.weatherly.weather.ui.components.search.SearchInputField
import com.weatherly.weather.ui.theme.TitleLargeStyle
import com.weatherly.weather.ui.theme.Weatherly
import com.weatherly.weather.viewmodel.search.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val favorites by viewModel.favoritesFlow.collectAsState(initial = emptyList())

    LaunchedEffect(uiState.selectedCityId) {
        if (uiState.selectedCityId != null) {
            onNavigateBack()
            viewModel.clearSearch()
        }
    }

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Weatherly.colors.backgroundDeepest.copy(alpha = 0.85f)),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "Поиск города",
                style = TitleLargeStyle.copy(color = Color.White),
            )

            SearchInputField(
                query = uiState.query,
                onQueryChange = { viewModel.updateQuery(it) },
            )

            SearchContentStatus(
                uiState = uiState,
                favorites = favorites,
                onCitySelect = { name -> viewModel.selectCityByName(name) },
                onFavoriteToggle = { city -> viewModel.toggleFavorite(city) },
            )

            if (favorites.isNotEmpty()) {
                FavoritesSection(
                    favorites = favorites,
                    onCitySelected = { city -> viewModel.selectFavoriteCity(city) },
                    onFavoriteToggle = { city -> viewModel.toggleFavorite(city) },
                )
            }
        }
    }
}
