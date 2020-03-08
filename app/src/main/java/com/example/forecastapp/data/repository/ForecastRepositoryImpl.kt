package com.example.forecastapp.data.repository

import androidx.lifecycle.LiveData
import com.example.forecastapp.data.dataBase.CurrentWeatherDAO
import com.example.forecastapp.data.dataBase.WeatherLocationDao
import com.example.forecastapp.data.dataBase.entity.CurrentWeatherEntry
import com.example.forecastapp.data.dataBase.entity.Location
import com.example.forecastapp.data.network.WeatherNetworkDataSource
import com.example.forecastapp.data.network.reponse.CurrentWeatherResponse
import com.example.forecastapp.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl (
    private val currentWeatherDAO: CurrentWeatherDAO,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
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

    // to save the response into the db
    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO){
            currentWeatherDAO.upsert(fetchedWeather.currentWeatherEntry)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }

    private suspend fun initWeatherData(){
        val lastWeatherLocation = weatherLocationDao.getLocation().value

        if(lastWeatherLocation == null || locationProvider.hasLocationChanged(lastWeatherLocation)){
            fetchCurrentWeather()
            return
        }

        else if(isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime)){
            fetchCurrentWeather()
        }
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime) : Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocationString()
        )
        // the init block observes forever the result
    }
    override suspend fun getWeatherLocation(): LiveData<Location> {
        return withContext(IO){
            return@withContext weatherLocationDao.getLocation()
        }
    }

}