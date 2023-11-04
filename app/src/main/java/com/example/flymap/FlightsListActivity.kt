package com.example.flymap

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class FlightsListActivity : AppCompatActivity() {

    private lateinit var modalNoConnection :AlertDialog
    private lateinit var networkManager: NetworkManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flights_list_activity)
        createNonCancelableAlertDialog()
        networkManager = NetworkManager(this)
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

        networkManager.observe(this){


            Log.d("VALUE IT",it!!.toString())
            Log.d("VALUE isShowing",modalNoConnection.isShowing.toString())
            if(!it)
            {
                Log.d("VALUE IT LOST",it!!.toString())

                if (!modalNoConnection.isShowing)
                    modalNoConnection.show()
            }else
            {
                if (modalNoConnection.isShowing)
                    modalNoConnection.dismiss()
            }
        }
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
    private fun createNonCancelableAlertDialog() {
        Log.d("CREATION MODAL","CREATION MODAL")
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(
            "Perte de la connexion")
        alertDialogBuilder.setMessage("Vous devez disposer d'une connexion internet pour utiliser l'application")

        // Bouton "OK"

        // Désactivez la possibilité de fermeture de la boîte de dialogue
        alertDialogBuilder.setCancelable(false)
        modalNoConnection = alertDialogBuilder.create()



    }
}