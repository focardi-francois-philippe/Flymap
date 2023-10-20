package com.example.flymap

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FlightsListView : AppCompatActivity() {
    private lateinit var viewModelFlight : DataAirportViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flights_list_view)



        //val flyNumber = intent.getStringExtra("flyNumber")
        //val fromAirport = intent.getParcelableExtra<Airport>("fromAirport")
        val airport = intent.getParcelableExtra<Airport>("airport")
        val departureDatetime = intent.getIntExtra("debut",0)
        val arrivalDatetime = intent.getIntExtra("fin",0)
        val isArrive = intent.getBooleanExtra("isArrive",false)

        val viewModelFlight = ViewModelProvider(this).get(DataAirportViewModel::class.java)

        Log.d("airport",airport.toString())


        viewModelFlight.fetchDataFromApDepartArrivei(isArrive,airport!!.code,departureDatetime,arrivalDatetime)



        viewModelFlight.getflightLiveData().observe(this, Observer {
            val dataset = viewModelFlight.getflightLiveData()
            val customAdapter = FlightsListViewAdapter(dataset.value!!)

            val recyclerView: RecyclerView = findViewById(R.id.flightsList)
            recyclerView.adapter = customAdapter
            recyclerView.layoutManager = LinearLayoutManager(this)
            })
        /*
        if (listOfNotNull(fromAirport?.city, fromAirport?.code).any() &&
            listOfNotNull(toAirport?.city, toAirport?.code).any() &&
            departureDatetime != null && arrivalDatetime != null &&
            flyNumber != null) {
            val fromCity = fromAirport?.city!!
            val fromCode = fromAirport.code
            val toCity = toAirport?.city!!
            val toCode = toAirport.code

            val flyingTime = "9h20" // TODO

            val d = listOf(flyNumber, fromCity, fromCode, departureDatetime, toCity, toCode, arrivalDatetime, flyingTime)
            dataset.add(d)
        }
*/

    }
}