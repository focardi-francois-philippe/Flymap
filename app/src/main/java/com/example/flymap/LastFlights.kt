package com.example.flymap

data class LastFlights(
    val icao24: String,
    val firstSeen: Double,
    val estDepartureAirport: String,
    val lastSeen: Double,
    val estArrivalAirport: String,
    val callsign: String,
    val estDepartureAirportHorizDistance: Double,
    val estDepartureAirportVertDistance: Double,
    val estArrivalAirportHorizDistance: Double,
    val estArrivalAirportVertDistance: Double,
    val departureAirportCandidatesCount: Int,
    val arrivalAirportCandidatesCount: Int
)