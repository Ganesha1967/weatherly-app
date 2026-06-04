package com.weatherly.weather.ui.navigation

import androidx.annotation.DrawableRes

data class BottomNavItem(
    val label: String,
    @param:DrawableRes val icon: Int,
    val route: String,
)
