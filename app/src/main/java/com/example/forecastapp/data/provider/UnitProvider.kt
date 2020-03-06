package com.example.forecastapp.data.provider

import com.example.forecastapp.util.Constants
import com.example.forecastapp.util.Constants.Companion

interface UnitProvider {

    fun getUnitSistem(): Companion.UnitSystem
}