package com.example.flymap

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FlyInfosViewAdapter(private val dataSet: List<Pair<String, Any>>) :
    RecyclerView.Adapter<FlyInfosViewAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val infoTextView: TextView
        val infoTextView2: TextView

        init {
            // Define click listener for the ViewHolder's View
            infoTextView = view.findViewById(R.id.infoTextView)
            infoTextView2 = view.findViewById(R.id.infoTextView2)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.info_list_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.infoTextView.text = dataSet[position].first
        viewHolder.infoTextView2.text = dataSet[position].second.toString()

        if (position % 2 == 1) {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#E8F8FF"))
        }
        else {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}