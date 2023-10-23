package com.example.flymap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Calendar
import java.util.Date

class MainViewModel: ViewModel() {
    private val fromCalendar: MutableLiveData<Calendar> by lazy {
        MutableLiveData<Calendar>(Calendar.getInstance())
    }
    private val toCalendar: MutableLiveData<Calendar> by lazy {
        MutableLiveData<Calendar>(Calendar.getInstance())
    }

    private val  airportsList = MutableLiveData(Utils.generateAirportList())



    enum class DateType {
        BEGIN, END
    }

    fun getBeginDateLiveData(): LiveData<Calendar>{
        return fromCalendar
    }

    fun getEndDateLiveData(): LiveData<Calendar>{
        return toCalendar
    }

    fun updateCalendarLiveData(dateType: DateType, calendar: Calendar){
        if(dateType == DateType.BEGIN) fromCalendar.value = calendar else toCalendar.value = calendar
    }


    fun getAirportLiveData(): LiveData<List<Airport>>
    {
        return airportsList
    }
}
