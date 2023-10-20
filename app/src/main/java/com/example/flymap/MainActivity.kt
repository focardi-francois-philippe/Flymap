package com.example.flymap

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Switch
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var viewModelMain :MainViewModel
    @SuppressLint("MissingInflatedId", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModelMain = ViewModelProvider(this).get(MainViewModel::class.java)
        val searchButton = findViewById<Button>(R.id.search_button)
        //val airportsList = Utils.generateAirportList()

        val spinnerAirport = findViewById<Spinner>(R.id.spinnerAirport)
        val spinnerAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,viewModelMain.getAirportLiveData().value!!)


        val switchDepartArrive = findViewById<Switch>(R.id.depart_arrive)

        spinnerAirport.adapter = spinnerAdapter


        searchButton.setOnClickListener{
            val AirportIndex = spinnerAirport.selectedItemPosition
            val airportSelected = viewModelMain.getAirportLiveData().value!![AirportIndex]


            val isArrive = switchDepartArrive.isChecked
            val debut = 1697631222
            val fin = 1697717622

            val intent = Intent(this,FlightsListView::class.java).apply {
                putExtra("isArrive",isArrive)
                putExtra("debut",debut)
                putExtra("fin",fin)
                putExtra("airport",airportSelected)
            }
            startActivity(intent)
           // DataAirportViewModel().fetchDataFromApi()
        }



    }
}