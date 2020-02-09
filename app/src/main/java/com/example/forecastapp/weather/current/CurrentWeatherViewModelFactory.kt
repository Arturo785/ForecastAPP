package com.example.forecastapp.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.forecastapp.data.repository.ForecastRepository

class CurrentWeatherViewModelFactory(private val forecastRepository: ForecastRepository)
    : ViewModelProvider.NewInstanceFactory()
{


    // to have an instance of the view model and not recreate one when returning
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(forecastRepository) as T
    }


}