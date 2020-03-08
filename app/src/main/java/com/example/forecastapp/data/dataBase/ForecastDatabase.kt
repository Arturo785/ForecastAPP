package com.example.forecastapp.data.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.forecastapp.data.dataBase.entity.CurrentWeatherEntry
import com.example.forecastapp.data.dataBase.entity.Location
import com.example.forecastapp.util.Converters

// connects with the repository


@Database(
    entities = [CurrentWeatherEntry::class,Location::class],
    version = 1)
    @TypeConverters(Converters::class)




abstract class ForecastDatabase : RoomDatabase(){

    abstract fun currentWeatherDao(): CurrentWeatherDAO
    abstract fun weatherLocationDao(): WeatherLocationDao

    companion object{ // to have a singleton of the DB
       @Volatile private var instance : ForecastDatabase? = null
        private val LOCK = Any() // to prevent threads locking

        //Whenever it's called check if it is null, if it isn't return the instance otherwise
        //create a new instance

        operator fun invoke(context : Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                ForecastDatabase::class.java,"forecast.db")
                .build()
    }

}