package com.example.flymap

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataAirportViewModel : ViewModel() {
    val flightLiveData = MutableLiveData<List<FlightModel>>()

    private val BASE_URL = "https://opensky-network.org/api"
    private val REQUEST_DEPART_URL = BASE_URL+"/flights/departure"
    private val REQUEST_ARRIVE_URL = BASE_URL+"/flights/arrival"

    // Function to make the network request using viewModelScope
     fun fetchDataFromApDepartArrivei(isArrive : Boolean,airport : String,debut : Int, fin : Int) {
        Log.d("AZERTY","ok")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("AZERTY2","ok")

                val req = if(isArrive) REQUEST_ARRIVE_URL else REQUEST_DEPART_URL
                val result =  RequestManager.getSuspended("https://opensky-network.org/api/flights/departure?airport=$airport&begin=$debut&end=$fin",
                    HashMap())
                val gson = Gson()
                val myDataList: List<FlightModel> =  gson.fromJson(result, Array<FlightModel>::class.java).toList()
                Log.d("result",result!!)
                flightLiveData.postValue(myDataList) // Update the LiveData with the result
            } catch (e: Exception) {
                // Handle network request errors here
            }
        }
    }

    fun getflightLiveData() : LiveData<List<FlightModel>>{
        return flightLiveData
    }

}