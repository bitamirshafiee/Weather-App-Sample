package com.weather.data.service

import retrofit2.Retrofit

class ServiceProvider(retrofit: Retrofit) {
    val weatherService: WeatherService = retrofit.create(WeatherService::class.java)
}