package badvilbel.ws20st.frontend.maintenance

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import badvilbel.ws20st.frontend.R
import badvilbel.ws20st.frontend.models.vehicle.VehicleResponse
import badvilbel.ws20st.frontend.models.vehicle_comment.VehicleCommentResponse
import badvilbel.ws20st.frontend.networking.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class VehicleDetailsActivity : AppCompatActivity() {
    lateinit var tvLicensePlate: TextView
    lateinit var tvManufacturer: TextView
    lateinit var tvModel: TextView
    lateinit var tvMileage: TextView
    lateinit var tvComments: TextView

    lateinit var vehicle: VehicleResponse

    private var vehicleComments: ArrayList<VehicleCommentResponse> = arrayListOf()

    lateinit var token: String

    lateinit var rvVehicleComments: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_details)

        tvComments = findViewById(R.id.tvVDComments)

        tvComments.visibility = View.GONE

        token =
            getSharedPreferences("employee", Context.MODE_PRIVATE).getString("access_token", "")
                .toString()

        vehicle = intent.getSerializableExtra("vehicle") as VehicleResponse

        rvVehicleComments = findViewById(R.id.rvVehicleComments)

        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.readVehicleComments(vehicle.id)

            if (response.isSuccessful && response.body() != null) {
                vehicleComments = (response.body() as ArrayList<VehicleCommentResponse>?)!!

                if (vehicleComments.isNotEmpty()) {
                    tvComments.visibility = View.VISIBLE
                }

                rvVehicleComments.layoutManager = LinearLayoutManager(this@VehicleDetailsActivity)
                rvVehicleComments.adapter =
                    VehicleCommentAdapter(
                        vehicleComments,
                        token
                    ) { position -> onItemClick(position) }
                rvVehicleComments.addItemDecoration(
                    DividerItemDecoration(
                        rvVehicleComments.context,
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
        }

        tvLicensePlate = findViewById(R.id.tvVDLicensePlate)
        tvManufacturer = findViewById(R.id.tvVDManufacturer)
        tvModel = findViewById(R.id.tvVDModel)
        tvMileage = findViewById(R.id.tvVDMileage)

        tvLicensePlate.text = vehicle.licensePlate
        tvManufacturer.text = vehicle.manufacturer
        tvModel.text = vehicle.model
        tvMileage.text = vehicle.mileage.toString()
    }

    fun deleteVehicle(view: View) {
        GlobalScope.launch(Dispatchers.Main) {
            val token =
                getSharedPreferences("employee", Context.MODE_PRIVATE).getString("access_token", "")

            val response = RetrofitInstance.api.deleteVehicle("Bearer ${token!!}", vehicle.id)

            if (response.isSuccessful && response.body() != null) {
                val toast = Toast.makeText(
                    this@VehicleDetailsActivity,
                    getString(R.string.deleted),
                    Toast.LENGTH_SHORT
                )

                toast.show()

                finish()
            }
        }
    }

    private fun onItemClick(position: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.deleteVehicleComment(vehicleComments[position].id)

            if (response.isSuccessful && response.body() != null) {
                val toast = Toast.makeText(
                    this@VehicleDetailsActivity,
                    getString(R.string.deleted),
                    Toast.LENGTH_SHORT
                )

                toast.show()

                vehicleComments.removeAt(position)

                if (vehicleComments.isEmpty()) {
                    tvComments.visibility = View.GONE
                } else {
                    tvComments.visibility = View.VISIBLE
                }

                rvVehicleComments.adapter?.notifyDataSetChanged()
            }
        }
    }

}