package com.example.flymap

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import kotlin.math.log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FlightMapFragement.newInstance] factory method to
 * create an instance of this fragment.
 */
class FlightMapFragement : Fragment(), OnMapReadyCallback {
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        Log.d("map fragement OK","map fragement OK")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flight_map_fragement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("map fragement OK2","map fragement OK2")
        mapView = view.findViewById<MapView>(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        val viewModelDataMapFlight = ViewModelProvider(requireActivity()).get(MapViewModel::class.java)

        val viewModelFlight = ViewModelProvider(requireActivity()).get(DataAirportViewModel::class.java)
        viewModelFlight.getClickedFlightLiveData().observe(viewLifecycleOwner, Observer {

            // Liste de coordonnées de points de trajet (LatLng)
            Log.d("OK ","OK ")
            viewModelDataMapFlight.fetchDataFromIcao24(it.icao24)



            //viewModelDataMapFlight.fetchDataFromIcao24(it.icao24)
        })
        viewModelDataMapFlight.getMapFlightData().observe(viewLifecycleOwner, Observer {
            Log.d("OKBGGG", "OKKKK")
            val pointsOfRoute = listOf(
                LatLng(37.6147, -122.3655),
                LatLng(37.6155, -122.3665),
                LatLng(37.6160, -122.3670),
                // Ajoutez d'autres coordonnées de points de trajet ici...
            )

            // Créez une PolylineOptions pour représenter la ligne du trajet
            val polylineOptions = PolylineOptions()
                .addAll(pointsOfRoute)  // Ajoutez la liste de points au trajet
                .color(Color.BLUE)      // Couleur de la ligne
                .width(5f)              // Épaisseur de la ligne en pixels

            // Ajoutez la ligne du trajet à la carte
            googleMap.addPolyline(polylineOptions)

        })
    }
    override fun onMapReady(map: GoogleMap) {
        googleMap = map


    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FlightMapFragement.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FlightMapFragement().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}