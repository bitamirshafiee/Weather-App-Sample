package com.weather.data.repository

import com.weather.data.model.WeatherResponse
import com.weather.data.service.ServiceProvider
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

abstract class WeatherRepository {
    abstract suspend fun getCurrentWeathersByCityNames(): List<Deferred<WeatherResponse>>
}

class WeatherRepositoryImpl(serviceProvider: ServiceProvider) : WeatherRepository() {

    private val weatherService = serviceProvider.weatherService

    //better using city id with search in json file
    //TODO: an idea of how it should be implemented with async adding dispatchers to scope
    override suspend fun getCurrentWeathersByCityNames(): List<Deferred<WeatherResponse>> =
        coroutineScope {
            val deferredResult1 = async { weatherService.getWeather("Stockholm") }
            val deferredResult2 = async { weatherService.getWeather("London") }
            listOf(deferredResult1, deferredResult2)
        }

}