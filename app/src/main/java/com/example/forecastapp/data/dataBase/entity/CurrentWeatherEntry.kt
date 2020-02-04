package com.example.forecastapp.data.dataBase.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.forecastapp.util.Constants
import com.google.gson.annotations.SerializedName


@Entity(tableName = "current_weather")
data class CurrentWeatherEntry(
    @SerializedName("observation_time")
    val observationTime: String,
    val temperature: Int,
    @SerializedName("weather_code")
    val weatherCode: Int,
    @Embedded(prefix = "weatherIcons_")
    @SerializedName("weather_icons")
    val weatherIcons: List<String>,
    @Embedded(prefix = "weather_descriptions_")
    @SerializedName("weather_descriptions")
    val weatherDescriptions: List<String>,
    @SerializedName("wind_speed")
    val windSpeed: Int,
    @SerializedName("wind_degree")
    val windDegree: Int,
    @SerializedName("wind_dir")
    val windDir: String,
    val precip: Double,
    val humidity: Int,
    val cloudcover: Int,
    val feelslike: Int,
    @SerializedName("uv_index")
    val uvIndex: Int,
    val visibility: Int,
    @SerializedName("is_day")
    val isDay: String
){
    @PrimaryKey(autoGenerate = false)
    var id : Int = Constants.CURRENT_WEATHER_ID
}