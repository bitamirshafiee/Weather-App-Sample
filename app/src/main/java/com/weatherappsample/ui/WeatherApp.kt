package com.weatherappsample.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun WeatherApp(appState: WeatherAppState = rememberWeatherAppState()) {
    NavHost(
        navController = appState.navController,
        startDestination = NavControllerRoute.WeatherList.route
    ) {
        composable(route = NavControllerRoute.WeatherList.route) {
            //TODO: Weather list
        }
    }
}