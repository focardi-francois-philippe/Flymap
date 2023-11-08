package com.example.flymap

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel:ViewModel() {
    val mapFlightData = MutableLiveData<DataMapFlight>()
    val latLngLstForMap = MutableLiveData<List<LatLng>>()
    val flyStateData = MutableLiveData<FlyStateModel>()
    val lastFlightsData = MutableLiveData<Array<LastFlights>>()

    private val BASE_URL = "https://opensky-network.org/api"
    private val REQUEST_TRACKS = BASE_URL + "/tracks/all"

    fun fetchLastFlightsByIcao(icao24: String) {
        val nowTs = (System.currentTimeMillis() / 1000).toInt() - 3600
        val beginTs = nowTs - 259200 // 3 days in seconds
        val url = BASE_URL + "/flights/aircraft?icao24=${icao24}&begin=${beginTs}&end=${nowTs}"

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result =  RequestManager.getSuspended(url, HashMap())
                Log.d("result", result.toString())
                val gson = Gson()
                val myDataList: Array<LastFlights> = gson.fromJson(result, Array<LastFlights>::class.java)
                lastFlightsData.postValue(myDataList) // Update the LiveData with the result
                Log.d("result", myDataList.toString())
            } catch (e: Exception) {
                Log.e("flightList", e.localizedMessage)
            }
        }
    }
    fun fetchFlyStateByIcao(icao24: String, context: Context? = null) {
        val url = BASE_URL + "/states/all?icao24=${icao24}"
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result =  RequestManager.getSuspended(url, HashMap())
                Log.d("result", result.toString())
                val gson = Gson()
                val myDataList: FlyStateModelGson = gson.fromJson(result, FlyStateModelGson::class.java)
                flyStateData.postValue(myDataList.toModel()) // Update the LiveData with the result
                Log.d("result", myDataList.toString())
            } catch (e: Exception) {
                Log.e("flightList", e.localizedMessage)
                if (context != null) {
                    val gson = Gson()
                    val result = Utils.getJsonDataFromAsset(context, "state.json")
                    val myDataList: FlyStateModelGson = gson.fromJson(result, FlyStateModelGson::class.java)
                    flyStateData.postValue(myDataList.toModel())
                    Log.d("result", myDataList.toModel().toString())
                }
            }
        }
    }
    fun fetchDataFromIcao24(icao: String,context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("AZERTY2","ok")

                //val result =  RequestManager.getSuspended(
                //    "$REQUEST_TRACKS?icao24=$icao&time=0",
                //    HashMap())
               val jsonFileString = Utils.getJsonDataFromAsset(context, "flight.json")
               Log.i("data", jsonFileString!!)
                val gson = Gson()
                //val myDataList: DataMapFlight =  gson.fromJson(result, DataMapFlight::class.java)

                val myDataList: DataMapFlight =  gson.fromJson(jsonFileString, DataMapFlight::class.java)
                Log.d("result",myDataList.toString())
               // Log.d("result",myDataList.path.get(0).toString())
                mapFlightData.postValue(myDataList) // Update the LiveData with the result
            } catch (e: Exception) {
                Log.e("flightList", e.localizedMessage)
                // Handle network request errors here
            }
        }
    }
    fun getMapFlightData() : LiveData<DataMapFlight>
    {
        return mapFlightData
    }

    fun getLatLngLstForMap(): LiveData<List<LatLng>>
    {
        return latLngLstForMap
    }
    fun setLatLngLstForMap(latLng: List<LatLng>)
    {
        latLngLstForMap.postValue(latLng)
    }

    fun getFlyStateData(): LiveData<FlyStateModel>
    {
        return  flyStateData
    }

    fun getLastFlightsData(): LiveData<Array<LastFlights>>
    {
       return lastFlightsData
    }
}