package com.weather.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.weather.ui.weatherlist.WeatherList
import com.weather.ui.weatherlist.WeatherListViewModel

@Composable
fun WeatherApp(appState: WeatherAppState = rememberWeatherAppState()) {
    NavHost(
        navController = appState.navController,
        startDestination = NavControllerRoute.WeatherList.route
    ) {
        composable(route = NavControllerRoute.WeatherList.route) {
            val viewModel = hiltViewModel<WeatherListViewModel>()
            WeatherList(viewModel)
        }
    }
}