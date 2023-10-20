package com.example.flymap

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by sergio on 07/11/2021
 * All rights reserved GoodBarber
 */
@Parcelize
data class Airport(
    val code: String,
    val lat: String,
    val lon: String,
    val name: String,
    val city: String,
    val state: String,
    val country: String,
    val woeid: String,
    val tz: String,
    val phone: String,
    val type: String,
    val email: String,
    val url: String,
    val runway_length: String,
    val elev: String,
    val icao: String,
    val direct_flights: String,
    val carriers: String
):Parcelable {
    fun getFormattedName(): String {
        return "$code $city ($country)"
    }

    override fun toString(): String {
        return getFormattedName()
    }
}