package com.weather.data.service

import retrofit2.Retrofit
import javax.inject.Inject

class ServiceProvider @Inject constructor(retrofit: Retrofit) {
    val weatherService: WeatherService = retrofit.create(WeatherService::class.java)
}