package com.example.forecastapp.data.reponse

import com.example.forecastapp.data.dataBase.entity.CurrentWeatherEntry
import com.example.forecastapp.data.dataBase.entity.Location
import com.example.forecastapp.data.dataBase.entity.Request
import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponse(
    val request: Request,
    val location: Location,
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry
)