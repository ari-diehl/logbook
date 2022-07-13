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
    lateinit var etVehicleId: EditText
    lateinit var etMileage: EditText
    lateinit var tvError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_trip)

        etVehicleId = findViewById(R.id.etVehicleId)
        etMileage = findViewById(R.id.etMileage)
        tvError = findViewById(R.id.tvNewTripError)
    }

    fun startTrip(view: View) {
        val vehicleId = etVehicleId.text.toString()
        val mileage = etMileage.text.toString()

        if (vehicleId != "" && mileage != "") {
            GlobalScope.launch(Dispatchers.Main) {
                val response = RetrofitInstance.api.getVehicle(vehicleId)

                if (response.isSuccessful && response.body() != null) {
                    val intent = Intent(this@NewTripActivity, TripRecordingActivity::class.java)

                    intent.putExtra("vehicleId", vehicleId)
                    intent.putExtra("mileage", mileage)

                    startActivity(intent)
                } else {
                    tvError.text = getString(R.string.error1)
                }
            }
        }
    }
}