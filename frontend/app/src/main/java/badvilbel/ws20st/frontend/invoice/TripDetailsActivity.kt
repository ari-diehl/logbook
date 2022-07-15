package badvilbel.ws20st.frontend.invoice

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import badvilbel.ws20st.frontend.R
import badvilbel.ws20st.frontend.Utils
import badvilbel.ws20st.frontend.models.trip.TripResponse
import badvilbel.ws20st.frontend.networking.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TripDetailsActivity : AppCompatActivity() {
    lateinit var trip: TripResponse

    lateinit var tvDepartureStreet: TextView
    lateinit var tvDepartureHouseNumber: TextView
    lateinit var tvDeparturePostalCode: TextView
    lateinit var tvDepartureLocality: TextView
    lateinit var tvDepartureCountry: TextView
    lateinit var tvArrivalStreet: TextView
    lateinit var tvArrivalHouseNumber: TextView
    lateinit var tvArrivalPostalCode: TextView
    lateinit var tvArrivalLocality: TextView
    lateinit var tvArrivalCountry: TextView
    lateinit var tvStart: TextView
    lateinit var tvEnd: TextView
    lateinit var tvLicensePlate: TextView
    lateinit var tvDistance: TextView
    lateinit var btnDriver: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_details)

        trip = intent.getSerializableExtra("trip") as TripResponse

        tvDepartureStreet = findViewById(R.id.tvTDDepartureStreet)
        tvDepartureHouseNumber = findViewById(R.id.tvTDDepartureHouseNumber)
        tvDeparturePostalCode = findViewById(R.id.tvTDDeparturePostalCode)
        tvDepartureLocality = findViewById(R.id.tvTDDepartureLocality)
        tvDepartureCountry = findViewById(R.id.tvTDDepartureCountry)
        tvArrivalStreet = findViewById(R.id.tvTDArrivalStreet)
        tvArrivalHouseNumber = findViewById(R.id.tvTDArrivalHouseNumber)
        tvArrivalPostalCode = findViewById(R.id.tvTDArrivalPostalCode)
        tvArrivalLocality = findViewById(R.id.tvTDArrivalLocality)
        tvArrivalCountry = findViewById(R.id.tvTDArrivalCountry)
        tvStart = findViewById(R.id.tvTDStart)
        tvEnd = findViewById(R.id.tvTDEnd)
        tvLicensePlate = findViewById(R.id.tvTDLicensePlate)
        tvDistance = findViewById(R.id.tvTDDistance)
        btnDriver = findViewById(R.id.btnTDDriver)

        tvDepartureStreet.text = trip.departureStreet
        tvDepartureHouseNumber.text = trip.departureHouseNumber
        tvDeparturePostalCode.text = trip.departurePostalCode
        tvDepartureLocality.text = trip.departureLocality
        tvDepartureCountry.text = trip.departureCountry
        tvArrivalStreet.text = trip.arrivalStreet
        tvArrivalHouseNumber.text = trip.arrivalHouseNumber
        tvArrivalPostalCode.text = trip.arrivalPostalCode
        tvArrivalLocality.text = trip.arrivalLocality
        tvArrivalCountry.text = trip.arrivalCountry
        tvStart.text = Utils.formatJsonDate(trip.start)
        tvEnd.text = Utils.formatJsonDate(trip.end)
        tvDistance.text = trip.distance.toString()

        GlobalScope.launch(Dispatchers.Main) {
            val responseVehicle = RetrofitInstance.api.readVehicle(trip.vehicleId)

            val token =
                getSharedPreferences("employee", Context.MODE_PRIVATE).getString("access_token", "")

            val responseDriver =
                RetrofitInstance.api.readEmployee("Bearer ${token!!}", trip.driverId)

            val vehicle = responseVehicle.body()
            val driver = responseDriver.body()

            if (responseVehicle.isSuccessful && vehicle != null && responseDriver.isSuccessful && driver != null) {
                tvLicensePlate.text = vehicle.licensePlate

                btnDriver.setOnClickListener {
                    val intent = Intent(this@TripDetailsActivity, UserDetailsActivity::class.java)

                    intent.putExtra("user", driver)

                    startActivity(intent)
                }

                btnDriver.text = "${driver.firstName} ${driver.lastName}"
            }
        }

    }
}