package com.example.forecastapp.data.provider


import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.forecastapp.util.Constants.*
import com.example.forecastapp.util.Constants.Companion.UnitSystem.*


class UnitProviderImpl(context: Context) : UnitProvider {

    private val appContext = context.applicationContext
    val UNIT_SYSTEM = "UNIT_SYSTEM"

    private val preferences : SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    override fun getUnitSistem(): Companion.UnitSystem {
        val selectedName = preferences.getString(UNIT_SYSTEM, METRIC.name)
        return Companion.UnitSystem.valueOf(selectedName ?: METRIC.name)
    }
}