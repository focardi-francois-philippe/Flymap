package com.example.flymap

data class DataMapFlight(
    val icao24: String,
    val callsign: String?,
    val startTime: Long,
    val endTime: Long,
    val path: List<DataPathFlightMap>
)
