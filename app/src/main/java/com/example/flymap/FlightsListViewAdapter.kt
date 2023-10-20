package com.example.flymap

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class FlightsListViewAdapter(private val dataSet: List<FlightModel>) :
    RecyclerView.Adapter<FlightsListViewAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val flighNumberTextView: TextView
        val fromTextView: TextView
        val toTextView: TextView
        val flyTimeTextView: TextView

        init {
            // Define click listener for the ViewHolder's View
            flighNumberTextView = view.findViewById(R.id.flighNumberTextView)
            fromTextView = view.findViewById(R.id.fromTextView)
            toTextView = view.findViewById(R.id.toTextView)
            flyTimeTextView = view.findViewById(R.id.flyTimeTextView)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.text_row_item, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val flightInfos = dataSet[position]

        /*
        val flyNumber = "Flight " + flightInfos.callsign
        val fromAirport = flightInfos[1]
        val fromAirportAb = flightInfos[2]
        val departureDatetime = flightInfos[3]
        val toAirport = flightInfos[4]
        val toAirportAb = flightInfos[5]
        val arrivalDatetime = flightInfos[6]
        val flyTime = flightInfos[7].replace(':', 'h')

        val textFrom = "From: $fromAirport ($fromAirportAb) $departureDatetime"
        val textTo = "To: $toAirport ($toAirportAb) $arrivalDatetime"

        viewHolder.flighNumberTextView.text = flyNumber
        viewHolder.fromTextView.text = textFrom
        viewHolder.toTextView.text = textTo
        viewHolder.flyTimeTextView.text = flyTime
         */

        if (position % 2 == 1) {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#E8F8FF"))
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}