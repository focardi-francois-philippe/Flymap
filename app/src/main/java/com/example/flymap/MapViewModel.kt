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
    private val BASE_URL = "https://opensky-network.org/api"
    private val REQUEST_TRACKS = BASE_URL+"/tracks/all"
    fun fetchDataFromIcao24(icao: String,context: Context)
    {

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
        return  mapFlightData
    }

    fun getLatLngLstForMap(): LiveData<List<LatLng>>
    {
        return latLngLstForMap
    }
    fun setLatLngLstForMap(latLng: List<LatLng>)
    {
        latLngLstForMap.postValue(latLng)
    }


}