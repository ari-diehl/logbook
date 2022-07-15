package badvilbel.ws20st.frontend.maintenance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import badvilbel.ws20st.frontend.R
import badvilbel.ws20st.frontend.models.vehicle.VehicleResponse

class VehicleAdapter(
    private val vehicles: List<VehicleResponse>,
    private val onItemClick: (position: Int) -> Unit
) :
    RecyclerView.Adapter<VehicleAdapter.ViewHolder>() {

    class ViewHolder(view: View, private val onItemClick: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(view) {
        val manufacturerModel: TextView

        init {
            view.setOnClickListener {
                onItemClick(adapterPosition)
            }

            manufacturerModel = view.findViewById(R.id.tvVIManufacturerModel)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.vehicle_item, viewGroup, false)

        return ViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val vehicle = vehicles[position]
        viewHolder.manufacturerModel.text = "${vehicle.manufacturer} ${vehicle.model}"
    }

    override fun getItemCount() = vehicles.size

}