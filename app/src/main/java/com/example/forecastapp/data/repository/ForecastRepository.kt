package com.example.forecastapp.data.repository

import androidx.lifecycle.LiveData
import com.example.forecastapp.data.dataBase.entity.CurrentWeatherEntry
import com.example.forecastapp.data.dataBase.entity.Location

interface ForecastRepository {

    // suspend allows to use the fun inside a coroutine
    suspend fun getCurrentWeather(metric: Boolean) : LiveData<CurrentWeatherEntry>
    suspend fun getWeatherLocation(): LiveData<Location>
}