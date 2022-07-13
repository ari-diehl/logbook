package badvilbel.ws20st.frontend.invoice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import badvilbel.ws20st.frontend.R
import badvilbel.ws20st.frontend.models.trip.TripResponse

class TripAdapter(private val trips: List<TripResponse>) :
    RecyclerView.Adapter<TripAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val locationFrom: TextView
        val locationTo: TextView

        init {
            locationFrom = view.findViewById(R.id.tvTILocationFrom)
            locationTo = view.findViewById(R.id.tvTILocationTo)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.trip_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.locationFrom.text = trips[position].departureLocality
        viewHolder.locationTo.text = trips[position].arrivalLocality
    }

    override fun getItemCount() = trips.size

}