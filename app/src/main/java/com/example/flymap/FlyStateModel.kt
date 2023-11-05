package com.example.flymap

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class FlyState(
    val icao24: String,
    val callsign: String,
    val origin_country: String,
    val time_position: Double,
    val last_contact: Double,
    val longitude: Double,
    val latitude: Double,
    val baro_altitude: Double,
    val on_ground: Boolean,
    val velocity: Double,
    val true_track: Double,
    val vertical_rate: Double,
    val sensors: List<Int>?,
    val geo_altitude: Double,
    val squawk: String?,
    val spi: Boolean,
    val category: Double,
) {
    fun serializeToMap(): Map<String, Any> {
        return convert()
    }
    private inline fun <I, reified O> I.convert(): O {
        val gson = Gson()
        val json = gson.toJson(this)
        return gson.fromJson(json, object : TypeToken<O>() {}.type)
    }
}
data class FlyStateModel(
    val time: Int,
    val states: List<FlyState>
)
data class FlyStateModelGson(
    val time: Int,
    val states: List<List<Any>>?
) {
    fun toModel(): FlyStateModel {
        val states = mutableListOf<FlyState>()
        this.states?.forEach {
            val state = FlyState(
                it.get(0) as String,
                it.get(1) as String,
                it.get(2) as String,
                it.get(3) as Double,
                it.get(4) as Double,
                it.get(5) as Double,
                it.get(6) as Double,
                it.get(7) as Double,
                it.get(8) as Boolean,
                it.get(9) as Double,
                it.get(10) as Double,
                it.get(11) as Double,
                listOf(),
                it.get(13) as Double,
                it.get(14) as String?,
                it.get(15) as Boolean,
                it.get(16) as Double,
            )
            states.add(state)
        }
        return FlyStateModel(
            time = time,
            states = states,
        )
    }
}