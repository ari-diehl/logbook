package badvilbel.ws20st.frontend.invoice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import badvilbel.ws20st.frontend.R
import badvilbel.ws20st.frontend.models.employee.EmployeeResponse

class UserAdapter(
    private val users: List<EmployeeResponse>,
    private val onItemClick: (position: Int) -> Unit
) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(view: View, private val onItemClick: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(view) {
        val name: TextView

        init {
            view.setOnClickListener {
                onItemClick(adapterPosition)
            }

            name = view.findViewById(R.id.tvUIName)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.user_item, viewGroup, false)

        return ViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.name.text = "${users[position].firstName} ${users[position].lastName}"
    }

    override fun getItemCount() = users.size

}