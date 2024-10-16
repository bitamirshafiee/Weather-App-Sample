package com.weather.ui.weatherlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.data.model.WeatherResponse
import com.weather.data.model.getDefaultWeatherResponse
import com.weather.data.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class WeatherListViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _isInProgress = MutableStateFlow(true)
    val isInProgress: StateFlow<Boolean> = _isInProgress

    private val _isShowErrorDialog = MutableStateFlow(Pair(false, -1))
    val isShowErrorDialog: StateFlow<Pair<Boolean, Int>> = _isShowErrorDialog

    private val _weatherResult = MutableStateFlow(listOf(getDefaultWeatherResponse()))
    val weatherResult: StateFlow<List<WeatherResponse>> = _weatherResult

    fun resetErrorDialog() {
        _isShowErrorDialog.value = Pair(false, -1)
    }

    init {
        getWeathers()
    }

    //TODO: map weather data
    //TODO: Add list of cities for api call, explain it is sent from here which is sth that usually
    // comes from UI
    private fun getWeathers() {
        viewModelScope.launch {
            val result = repository.getCurrentWeathersByCityNames()
            _weatherResult.value = result.awaitAll()
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

}