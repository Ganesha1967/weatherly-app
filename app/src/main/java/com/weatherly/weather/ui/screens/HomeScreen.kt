package com.weatherly.weather.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.weatherly.weather.ui.components.home.RecommendationCard
import com.weatherly.weather.ui.components.home.TimePeriodsBlock
import com.weatherly.weather.ui.components.home.WeatherHeaderSection
import com.weatherly.weather.ui.components.home.WeatherHeaderState
import com.weatherly.weather.ui.theme.Weatherly
import com.weatherly.weather.viewmodel.home.HomeUiState
import com.weatherly.weather.viewmodel.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val pullToRefreshState = rememberPullToRefreshState()
    val isRefreshing = uiState.isLoading && uiState.timePeriods.isNotEmpty()

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(Weatherly.colors.backgroundDeepest),
    ) {
        if (uiState.isLoading && uiState.timePeriods.isEmpty()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Weatherly.colors.actionAccent,
            )
        } else {
            HomeContent(
                uiState = uiState,
                pullToRefreshState = pullToRefreshState,
                isRefreshing = isRefreshing,
                onRefresh = { viewModel.refreshWeather() },
                modifier = modifier,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    uiState: HomeUiState,
    pullToRefreshState: PullToRefreshState,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PullToRefreshBox(
        state = pullToRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            WeatherHeaderSection(
                state =
                    WeatherHeaderState(
                        currentTemp = uiState.currentTemp,
                        tempRange = uiState.tempRange,
                        weatherType = uiState.weatherType,
                        weatherDescription = uiState.weatherDescription,
                        precipitation = uiState.precipitation,
                    ),
                uiState = uiState,
            )

            TimePeriodsBlock(uiState = uiState)
            RecommendationCard(uiState = uiState)
        }
    }
}
