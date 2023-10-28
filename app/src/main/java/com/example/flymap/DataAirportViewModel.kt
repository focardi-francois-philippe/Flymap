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
    private val clickedFlightLiveData = MutableLiveData<Flight>()

    val activityDataAirport = MutableLiveData<DataAirportForFlightCell>()
    private val BASE_URL = "https://opensky-network.org/api"
    private val REQUEST_DEPART_URL = BASE_URL+"/flights/departure"
    private val REQUEST_ARRIVE_URL = BASE_URL+"/flights/arrival"

    // Function to make the network request using viewModelScope
     fun fetchDataFromApDepartArrive(isArrive: Boolean, airport: String, debut: Long, fin: Long) {
        Log.d("AZERTY","ok")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("AZERTY2","ok")

                val req = if(isArrive) REQUEST_ARRIVE_URL else REQUEST_DEPART_URL
                val result =  RequestManager.getSuspended(
                    "$req?airport=$airport&begin=$debut&end=$fin",
                    HashMap())
                val gson = Gson()
                val myDataList: List<FlightModel> =  gson.fromJson(result, Array<FlightModel>::class.java).toList()
                Log.d("result",result!!)
                flightLiveData.postValue(myDataList) // Update the LiveData with the result
            } catch (e: Exception) {
                Log.e("flightList", e.localizedMessage)
                // Handle network request errors here
            }
        }
    }

    fun setActivityDataAirport(dataAirportForFlightCell: DataAirportForFlightCell)
    {
        activityDataAirport.postValue(dataAirportForFlightCell)
    }
    fun getActivityDataAirport() : LiveData<DataAirportForFlightCell>
    {
        return activityDataAirport
    }

    fun getClickedFlightLiveData() : LiveData<Flight>
    {
        return clickedFlightLiveData
    }
    fun  setClickedFlightLiveData(flight: Flight)
    {
        clickedFlightLiveData.postValue(flight)
        Log.d("LIVE DATAAA","DATJAHAHF")
    }
    fun getflightLiveData() : LiveData<List<FlightModel>>{
        return flightLiveData
    }

}