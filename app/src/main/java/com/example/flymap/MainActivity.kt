package com.example.flymap

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.slider.Slider
import java.text.SimpleDateFormat
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var viewModelMain :MainViewModel
    private lateinit var modalNoConnection :AlertDialog
    private lateinit var networkManager: NetworkManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("OK CREATE AVANT MODAL","OK")
        createNonCancelableAlertDialog()
        Log.d("OK CREATE APRES MODAL","OK")
        networkManager = NetworkManager(this)



        val slider = findViewById<Slider>(R.id.slider)
        viewModelMain = ViewModelProvider(this).get(MainViewModel::class.java)
        val searchButton = findViewById<Button>(R.id.search_button)
        //val airportsList = Utils.generateAirportList()
        val intervalTextView = findViewById<TextView>(R.id.to)
        val spinnerAirport = findViewById<Spinner>(R.id.spinnerAirport)
        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            viewModelMain.getAirportLiveData().value!!
        )
        val beginDateLabel = findViewById<TextView>(R.id.from_date)
        //val endDateLabel = findViewById<TextView>(R.id.to_date)

        val switchDepartArrive = findViewById<Switch>(R.id.depart_arrive)

        spinnerAirport.adapter = spinnerAdapter

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

        viewModelMain.getBeginDateLiveData().observe(this) {
            beginDateLabel.text = Utils.dateToString(it.time)
            slider.value = 0F
        }


        viewModelMain.getEndDateLiveData().observe(this) {
            intervalTextView.text = "To : " + Utils.dateToString(it.time)

        }

        beginDateLabel.setOnClickListener { showDatePickerDialog(MainViewModel.DateType.BEGIN) }
        //endDateLabel.setOnClickListener { showDatePickerDialog(MainViewModel.DateType.END) }

        switchDepartArrive.setOnCheckedChangeListener { compoundButton, b ->
            slider.value = 0f
        }

        slider.addOnChangeListener {slider, value, fromUser ->
            val calendar = viewModelMain.getBeginDateLiveData().value
            val copieCalendar = Calendar.getInstance()
            copieCalendar.timeInMillis = calendar!!.timeInMillis
            val maxDate = Calendar.getInstance()

            if (switchDepartArrive.isChecked)
                maxDate.add(Calendar.DAY_OF_MONTH, -1)
            else
                maxDate.add(Calendar.DAY_OF_MONTH, 2)
            copieCalendar.add(Calendar.DAY_OF_MONTH,value.toInt())
            if (copieCalendar<= maxDate) {
                viewModelMain.setIntervalJour(value.toInt())
                viewModelMain.updateCalendarLiveData(MainViewModel.DateType.END,copieCalendar)
                slider.value = value
            }
            else {
                slider.value = value -1
            }

        }
        searchButton.setOnClickListener {
            val AirportIndex = spinnerAirport.selectedItemPosition
            val airportSelected = viewModelMain.getAirportLiveData().value!![AirportIndex]


            val isArrive = switchDepartArrive.isChecked
            val debut: Long = Utils.dateToUnixEpoch(SimpleDateFormat("dd/MM/yy").parse(
                beginDateLabel.text.toString()
            ))
              val fin: Long = Utils.dateToUnixEpoch(SimpleDateFormat("dd/MM/yy").parse(
                  Utils.dateToString(viewModelMain.getEndDateLiveData().value!!.time)
               ))


            val intent = Intent(this, FlightsListActivity::class.java).apply {
                putExtra("isArrive", isArrive)
                putExtra("debut", debut)
                putExtra("fin", fin)
                putExtra("airport", airportSelected)
            }
            startActivity(intent)
            // DataAirportViewModel().fetchDataFromApi()
        }

    }
    private fun showDatePickerDialog(dateType: MainViewModel.DateType) {
        // Date Select Listener.

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_MONTH,-1)

            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)


            val maxDate = Calendar.getInstance()
            maxDate.add(Calendar.DAY_OF_MONTH,-1)


            if (calendar <= maxDate) {

                viewModelMain.updateCalendarLiveData(dateType, calendar)
                viewModelMain.updateCalendarLiveData(MainViewModel.DateType.END,calendar)

            }
        }

        val currentCalendar = if (dateType == MainViewModel.DateType.BEGIN) viewModelMain.getBeginDateLiveData().value else viewModelMain.getEndDateLiveData().value

        val datePickerDialog = DatePickerDialog(
            this,
            dateSetListener,
            currentCalendar!!.get(Calendar.YEAR),
            currentCalendar.get(Calendar.MONTH),
            currentCalendar.get(Calendar.DAY_OF_MONTH)
        )

        // Date maximale (aujourd'hui)
        val today = Calendar.getInstance()
        today.add(Calendar.DAY_OF_MONTH,-1)
        datePickerDialog.datePicker.maxDate = today.timeInMillis

        datePickerDialog.show()
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
