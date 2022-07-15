package badvilbel.ws20st.frontend.driver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import badvilbel.ws20st.frontend.R
import badvilbel.ws20st.frontend.networking.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewTripActivity : AppCompatActivity() {
    lateinit var etLicensePlate: EditText
    lateinit var etMileage: EditText
    lateinit var tvError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_trip)

        etLicensePlate = findViewById(R.id.etLicensePlate)
        etMileage = findViewById(R.id.etMileage)
        tvError = findViewById(R.id.tvNewTripError)
    }

    fun startTrip(view: View) {
        val licensePlate = etLicensePlate.text.toString()
        val mileage = etMileage.text.toString()

        if (licensePlate.isEmpty() || mileage.isEmpty()) {
            tvError.text = getString(R.string.error_missing_input)
            return
        }



        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.readVehicles(licensePlate)

            val body = response.body()

            if (response.isSuccessful && body != null && body!!
                    .isNotEmpty()
            ) {
                val intent = Intent(this@NewTripActivity, TripRecordingActivity::class.java)

                intent.putExtra("vehicleId", body[0].id)
                intent.putExtra("mileage", mileage)

                startActivity(intent)
            } else {
                tvError.text = getString(R.string.error_vehicle_not_found)
            }
        }
    }
}