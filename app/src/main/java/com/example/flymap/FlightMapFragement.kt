package com.example.flymap

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Random

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
        return inflater.inflate(R.layout.flight_map_fragement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("map fragement OK2","map fragement OK2")
        val button = view.findViewById<FloatingActionButton>(R.id.button)
        val infoList = view.findViewById<RecyclerView>(R.id.infoList)
        val lastFlightsList = view.findViewById<RecyclerView>(R.id.lastFlights)
        mapView = view.findViewById<MapView>(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(lastFlightsList)

        val viewModelDataMapFlight = ViewModelProvider(requireActivity()).get(MapViewModel::class.java)

        val viewModelFlight = ViewModelProvider(requireActivity()).get(DataAirportViewModel::class.java)
        viewModelFlight.getClickedFlightLiveData().observe(viewLifecycleOwner, Observer {

            // Liste de coordonnées de points de trajet (LatLng)
            Log.d("OK ",it.icao24)
            viewModelDataMapFlight.fetchDataFromIcao24(it.icao24, requireContext())
            viewModelDataMapFlight.fetchFlyStateByIcao(it.icao24)
            viewModelDataMapFlight.fetchLastFlightsByIcao(it.icao24)

            //viewModelDataMapFlight.fetchDataFromIcao24(it.icao24)
        })
        viewModelDataMapFlight.getMapFlightData().observe(viewLifecycleOwner, Observer {
            Log.d("OKBGGG", "OKKKK")
            val lstLatLng = ArrayList<LatLng>()

            for(el in it.path) {
                lstLatLng.add(LatLng(el.get(1).toString().toDouble(), el.get(2).toString().toDouble()))
            }
            viewModelDataMapFlight.setLatLngLstForMap(lstLatLng)
        })

        button.setOnClickListener { _ ->
            viewModelDataMapFlight.getFlyStateData().observe(viewLifecycleOwner, Observer {
                if (it.states.isEmpty())
                    return@Observer

                val latLng = LatLng(it.states.first().latitude, it.states.first().longitude)
                googleMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title("Fly")
                )
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))

                val dataset: List<Pair<String, Any>> = it.states.first().serializeToMap().toList()
                val infoAdapter = FlyInfosViewAdapter(dataset)
                infoList.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
                infoList.adapter = infoAdapter
                toggleViewVisibility(infoList)
            })
        }

        viewModelDataMapFlight.getLastFlightsData().observe(viewLifecycleOwner, Observer {
            val dataset: List<LastFlights> = it.toList()
            val infoAdapter = LastFlightsViewAdapter(dataset)
            Log.d("dataset", dataset.toString())
            lastFlightsList.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            lastFlightsList.adapter = infoAdapter
            toggleViewVisibility(lastFlightsList)
        })
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val viewModelDataMapFlight = ViewModelProvider(requireActivity()).get(MapViewModel::class.java)
        viewModelDataMapFlight.getLatLngLstForMap().observe(viewLifecycleOwner,Observer{

            val color = intArrayOf(Color.BLUE,Color.BLACK,Color.GREEN)
            Log.d("OK MAP ICI","ON DESSINE ICI")
            // Créez une PolylineOptions pour représenter la ligne du trajet
            val polylineOptions = PolylineOptions()
                .addAll(it)  // Ajoutez la liste de points au trajet
                .color(color.get(Random().nextInt(3)))      // Couleur de la ligne
                .width(20f)              // Épaisseur de la ligne en pixels

            // Ajoutez la ligne du trajet à la carte
            googleMap.addPolyline(polylineOptions)
        })
    }

    fun toggleViewVisibility(view: View) {
        if (view.visibility == View.INVISIBLE)
            view.visibility = View.VISIBLE
        else if (view.visibility == View.VISIBLE)
            view.visibility = View.INVISIBLE
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