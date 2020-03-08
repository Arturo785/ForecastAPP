package com.example.forecastapp

import android.app.Application
import com.example.forecastapp.data.dataBase.ForecastDatabase
import com.example.forecastapp.data.network.*
import com.example.forecastapp.data.provider.LocationProvider
import com.example.forecastapp.data.provider.LocationProviderImpl
import com.example.forecastapp.data.provider.UnitProvider
import com.example.forecastapp.data.provider.UnitProviderImpl
import com.example.forecastapp.data.repository.ForecastRepository
import com.example.forecastapp.data.repository.ForecastRepositoryImpl
import com.example.forecastapp.weather.current.CurrentWeatherViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton {instance<ForecastDatabase>().currentWeatherDao()}
        bind() from singleton {instance<ForecastDatabase>().weatherLocationDao()}
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) } // binds
        // different because this ones have implementation
        bind() from singleton { WeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl() }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(),instance(), instance(),instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(),instance()) }

        }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}