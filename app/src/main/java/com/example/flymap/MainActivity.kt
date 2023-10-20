package com.example.flymap

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val airportsList = Utils.generateAirportList()

        val spinnerAirport = findViewById<Spinner>(R.id.spinnerAirport)
        val spinnerAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,airportsList)

        spinnerAirport.adapter = spinnerAdapter
    }
}