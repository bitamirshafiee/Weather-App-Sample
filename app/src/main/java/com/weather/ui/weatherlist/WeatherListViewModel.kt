package com.weather.ui.weatherlist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WeatherListViewModel : ViewModel(){

    private val _isInProgress = MutableStateFlow(true)
    val isInProgress: StateFlow<Boolean> = _isInProgress

    private val _isShowErrorDialog = MutableStateFlow(Pair(false, -1))
    val isShowErrorDialog: StateFlow<Pair<Boolean, Int>> = _isShowErrorDialog

    fun resetErrorDialog(){
        _isShowErrorDialog.value = Pair(false,-1)
    }
}