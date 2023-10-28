package com.example.flymap

data class Flight(
    val icao24: String,
    val flyNumber: String,
    val fromAirport: String?,
    val fromAirportCode: String?,
    val toAirport: String?,
    val toAirportCode: String?,
    val departDatetime: String,
    val arrivalDatetime: String,
    val flyTime: String,
    val currentProgress: Float,
)
