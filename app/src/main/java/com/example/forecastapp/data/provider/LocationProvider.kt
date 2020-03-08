package com.example.forecastapp.data.provider

import com.example.forecastapp.data.dataBase.entity.Location


interface LocationProvider {

    suspend fun hasLocationChanged(lastWeatherLocation: Location): Boolean
    suspend fun getPreferredLocationString(): String
}