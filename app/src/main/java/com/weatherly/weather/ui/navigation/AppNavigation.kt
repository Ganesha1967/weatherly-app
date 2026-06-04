package com.weatherly.weather.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.weatherly.weather.data.preferences.AppState
import com.weatherly.weather.ui.theme.Weatherly
import com.weatherly.weather.ui.theme.localTheme
import com.weatherly.weather.utils.formatNavigationDate

@Composable
fun AppNavigation(
    appState: AppState,
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val prefs by appState.prefs.collectAsState()

    LaunchedEffect(prefs.theme) {
        localTheme.value = prefs.theme
    }

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
    ) { paddingValues ->
        Box(
            modifier =
                modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors =
                                listOf(
                                    Weatherly.colors.gradientHeaderStart,
                                    Weatherly.colors.backgroundPrimary,
                                    Weatherly.colors.backgroundDeepest,
                                ),
                            startY = 0f,
                            endY = 1000f,
                        ),
                    ),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
            ) {
                NavigationHeader(
                    cityName = prefs.cityName,
                    currentDate = formatNavigationDate(),
                )
                NavHostContainer(
                    navController = navController,
                    appState = appState,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun NavigationHeader(
    cityName: String,
    currentDate: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(
                text = cityName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
            )
            Text(
                text = currentDate,
                fontSize = 12.sp,
                color = Weatherly.colors.textMuted,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.5.sp,
            )
        }
    }
}
