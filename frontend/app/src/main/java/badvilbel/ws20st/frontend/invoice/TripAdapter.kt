package badvilbel.ws20st.frontend.invoice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import badvilbel.ws20st.frontend.R
import badvilbel.ws20st.frontend.Utils
import badvilbel.ws20st.frontend.models.trip.TripResponse

class TripAdapter(
    private val trips: List<TripResponse>,
    private val onItemClick: (position: Int) -> Unit
) :
    RecyclerView.Adapter<TripAdapter.ViewHolder>() {

    class ViewHolder(view: View, private val onItemClick: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(view) {
        val departureLocality: TextView
        val arrivalLocality: TextView
        val start: TextView
        val end: TextView

        init {
            view.setOnClickListener {
                onItemClick(adapterPosition)
            }

            departureLocality = view.findViewById(R.id.tvTIDepartureLocality)
            arrivalLocality = view.findViewById(R.id.tvTIArrivalLocality)
            start = view.findViewById(R.id.tvTIStart)
            end = view.findViewById(R.id.tvTIEnd)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.trip_item, viewGroup, false)

        return ViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.departureLocality.text = trips[position].departureLocality
        viewHolder.arrivalLocality.text = trips[position].arrivalLocality
        viewHolder.start.text = Utils.formatJsonDate(trips[position].start)
        viewHolder.end.text = Utils.formatJsonDate(trips[position].end)
    }

    override fun getItemCount() = trips.size

}