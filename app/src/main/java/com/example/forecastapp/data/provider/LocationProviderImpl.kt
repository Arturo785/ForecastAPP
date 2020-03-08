package com.example.forecastapp.data.provider

import com.example.forecastapp.data.dataBase.entity.Location


class LocationProviderImpl : LocationProvider {
    override suspend fun hasLocationChanged(lastWeatherLocation: Location): Boolean {
        return true // only as a dummy answer, not implemented yet
    }

    override suspend fun getPreferredLocationString(): String {
        return "Los angeles" // only as a dummy answer, not implemented yet
    }
}