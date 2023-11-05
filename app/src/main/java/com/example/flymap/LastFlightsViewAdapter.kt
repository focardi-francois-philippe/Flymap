package com.example.flymap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class LastFlightsViewAdapter(private val dataSet: List<LastFlights>) :
    RecyclerView.Adapter<LastFlightsViewAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val aeroDepart: TextView
        val aeroArrival: TextView
        val departDate: TextView
        val arrivalDate: TextView
        val flyDuration: TextView
        val flyNumber: TextView

        init {
            // Define click listener for the ViewHolder's View
            aeroDepart = view.findViewById(R.id.aeroDepart)
            aeroArrival = view.findViewById(R.id.aeroArrival)
            departDate = view.findViewById(R.id.departDate)
            arrivalDate = view.findViewById(R.id.arrivalDate)
            flyDuration = view.findViewById(R.id.flyDuration)
            flyNumber = view.findViewById(R.id.flyNumber)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.last_flights_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.aeroDepart.text = dataSet[position].estDepartureAirport
        viewHolder.aeroArrival.text = dataSet[position].estArrivalAirport
        viewHolder.departDate.text = toDate(dataSet[position].firstSeen.toLong())
        viewHolder.arrivalDate.text = toDate(dataSet[position].lastSeen.toLong())
        viewHolder.flyDuration.text = toDuration(dataSet[position].lastSeen.toLong(),
            dataSet[position].firstSeen.toLong())
        viewHolder.flyNumber.text = dataSet[position].callsign
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    fun toDate(time: Long): String {
        val date = Date(time * 1000L) // *1000 is to convert seconds to milliseconds
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm"); // the format of your date
        sdf.timeZone = TimeZone.getTimeZone("GMT+1");
        return sdf.format(date);
    }

    fun toDuration(time1: Long, time2: Long): String {
        val diff = time1 - time2
        val minutes = diff / 60
        val hours = minutes / 60
        //val days = hours / 24

        return if (hours > 0) {
            "${hours}h${minutes}"
        } else {
            "${minutes}m"
        }
    }
}