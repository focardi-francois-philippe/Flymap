package com.example.flymap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Date

class MainViewModel: ViewModel() {
    private val fromCalendar: MutableLiveData<Date> by lazy {
        MutableLiveData<Date>()
    }
    private val toCalendar: MutableLiveData<Date> by lazy {
        MutableLiveData<Date>()
    }

    private val  airportsList = MutableLiveData(Utils.generateAirportList())

    fun  updateDateFromCalendar(date: Date)
    {
        fromCalendar.value = date
    }
    fun  updateDateToCalendar(date: Date)
    {
        toCalendar.value = date
    }

    fun getFromCalendar(): LiveData<Date> {
        return fromCalendar
    }

    fun getToCalendar() : LiveData<Date> {
        return toCalendar
    }


    fun getAirportLiveData(): LiveData<List<Airport>>
    {
        return airportsList
    }
}