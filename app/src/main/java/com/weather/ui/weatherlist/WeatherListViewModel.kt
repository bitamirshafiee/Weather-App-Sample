package com.weather.ui.weatherlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.data.model.WeatherData
import com.weather.data.model.getDefaultWeatherData
import com.weather.data.repository.WeatherRepository
import com.weather.ext.createIconUrl
import com.weather.ext.errorHandlerHelper
import com.weather.ext.getCityNames
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Deferred
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

    private val _weatherResult = MutableStateFlow(listOf<WeatherData>())
    val weatherResult: StateFlow<List<WeatherData>> = _weatherResult

    fun resetErrorDialog() {
        _isShowErrorDialog.value = Pair(false, -1)
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _isInProgress.value = false
        val networkError = errorHandlerHelper(exception)
        _isShowErrorDialog.value = Pair(true, networkError.errorMessage)
        println("Caught exception: ${exception.message}")
    }

    init {
        getWeathers(getCityNames())
    }

    //city names are passed here in viewmodel which in a real world app could be something that
    // will be gotten from the UI and it would be easier to test too
    //We can also create a sealed NetworkResult class with either successful or Exception objects
    // and use it for sending the data to viewmodel which is more cleaner and understandable
    private fun getWeathers(cities: List<String>) {
        _isInProgress.value = true
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getCurrentWeathersByCityNames(cities = cities)
            _weatherResult.value = result.awaitAll()
                .map { weatherResponse ->
                    WeatherData(
                        cityName = weatherResponse.cityName,
                        currentTemperature = weatherResponse.weatherStatistic.temperature,
                        feelsLikeTemperature = weatherResponse.weatherStatistic.feelsLikeTemperature,
                        iconUrl = createIconUrl(
                            weatherResponse.weather.firstOrNull()?.icon ?: ""
                        )
                    )
                }
            _isInProgress.value = false
        }
    }

}