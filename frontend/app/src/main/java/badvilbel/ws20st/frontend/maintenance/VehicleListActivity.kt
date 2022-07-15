package badvilbel.ws20st.frontend.maintenance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import badvilbel.ws20st.frontend.R
import badvilbel.ws20st.frontend.models.vehicle.VehicleResponse
import badvilbel.ws20st.frontend.networking.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class VehicleListActivity : AppCompatActivity() {
    var vehicles: List<VehicleResponse> = listOf()

    lateinit var rvVehicles: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_list)

        rvVehicles = findViewById(R.id.rvVehicles)

        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.readVehicles()

            if (response.isSuccessful && response.body() != null) {
                vehicles = response.body()!!

                rvVehicles.layoutManager = LinearLayoutManager(this@VehicleListActivity)
                rvVehicles.adapter =
                    VehicleAdapter(vehicles) { position -> onItemClick(position) }
                rvVehicles.addItemDecoration(
                    DividerItemDecoration(
                        rvVehicles.context,
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
        }
    }

    private fun onItemClick(position: Int) {
        val intent = Intent(this, VehicleDetailsActivity::class.java)
        intent.putExtra("vehicle", vehicles[position])
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.readVehicles()

            if (response.isSuccessful && response.body() != null) {
                vehicles = response.body()!!
                rvVehicles.adapter =
                    VehicleAdapter(vehicles) { position -> onItemClick(position) }
            }
        }
    }

    fun createVehicle(view: View) {
        startActivity(Intent(this, NewVehicleActivity::class.java))
    }
}