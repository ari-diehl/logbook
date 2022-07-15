package badvilbel.ws20st.frontend.maintenance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import badvilbel.ws20st.frontend.R
import badvilbel.ws20st.frontend.Utils
import badvilbel.ws20st.frontend.models.vehicle_comment.VehicleCommentResponse
import badvilbel.ws20st.frontend.networking.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class VehicleCommentAdapter(
    private val vehicleComments: List<VehicleCommentResponse>,
    private val token: String,
    private val onItemClick: (position: Int) -> Unit
) :
    RecyclerView.Adapter<VehicleCommentAdapter.ViewHolder>() {

    class ViewHolder(view: View, private val onItemClick: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(view) {
        val employeeName: TextView
        val datetime: TextView
        val text: TextView

        init {
            view.findViewById<ImageView>(R.id.ivVCDelete).setOnClickListener {
                onItemClick(adapterPosition)
            }

            employeeName = view.findViewById(R.id.tvVCEmployeeName)
            datetime = view.findViewById(R.id.tvVCDatetime)
            text = view.findViewById(R.id.tvVCText)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.vehicle_comment_item, viewGroup, false)

        return ViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val vehicleComment = vehicleComments[position]

        GlobalScope.launch(Dispatchers.Main) {
            val response =
                RetrofitInstance.api.readEmployee("Bearer ${token!!}", vehicleComment.employeeId)

            if (response.isSuccessful && response.body() != null) {
                val employee = response.body()

                viewHolder.employeeName.text =
                    "${employee!!.firstName} ${employee.lastName}"
                viewHolder.datetime.text = Utils.formatJsonDate(vehicleComment.datetime)
                viewHolder.text.text = vehicleComment.text
            }
        }
    }

    override fun getItemCount() = vehicleComments.size

}