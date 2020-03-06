package com.example.forecastapp.weather.current

import androidx.lifecycle.ViewModel
import com.example.forecastapp.data.provider.UnitProvider
import com.example.forecastapp.data.repository.ForecastRepository
import com.example.forecastapp.internal.lazyDeferred
import com.example.forecastapp.util.Constants.Companion.UnitSystem

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    private val unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = UnitSystem.METRIC //get from settings later
    //private val unitSystem = unitProvider.getUnitSistem() this is the right way but the API in free
    //mode does not allow especial type of metric

    val isMetric : Boolean
    get() {
        return unitSystem == UnitSystem.METRIC
    }


    //by lazy only access it when it is needed
    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }

}
