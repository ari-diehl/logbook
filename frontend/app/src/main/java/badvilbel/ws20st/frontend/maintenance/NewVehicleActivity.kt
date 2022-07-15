package badvilbel.ws20st.frontend.maintenance

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import badvilbel.ws20st.frontend.R
import badvilbel.ws20st.frontend.models.vehicle.VehicleCreate
import badvilbel.ws20st.frontend.networking.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewVehicleActivity : AppCompatActivity() {
    lateinit var etLicensePlate: EditText
    lateinit var etManufacturer: EditText
    lateinit var etModel: EditText
    lateinit var etMileage: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_vehicle)

        etLicensePlate = findViewById(R.id.etNVLicensePlate)
        etManufacturer = findViewById(R.id.etNVManufacturer)
        etModel = findViewById(R.id.etNVModel)
        etMileage = findViewById(R.id.etNVMileage)
    }

    fun createVehicle(view: View) {
        val licensePlate = etLicensePlate.text.toString()
        val manufacturer = etManufacturer.text.toString()
        val model = etModel.text.toString()
        val mileage = etMileage.text.toString()

        if (licensePlate == "" || manufacturer == "" || model == "" || mileage == "") {
            return
        }

        GlobalScope.launch(Dispatchers.Main) {
            val token =
                getSharedPreferences("vehicle", Context.MODE_PRIVATE).getString("access_token", "")

            val response = RetrofitInstance.api.createVehicle(
                "Bearer ${token!!}",
                VehicleCreate(licensePlate, manufacturer, model, mileage.toDouble())
            )

            if (response.isSuccessful && response.body() != null) {
                val toast = Toast.makeText(
                    this@NewVehicleActivity,
                    getString(R.string.created),
                    Toast.LENGTH_SHORT
                )

                toast.show()

                finish()
            }
        }
    }
}