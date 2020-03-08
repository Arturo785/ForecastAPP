package com.example.forecastapp.data.dataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.forecastapp.data.dataBase.entity.Location
import com.example.forecastapp.data.dataBase.entity.WEATHER_LOCATION_ID

@Dao
interface WeatherLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(location : Location)

    @Query("SELECT * FROM weather_location WHERE id = $WEATHER_LOCATION_ID")
    fun getLocation(): LiveData<Location>

}