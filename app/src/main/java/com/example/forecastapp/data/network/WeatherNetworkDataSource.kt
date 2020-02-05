package com.example.forecastapp.data.network

import androidx.lifecycle.LiveData
import com.example.forecastapp.data.network.reponse.CurrentWeatherResponse


interface WeatherNetworkDataSource {

    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String
    )
}