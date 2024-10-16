package com.weather.ext

import com.weather.const.IMAGE_URL

fun getCityNames() =
    listOf("Gothenburg", "Stockholm", "Mountain View", "London", "New York", "Berlin")

fun createIconUrl(iconName : String) = IMAGE_URL.plus(iconName).plus(".png")