package com.example.flymap

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class FlightsListView : AppCompatActivity() {

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

        if (airport != null) {
            viewModelFlight.setActivityDataAirport(DataAirportForFlightCell(isArrive,airport.city,airport.code))
        }
        viewModelFlight.fetchDataFromApDepartArrive(isArrive,airport!!.icao, departureTs, arrivalTs)
        /*
                viewModelFlight.getflightLiveData().observe(this, Observer {
                    val dataset = it
                    val flights = mutableListOf<Flight>()

                    Log.d("flightList", "BIBI")
                }

                    dataset.forEach {
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
                           Utils.convertTimestampToDate(it.firstSeen),
                           Utils.convertTimestampToDate(it.lastSeen),
                           Utils.convertTimestampToDuration(it.lastSeen, it.firstSeen),
                           Utils.getCurrentProgress(it.lastSeen, it.firstSeen)
                        )
                        flights.add(flight)
                    }

                    Log.d("flightList", flights.size.toString())
                    val customAdapter = FlightsListViewAdapter(flights)

                    val recyclerView: RecyclerView = findViewById(R.id.flightsList)
                    recyclerView.adapter = customAdapter
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    })*/
    }
}