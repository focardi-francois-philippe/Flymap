package com.example.flymap

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class FlightsListViewAdapter(private val dataSet: List<Flight>, val cellClickListener: OnCellClickListener) :
    RecyclerView.Adapter<FlightsListViewAdapter.ViewHolder>() {

    interface OnCellClickListener {
        fun onCellClicked(flight: Flight)
    }
    /**
     *
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val flighNumberTextView: TextView
        val fromTextView: TextView
        val toTextView: TextView
        val flyTimeTextView: TextView
        val planeView: ImageView

        init {
            // Define click listener for the ViewHolder's View
            flighNumberTextView = view.findViewById(R.id.flighNumberTextView)
            fromTextView = view.findViewById(R.id.fromTextView)
            toTextView = view.findViewById(R.id.toTextView)
            flyTimeTextView = view.findViewById(R.id.flyTimeTextView)
            planeView = view.findViewById(R.id.plane)
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
        val flight = dataSet[position]

        val flyNumber = "Flight " + flight.flyNumber
        val fromAirport = flight.fromAirport ?: ""
        val fromAirportCode = flight.fromAirportCode
        val departDatetime = flight.departDatetime
        val toAirport = flight.toAirport ?: ""
        val toAirportCode = flight.toAirportCode
        val arrivalDatetime = flight.arrivalDatetime
        val flyTime = flight.flyTime

        val textFrom = "$fromAirport ($fromAirportCode) $departDatetime"
        val textTo = "" +
                "$toAirport ($toAirportCode) $arrivalDatetime"

        viewHolder.flighNumberTextView.text = flyNumber
        viewHolder.fromTextView.text = textFrom
        viewHolder.toTextView.text = textTo
        viewHolder.flyTimeTextView.text = flyTime

        viewHolder.planeView.left += 20 * flight.currentProgress.toInt()
        viewHolder.itemView.setOnClickListener { view->
            cellClickListener.onCellClicked(flight)
            Log.d("TESTTT",flight.toString())
        }
        if (position % 2 == 1) {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#E8F8FF"))
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}