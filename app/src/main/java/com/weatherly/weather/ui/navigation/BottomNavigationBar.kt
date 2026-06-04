package com.weatherly.weather.ui.navigation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.weatherly.weather.ui.theme.Weatherly

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Weatherly.colors.backgroundCard,
        tonalElevation = 0.dp,
        windowInsets = NavigationBarDefaults.windowInsets,
        modifier =
            modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.navigationBars)
                .height(64.dp)
                .border(width = 1.dp, color = Color.White.copy(alpha = 0.05f)),
    ) {
        Constants.BottomNavItems.forEach { navItem ->
            val isActive = currentRoute == navItem.route

            NavigationBarItem(
                selected = isActive,
                onClick = {
                    if (currentRoute != navItem.route) {
                        navController.navigate(navItem.route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = navItem.icon),
                        contentDescription = navItem.label,
                        modifier = Modifier.size(24.dp),
                        tint =
                            if (isActive) {
                                Weatherly.colors.actionAccent
                            } else {
                                Color.White.copy(alpha = 0.4f)
                            },
                    )
                },
                colors =
                    NavigationBarItemDefaults.colors(
                        selectedIconColor = Weatherly.colors.actionAccent,
                        unselectedIconColor = Color.White.copy(alpha = 0.4f),
                        indicatorColor = Color.Transparent,
                    ),
                label = {},
                alwaysShowLabel = false,
            )
        }
    }
}
