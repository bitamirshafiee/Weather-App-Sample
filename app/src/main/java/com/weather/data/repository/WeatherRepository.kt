package com.weather.data.repository

import com.weather.data.model.WeatherResponse
import com.weather.data.service.ServiceProvider
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

abstract class WeatherRepository {
    abstract suspend fun getCurrentWeathersByCityNames(cities: List<String>): List<Deferred<WeatherResponse>>
}

class WeatherRepositoryImpl @Inject constructor(serviceProvider: ServiceProvider) :
    WeatherRepository() {

    private val weatherService = serviceProvider.weatherService

    //better using city id with search in json file
    //TODO: an idea of how it should be implemented with async adding dispatchers to scope
    //TODO: handle exception
    override suspend fun getCurrentWeathersByCityNames(cities: List<String>): List<Deferred<WeatherResponse>> =
        coroutineScope {
            cities.map {
                async(Dispatchers.IO) { weatherService.getWeather(it) }
            }
        }

}