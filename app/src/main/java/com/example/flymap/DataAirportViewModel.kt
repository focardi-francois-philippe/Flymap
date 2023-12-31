package com.example.flymap

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataAirportViewModel : ViewModel() {

    val flightLiveData = MutableLiveData<List<FlightModel>>()
    private val clickedFlightLiveData = MutableLiveData<Flight>()

    val activityDataAirport = MutableLiveData<DataAirportForFlightCell>()
    private val BASE_URL = "https://opensky-network.org/api"
    private val REQUEST_DEPART_URL = BASE_URL+"/flights/departure"
    private val REQUEST_ARRIVE_URL = BASE_URL+"/flights/arrival"

    // Function to make the network request using viewModelScope
     fun fetchDataFromApDepartArrive(isArrive: Boolean, airport: String, debut: Long, fin: Long, context: Context? = null) {
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
                if (context != null) {
                    val gson = Gson()
                    val result = Utils.getJsonDataFromAsset(context, "flights.json")
                    val myDataList: List<FlightModel> =
                        gson.fromJson(result, Array<FlightModel>::class.java).toList()
                    withContext(Dispatchers.Main) {
                        Utils.showShortToast(context,"Lecture fichier flights")
                    }
                    flightLiveData.postValue(myDataList)


                }
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