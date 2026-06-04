package com.weatherly.weather.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.weatherly.weather.R
import com.weatherly.weather.ui.components.analytics.SunriseSunsetCard
import com.weatherly.weather.ui.components.analytics.UvPrecipitationSection
import com.weatherly.weather.ui.components.analytics.WeatherInsightCard
import com.weatherly.weather.ui.components.analytics.WeeklyForecastCard
import com.weatherly.weather.ui.components.analytics.WindPressureSection
import com.weatherly.weather.ui.theme.Weatherly
import com.weatherly.weather.viewmodel.analytics.AnalyticsUiState
import com.weatherly.weather.viewmodel.analytics.AnalyticsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(
    modifier: Modifier = Modifier,
    viewModel: AnalyticsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val pullToRefreshState = rememberPullToRefreshState()
    val isRefreshing = uiState.isLoading && uiState.weeklyForecast.isNotEmpty()

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(Weatherly.colors.backgroundPrimary),
    ) {
        if (uiState.isLoading && uiState.weeklyForecast.isEmpty()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Weatherly.colors.actionAccent,
            )
        } else {
            PullToRefreshBox(
                state = pullToRefreshState,
                isRefreshing = isRefreshing,
                onRefresh = { viewModel.refreshAnalytics() },
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter,
            ) {
                AnalyticsContent(uiState = uiState)
            }
        }
    }
}

@Composable
private fun AnalyticsContent(
    uiState: AnalyticsUiState,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        WeatherInsightCard(
            title = uiState.insight.title,
            description = uiState.insight.description,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_analytics),
                    contentDescription = null,
                    tint = Weatherly.colors.waveSecondary,
                    modifier = Modifier.size(24.dp),
                )
            },
        )

        WeeklyForecastCard(forecastData = uiState.weeklyForecast)
        WindPressureSection(uiState = uiState)
        UvPrecipitationSection(uiState = uiState)
        SunriseSunsetCard(data = uiState.sunriseSunset)

        Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
        Spacer(modifier = Modifier.height(64.dp))
    }
}
