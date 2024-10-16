package com.weather.ui.weatherlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.data.model.WeatherData
import com.weather.data.model.getDefaultWeatherData
import com.weather.data.repository.WeatherRepository
import com.weather.ext.createIconUrl
import com.weather.ext.getCityNames
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherListViewModel @Inject constructor(private val repository: WeatherRepository) :
    ViewModel() {

    private val _isInProgress = MutableStateFlow(true)
    val isInProgress: StateFlow<Boolean> = _isInProgress

    private val _isShowErrorDialog = MutableStateFlow(Pair(false, -1))
    val isShowErrorDialog: StateFlow<Pair<Boolean, Int>> = _isShowErrorDialog

    private val _weatherResult = MutableStateFlow(listOf(getDefaultWeatherData()))
    val weatherResult: StateFlow<List<WeatherData>> = _weatherResult

    fun resetErrorDialog() {
        _isShowErrorDialog.value = Pair(false, -1)
    }

    init {
        getWeathers(getCityNames())
    }

    //TODO: map weather data
    //city names are passed here in viewmodel which in a real world app could be something that
    // will be gotten from the UI
    private fun getWeathers(cities: List<String>) {
        viewModelScope.launch {
            val result = repository.getCurrentWeathersByCityNames(cities = cities)
            _weatherResult.value = result.awaitAll()
                .map { weatherResponse ->
                    WeatherData(
                        cityName = weatherResponse.cityName,
                        currentTemperature = weatherResponse.weatherStatistic.temperature,
                        feelsLikeTemperature = weatherResponse.weatherStatistic.feelsLikeTemperature,
                        iconUrl = createIconUrl(weatherResponse.weather.firstOrNull()?.icon ?: "")
                    )
                }
        }
    }

}