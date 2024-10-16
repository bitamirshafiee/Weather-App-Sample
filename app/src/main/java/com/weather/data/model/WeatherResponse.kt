package com.weather.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("name") val cityName: String,
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
    cityName = "City",
    weather = listOf(
        Weather(condition = "", icon = "")
    ), WeatherStatistic(temperature = 9.9, feelsLikeTemperature = 7.66)
)

data class WeatherData(
    val cityName: String,
    val currentTemperature: Double,
    val feelsLikeTemperature: Double,
    val iconUrl: String
)

fun getDefaultWeatherData() = WeatherData(
    cityName = "City",
    currentTemperature = 1.1,
    feelsLikeTemperature = 5.4,
    iconUrl = "http://10d"
)
