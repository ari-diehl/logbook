package badvilbel.ws20st.frontend.driver

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import badvilbel.ws20st.frontend.R
import badvilbel.ws20st.frontend.networking.RetrofitInstance
import badvilbel.ws20st.frontend.models.trip.TripCreate
import badvilbel.ws20st.frontend.models.vehicle.VehicleUpdate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TripFinishedActivity : AppCompatActivity() {
    private lateinit var tvStart: TextView
    private lateinit var tvEnd: TextView
    private lateinit var tvLocationFrom: TextView
    private lateinit var tvLocationTo: TextView
    private lateinit var tvDistanceCovered: TextView
    private lateinit var tvError: TextView

    private lateinit var trip: TripCreate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_finished)

        tvStart = findViewById(R.id.tvTFStart)
        tvEnd = findViewById(R.id.tvTFEnd)
        tvLocationFrom = findViewById(R.id.tvTFLocationFrom)
        tvLocationTo = findViewById(R.id.tvTFLocationTo)
        tvDistanceCovered = findViewById(R.id.tvTFDistanceCovered)
        tvError = findViewById(R.id.tvTFError)

        val sharedPref = getSharedPreferences("employee", Context.MODE_PRIVATE)

        with(intent) {
            trip = TripCreate(
                getStringExtra("startJson")!!,
                getStringExtra("endJson")!!,
                sharedPref.getInt("id", 0),
                getStringExtra("vehicleId")!!,
                getStringExtra("locationFrom")!!,
                getStringExtra("locationTo")!!,
                getDoubleExtra("distance", 0.0)!!
            )
        }

        tvStart.text = intent.getStringExtra("start")
        tvEnd.text = intent.getStringExtra("end")
        tvLocationFrom.text = trip.locationFrom
        tvLocationTo.text = trip.locationTo
        tvDistanceCovered.text = trip.distance.toString()
    }

    fun insertTrip(view: View) {
        tvError.text = ""

        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.createTrip(trip)
            val responseVehicle = RetrofitInstance.api.updateVehicle(
                VehicleUpdate(
                    id = trip.vehicleId,
                    mileage = intent.getStringExtra("mileage")!!.toDouble() + trip.distance
                )
            )

            if (response.isSuccessful && response.body() != null && responseVehicle.isSuccessful && responseVehicle.body() != null) {
                val toast = Toast.makeText(
                    this@TripFinishedActivity,
                    getString(R.string.trip_created),
                    Toast.LENGTH_SHORT
                )
                toast.show()

                startActivity(
                    Intent(
                        this@TripFinishedActivity,
                        NewTripActivity::class.java
                    )
                )
                finish()
            } else {
                tvError.text = getString(R.string.error1)
            }
        }
    }

    fun cancel(view: View) {
        startActivity(
            Intent(
                this,
                NewTripActivity::class.java
            )
        )
        finish()
    }
}