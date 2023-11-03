package com.example.flymap

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData

class NetworkManager(context: Context):LiveData<Boolean>() {
    override fun onActive() {
        super.onActive()
        checkNetworkConnectivity()
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
    private var connectivityManager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val networkCallback = object :ConnectivityManager.NetworkCallback(){

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Log.d("OK AVAIBLE","OKKKKKKK")
            postValue(true)
        }

        override fun onUnavailable() {
            super.onUnavailable()
            Log.d("OK onUnavailable","OKKKKKKK")
            postValue(false)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            Log.d("OK onLost","OKKKKKKK")
            postValue(false)
        }
    }
    private fun checkNetworkConnectivity(){

        Log.d("OK checkNetworkConnecti","OKKKKKKK")
        val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo

        val requestBuilder = NetworkRequest.Builder().apply {

            addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

            addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)

            addTransportType(NetworkCapabilities.TRANSPORT_WIFI)

            addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)

        }.build()

        connectivityManager.registerNetworkCallback(requestBuilder,networkCallback)
        postValue(activeNetworkInfo != null && activeNetworkInfo.isConnected)
    }
}