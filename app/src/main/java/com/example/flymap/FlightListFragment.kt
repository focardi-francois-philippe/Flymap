package com.example.flymap

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FlightListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FlightListFragment : Fragment() {
    private lateinit var viewModelFlight : DataAirportViewModel
    lateinit var dataAirportForFlightCell : DataAirportForFlightCell
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelFlight = ViewModelProvider(requireActivity()).get(DataAirportViewModel::class.java)
        viewModelFlight.getActivityDataAirport().observe(viewLifecycleOwner,Observer{
            dataAirportForFlightCell = it
        })
        viewModelFlight.getflightLiveData().observe(viewLifecycleOwner, Observer {
            //findViewById<TextView>(R.id.textView).text = it.toString()

            val flights = mutableListOf<Flight>()

            val isArrive = false
            it.forEach {
                Log.d("OKKK", "OKKKKKKKK")
                val fromAirport =
                    if (isArrive) it.estDepartureAirport else dataAirportForFlightCell.city
                val fromAirportCode =
                    if (isArrive) it.estDepartureAirport else dataAirportForFlightCell.code
                val toAirport =
                    if (isArrive) dataAirportForFlightCell.city else it.estArrivalAirport
                val toAirportCode =
                    if (isArrive) dataAirportForFlightCell.code else it.estDepartureAirport
                val flight = Flight(
                    it.callsign,
                    fromAirport,
                    fromAirportCode,
                    toAirport,
                    toAirportCode,
                    Utils.convertTimestampToDate(it.firstSeen),
                    Utils.convertTimestampToDate(it.lastSeen),
                    Utils.convertTimestampToDuration(it.lastSeen, it.firstSeen),
                    Utils.getCurrentProgress(it.lastSeen, it.firstSeen)
                )
                flights.add(flight)
            }
            Log.d("OKKK2","OKKKKKKKK2")
            flights.forEach({
                Log.d("OKKK",it.toString())
            })

            val customAdapter = FlightsListViewAdapter(flights)
            val recyclerView = view.findViewById<RecyclerView>(R.id.flightsList)
            recyclerView.adapter = customAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            Log.d("OKKK","Apresr recycler")
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flight_list, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FlightListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FlightListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}