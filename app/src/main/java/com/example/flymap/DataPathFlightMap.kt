package com.example.flymap

data class DataPathFlightMap(
    val time:Int,
    val latitude:Float?,
    val longitude:Float?,
    val baro_altitude:Float?,
    val true_track:Float?,
    val on_ground:Boolean
)
