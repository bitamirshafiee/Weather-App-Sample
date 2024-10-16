package com.weather.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("main") val weatherStatistic: WeatherStatistic
)

data class Weather(
    @SerializedName("main") val condition: String, @SerializedName("icon") val icon: String
)

data class WeatherStatistic(
    @SerializedName("temp") val temperature: Double,
    @SerializedName("feels_like") val feelsLikeTemperature: Double
)

fun getDefaultWeatherResponse() = WeatherResponse(
    weather = listOf(
        Weather(condition = "", icon = "")
    ), WeatherStatistic(temperature = 9.9, feelsLikeTemperature = 7.66)
)
