package com.example.flymap

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.slider.Slider
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var viewModelMain :MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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



        viewModelMain.getBeginDateLiveData().observe(this) {
            beginDateLabel.text = Utils.dateToString(it.time)
        }


        viewModelMain.getEndDateLiveData().observe(this) {
            intervalTextView.text = "To : " + Utils.dateToString(it.time)
        }

        beginDateLabel.setOnClickListener { showDatePickerDialog(MainViewModel.DateType.BEGIN) }
        //endDateLabel.setOnClickListener { showDatePickerDialog(MainViewModel.DateType.END) }

        slider.addOnChangeListener {slider, value, fromUser ->
            val calendar = viewModelMain.getBeginDateLiveData().value
            val copieCalendar = Calendar.getInstance()
            copieCalendar.timeInMillis = calendar!!.timeInMillis
            val maxDate = Calendar.getInstance()
            copieCalendar.add(Calendar.DAY_OF_MONTH,value.toInt())
            if (copieCalendar<= maxDate)
            {
                viewModelMain.setIntervalJour(value.toInt())
                viewModelMain.updateCalendarLiveData(MainViewModel.DateType.END,copieCalendar)
                slider.value = value
            }else
            {
                slider.value = value -1
                Toast.makeText(this, "Impossible de depasser la limite", Toast.LENGTH_SHORT).show()
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

            val intent = Intent(this, FlightsListView::class.java).apply {
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
            } else {
                // Affichez un message d'erreur ou ne faites rien
                Toast.makeText(this, "Date non valide (doit être à partir d'hier)", Toast.LENGTH_SHORT).show()
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





}
