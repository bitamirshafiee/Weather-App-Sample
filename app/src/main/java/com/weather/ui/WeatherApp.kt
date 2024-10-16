package com.weather.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.weather.ui.weatherlist.WeatherList

@Composable
fun WeatherApp(appState: WeatherAppState = rememberWeatherAppState()) {
    NavHost(
        navController = appState.navController,
        startDestination = NavControllerRoute.WeatherList.route
    ) {
        composable(route = NavControllerRoute.WeatherList.route) {
            WeatherList()
        }
    }
}