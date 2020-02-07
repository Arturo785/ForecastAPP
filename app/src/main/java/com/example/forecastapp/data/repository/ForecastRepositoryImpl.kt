package com.example.forecastapp.data.repository

import androidx.lifecycle.LiveData
import com.example.forecastapp.data.dataBase.CurrentWeatherDAO
import com.example.forecastapp.data.dataBase.entity.CurrentWeatherEntry
import com.example.forecastapp.data.network.WeatherNetworkDataSource
import com.example.forecastapp.data.network.reponse.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl (
    private val currentWeatherDAO: CurrentWeatherDAO,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
): ForecastRepository {

    init { // observeForever because repository does not have life cycle
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever{
            newCurrentWeather -> persistFetchedCurrentWeather(newCurrentWeather)
        }
    }


    override suspend fun getCurrentWeather(metric: Boolean) :LiveData<CurrentWeatherEntry> {
        initWeatherData()
       return withContext(Dispatchers.IO){
           return@withContext currentWeatherDAO.getWeatherMetric()
       }
        //withContext returns a value
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO){
            currentWeatherDAO.upsert(fetchedWeather.currentWeatherEntry)
        }
    }

    private suspend fun initWeatherData(){
        if(isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1))){
            fetchCurrentWeather()
        }
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime) : Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(
            "Los Angeles"
        )
    }

}