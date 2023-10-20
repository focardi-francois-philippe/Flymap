package com.example.flymap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FlightsListView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flights_list_view)

        val d1 = arrayOf("AF098765", "Bastia", "BIA", "18/10/23 19:05", "New-York", "JFK", "19/10/23 7:05", "9:10")
        val d2 = arrayOf("AF098765", "Bastia", "BIA", "18/10/23 19:05", "New-York", "JFK", "19/10/23 7:05", "9:10")
        val d3 = arrayOf("AF098765", "Bastia", "BIA", "18/10/23 19:05", "New-York", "JFK", "19/10/23 7:05", "9:10")
        val d4 = arrayOf("AF098765", "Bastia", "BIA", "18/10/23 19:05", "New-York", "JFK", "19/10/23 7:05", "9:10")
        val d5 = arrayOf("AF098765", "Bastia", "BIA", "18/10/23 19:05", "New-York", "JFK", "19/10/23 7:05", "9:10")

        val dataset = arrayOf(d1, d2, d3, d4, d5)
        val customAdapter = FlightsListViewAdapter(dataset)

        val recyclerView: RecyclerView = findViewById(R.id.flightsList)
        recyclerView.adapter = customAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}