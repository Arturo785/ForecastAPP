package com.example.forecastapp.data.network

import okhttp3.Interceptor

//Used because of the dependency injection
interface ConnectivityInterceptor : Interceptor