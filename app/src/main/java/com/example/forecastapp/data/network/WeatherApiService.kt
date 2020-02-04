package com.example.forecastapp.data.network

import com.example.forecastapp.data.network.reponse.CurrentWeatherResponse
import com.example.forecastapp.util.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    /*
    xhttp://api.weatherstack.com/current?access_key=2cdba7a45e4a80d42dda82885b02b04c&query=New York
    optional parameters:
    */
    /*& units = m
    & language = en*/



    // the method, the endPoint, the query

    @GET("current")
    fun getCurrentWeather(
        @Query("query") location : String
    ): Deferred<CurrentWeatherResponse> // it is handled by coroutines


    companion object {
        operator fun invoke() : WeatherApiService {
            val requestInterceptor = Interceptor {

                val url = it.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("access_key",Constants.API_KEY)
                    .build()

                val request = it.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor it.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApiService::class.java)
        }
    }

}