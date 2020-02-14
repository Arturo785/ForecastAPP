package com.example.forecastapp.weather.current

import androidx.lifecycle.ViewModel
import com.example.forecastapp.data.repository.ForecastRepository
import com.example.forecastapp.internal.lazyDeferred
import com.example.forecastapp.util.Constants.Companion.UnitSystem

class CurrentWeatherViewModel(private val forecastRepository: ForecastRepository) : ViewModel() {

    private val unitSystem = UnitSystem.METRIC //get from settings later

    val isMetric : Boolean
    get() {
        return unitSystem == UnitSystem.METRIC
    }


    //by lazy only access it when it is needed
    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }

}
