package com.example.forecastapp.data.network

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response
import android.net.NetworkCapabilities
import android.net.Network
import android.content.Context.CONNECTIVITY_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import com.example.forecastapp.internal.NoConnectivityException
import java.io.IOException


class ConnectivityInterceptorImpl (context: Context): ConnectivityInterceptor {

    private val appContext = context.applicationContext // to reference whole context not just
    //the one from activity or fragment

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline()){
            throw NoConnectivityException()
        }
        return chain.proceed(chain.request())
    }

    private fun isOnline() : Boolean{
        val connectivityManager = appContext.getSystemService(CONNECTIVITY_SERVICE)
        as ConnectivityManager

        val networkInfo = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(networkInfo)

        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)


        /*val networkCapabilities = connectivityManager.getNetworkCapabilities(networkInfo) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)*/
    }

}