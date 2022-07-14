package badvilbel.ws20st.frontend.driver

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import badvilbel.ws20st.frontend.R
import badvilbel.ws20st.frontend.Utils
import badvilbel.ws20st.frontend.networking.RetrofitInstance
import badvilbel.ws20st.frontend.models.trip.TripCreate
import badvilbel.ws20st.frontend.models.vehicle.VehicleUpdate
import badvilbel.ws20st.frontend.models.vehicle_comment.VehicleCommentCreate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class TripFinishedActivity : AppCompatActivity() {
    private lateinit var tvStart: TextView
    private lateinit var tvEnd: TextView
    private lateinit var tvDepartureAddress: TextView
    private lateinit var tvArrivalAddress: TextView
    private lateinit var tvDistanceCovered: TextView
    private lateinit var tvError: TextView
    private lateinit var etVehicleComment: EditText

    private lateinit var trip: TripCreate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_finished)

        tvStart = findViewById(R.id.tvTFStart)
        tvEnd = findViewById(R.id.tvTFEnd)
        tvDepartureAddress = findViewById(R.id.tvTFDepartureAddress)
        tvArrivalAddress = findViewById(R.id.tvTFArrivalAddress)
        tvDistanceCovered = findViewById(R.id.tvTFDistanceCovered)
        tvError = findViewById(R.id.tvTFError)
        etVehicleComment = findViewById(R.id.etVehicleComment)

        val sharedPref = getSharedPreferences("employee", Context.MODE_PRIVATE)

        with(intent) {
            trip = TripCreate(
                getStringExtra("startJson")!!,
                getStringExtra("endJson")!!,
                sharedPref.getInt("id", 0),
                getIntExtra("vehicleId", 0),
                getStringExtra("departureStreet")!!,
                getStringExtra("departureHouseNumber")!!,
                getStringExtra("departurePostalCode")!!,
                getStringExtra("departureLocality")!!,
                getStringExtra("departureCountry")!!,
                getStringExtra("arrivalStreet")!!,
                getStringExtra("arrivalHouseNumber")!!,
                getStringExtra("arrivalPostalCode")!!,
                getStringExtra("arrivalLocality")!!,
                getStringExtra("arrivalCountry")!!,
                getDoubleExtra("distance", 0.0)!!
            )
        }

        tvStart.text = intent.getStringExtra("start")
        tvEnd.text = intent.getStringExtra("end")
        tvDepartureAddress.text = intent.getStringExtra("departureAddress")
        tvArrivalAddress.text = intent.getStringExtra("arrivalAddress")
        tvDistanceCovered.text = trip.distance.toString()
    }

    fun insertTrip(view: View) {
        tvError.text = ""

        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.createTrip(trip)
            val responseVehicle = RetrofitInstance.api.updateVehicle(
                trip.vehicleId,
                VehicleUpdate(
                    mileage = intent.getStringExtra("mileage")!!.toDouble() + trip.distance
                )
            )

            val comment = etVehicleComment.text.toString()

            if (comment != "") {
                val responseVehicleComment = RetrofitInstance.api.createVehicleComment(
                    VehicleCommentCreate(
                        trip.driverId,
                        trip.vehicleId,
                        comment,
                        Utils.dateToJson(Date())
                    )
                )

                if (!responseVehicleComment.isSuccessful || response.body() == null) {
                    tvError.text = getString(R.string.error_generic)

                    return@launch
                }
            }

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
                tvError.text = getString(R.string.error_generic)
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