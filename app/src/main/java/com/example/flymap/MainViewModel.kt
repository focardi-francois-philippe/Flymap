package com.example.flymap

import android.icu.util.DateInterval
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Calendar
import java.util.Date

class MainViewModel: ViewModel() {
    private val fromCalendar: MutableLiveData<Calendar> by lazy {
        val c = Calendar.getInstance()
        c.add(Calendar.DAY_OF_MONTH,-1)//Car l'api fonctionne seulement a maximum avec la date de la veille
        MutableLiveData<Calendar>(c)
    }
    private val toCalendar: MutableLiveData<Calendar> by lazy {
        val c = Calendar.getInstance()
        c.add(Calendar.DAY_OF_MONTH,-1)//Car l'api fonctionne seulement a maximum avec la date de la veille
        MutableLiveData<Calendar>(c)
    }

    private val  airportsList = MutableLiveData(Utils.generateAirportList())

    private val intervalJour: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(0)
    }

    enum class DateType {
        BEGIN, END
    }

    fun  getIntervalJour() : LiveData<Int>
    {
        return  intervalJour
    }
    fun  setIntervalJour(interval: Int)
    {
        intervalJour.postValue(interval)
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
