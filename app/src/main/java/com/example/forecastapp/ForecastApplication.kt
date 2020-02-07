package com.example.forecastapp

import android.app.Application
import com.example.forecastapp.data.dataBase.ForecastDatabase
import com.example.forecastapp.data.network.*
import com.example.forecastapp.data.repository.ForecastRepository
import com.example.forecastapp.data.repository.ForecastRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton {instance<ForecastDatabase>().currentWeatherDao()}
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) } // binds
        // different because this ones have implementation
        bind() from singleton { WeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(),instance()) }

        }
}