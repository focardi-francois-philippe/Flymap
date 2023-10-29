package com.example.flymap

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class FlightsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flights_list_activity)
        Log.d("OK","TABLETTE")
        val isTablet = findViewById<FragmentContainerView>(R.id.fragment_map_container) != null
        Log.d("OK","TABLETTE PASSER")

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

        viewModelFlight.getClickedFlightLiveData().observe(this, Observer {


            if (!isTablet)
            {
                val fragmentMap = FlightMapFragement()
                this.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_list_container, fragmentMap)
                    .addToBackStack(null)  // Pour permettre la navigation en arrière si nécessaire
                    .commit()
            }

        })
    }
}