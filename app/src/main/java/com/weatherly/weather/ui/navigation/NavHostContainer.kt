package com.weatherly.weather.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.weatherly.weather.data.preferences.AppState
import com.weatherly.weather.data.preferences.Screen
import com.weatherly.weather.ui.screens.AnalyticsScreen
import com.weatherly.weather.ui.screens.HomeScreen
import com.weatherly.weather.ui.screens.SearchScreen
import com.weatherly.weather.ui.screens.SettingsScreen

@Composable
fun NavHostContainer(
    navController: NavHostController,
    appState: AppState,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HOME.name,
        modifier = modifier,
    ) {
        composable(route = Screen.ANALYTICS.name) {
            AnalyticsScreen()
        }

        composable(route = Screen.HOME.name) {
            HomeScreen()
        }

        composable(route = Screen.SEARCH.name) {
            SearchScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = Screen.SETTINGS.name) {
            SettingsScreen(appState = appState)
        }
    }
}
