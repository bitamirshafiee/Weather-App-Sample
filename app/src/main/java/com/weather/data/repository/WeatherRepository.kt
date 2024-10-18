package com.weather.data.repository

import com.weather.data.model.WeatherResponse
import com.weather.data.service.ServiceProvider
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

abstract class WeatherRepository {
    abstract suspend fun getCurrentWeathersByCityNames(cities: List<String>): List<Deferred<WeatherResponse>>
}

class WeatherRepositoryImpl @Inject constructor(serviceProvider: ServiceProvider) :
    WeatherRepository() {

    private val weatherService = serviceProvider.weatherService

    //it would be better to use city ids instead of the city names and also we can have a json file of
    // all the city ids in the project and search for them in th file and then make the request
    override suspend fun getCurrentWeathersByCityNames(cities: List<String>): List<Deferred<WeatherResponse>> =
    //in case of an exception with just one of the api calls all the other coroutines will be canceled too,
        //maybe we can use a supervisorScope so all the coroutines will not be cancelled by one coroutine failure
        coroutineScope {
            cities.map {
                async(Dispatchers.IO) { weatherService.getWeather(it) }
            }
        }

}