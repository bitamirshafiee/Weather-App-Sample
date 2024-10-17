package com.weather.data.service

import com.weather.const.APP_ID
import com.weather.const.TEMPERATURE_UNIT
import com.weather.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather?")
    suspend fun getWeather(
        @Query("q") cityName: String,
        @Query("appid") appId: String = APP_ID,
        @Query("units") temperatureUnit: String = TEMPERATURE_UNIT
    ): WeatherResponse
}