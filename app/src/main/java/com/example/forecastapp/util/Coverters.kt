package com.example.forecastapp.util

import androidx.room.TypeConverter
import com.google.gson.Gson

// necessary to convert the lists of the currentWeatherEntry to room savable objects
class Converters {

    @TypeConverter
    fun listToJson(value: List<String>?): String {

        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<String>? {

        val objects = Gson().fromJson(value, Array<String>::class.java) as Array<String>
        val list = objects.toList()
        return list
    }
}