package com.example.flymap

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class FlightsListView : AppCompatActivity() {
    private lateinit var viewModelFlight : DataAirportViewModel

    fun convertTimestampToDate(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val date = Date(timestamp * 1000) // Convert seconds to milliseconds

        return dateFormat.format(date)
    }

    fun convertTimestampToDuration(d1: Long, d2: Long): String {
        val date1 = Date(d1 * 1000)
        val date2 = Date(d2 * 1000)
        val diff = date1.time - date2.time

        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60

        return if (hours >= 1)
            hours.toString() + "h" + (minutes % 60).toString().padStart(2, '0')
        else
            minutes.toString() + "m"
    }

    fun getCurrentProgress(arrival: Long, depart: Long): Float {
        val curDate = Calendar.getInstance().time.seconds

        val arrivalDate = Date(arrival)
        val departDate = Date(depart)

        val progress: Float = (arrivalDate.time - curDate.toFloat()) / (arrivalDate.time - departDate.time)
        return if (progress >= 0) progress else 0f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flights_list_view)



        //val flyNumber = intent.getStringExtra("flyNumber")
        //val fromAirport = intent.getParcelableExtra<Airport>("fromAirport")
        val airport = intent.getParcelableExtra<Airport>("airport")
        val departureTs = intent.getLongExtra("debut",0)
        val arrivalTs = intent.getLongExtra("fin",0)
        val isArrive = intent.getBooleanExtra("isArrive",false)

        val viewModelFlight = ViewModelProvider(this).get(DataAirportViewModel::class.java)

        Log.d("airport",airport.toString())


        viewModelFlight.fetchDataFromApDepartArrive(isArrive,airport!!.icao, departureTs, arrivalTs)

        viewModelFlight.getflightLiveData().observe(this, Observer {
            val dataset = viewModelFlight.getflightLiveData()
            val flights = mutableListOf<Flight>()

            Log.d("flightList", "BIBI")

            dataset.value?.forEach {
                val fromAirport = if (isArrive) it.estDepartureAirport else airport.city
                val fromAirportCode = if (isArrive) it.estDepartureAirport else airport.code
                val toAirport = if (isArrive) airport.city else it.estArrivalAirport
                val toAirportCode = if (isArrive) airport.code else it.estDepartureAirport
                val flight = Flight(
                    it.callsign,
                    fromAirport,
                    fromAirportCode,
                    toAirport,
                    toAirportCode,
                    convertTimestampToDate(it.firstSeen),
                    convertTimestampToDate(it.lastSeen),
                    convertTimestampToDuration(it.lastSeen, it.firstSeen),
                    getCurrentProgress(it.lastSeen, it.firstSeen)
                )
                flights.add(flight)
            }

            Log.d("flightList", flights.size.toString())
            val customAdapter = FlightsListViewAdapter(flights)

            val recyclerView: RecyclerView = findViewById(R.id.flightsList)
            recyclerView.adapter = customAdapter
            recyclerView.layoutManager = LinearLayoutManager(this)
            })
    }
}