package com.example.flymap

data class DataPathFlightMap(
    val time:Long,
    val latitude:Number?,
    val longitude:Number?,
    val baro_altitude:Number?,
    val true_track:Number?,
    val on_ground:Boolean?
)
