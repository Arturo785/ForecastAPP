package com.example.forecastapp.data.repository

import androidx.lifecycle.LiveData
import com.example.forecastapp.data.dataBase.entity.CurrentWeatherEntry

interface ForecastRepository {

    // suspend allows to use the fun inside a coroutine
    suspend fun getCurrentWeather(metric: Boolean) : LiveData<CurrentWeatherEntry>
}