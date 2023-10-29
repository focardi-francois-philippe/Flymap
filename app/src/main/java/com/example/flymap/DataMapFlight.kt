package com.example.flymap

import com.google.gson.annotations.SerializedName

data class DataMapFlight(
    val icao24: String,
    val callsign: String?,
    val startTime: Long,
    val endTime: Long,
    //@SerializedName("path") val path: List<List<DataPathFlightMap>>
    val path: List<List<Any>>
)
