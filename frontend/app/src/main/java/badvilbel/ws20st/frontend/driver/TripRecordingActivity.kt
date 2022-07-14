package badvilbel.ws20st.frontend.driver

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import badvilbel.ws20st.frontend.R
import badvilbel.ws20st.frontend.Utils
import com.google.android.gms.location.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TripRecordingActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var startLocation: Location? = null
    private var previousLocation: Location? = null
    private var currentLocation: Location? = null

    private var distanceCovered: Double = 0.0
    private lateinit var start: Date

    private lateinit var tvDistanceCovered: TextView
    private lateinit var tvDuration: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_recording)

        tvDistanceCovered = findViewById(R.id.tvDistanceCovered)
        tvDuration = findViewById(R.id.tvDuration)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            1
        )
        else
            startRecording()


    }

    fun startRecording()
    {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = LocationRequest.create().apply {
            interval = TimeUnit.SECONDS.toMillis(1)
            fastestInterval = TimeUnit.SECONDS.toMillis(1)
            maxWaitTime = TimeUnit.SECONDS.toMillis(1)
            priority = Priority.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                currentLocation = locationResult.lastLocation

                distanceCovered += currentLocation!!.distanceTo(previousLocation)

                tvDistanceCovered.text = Utils.formatKilometers(distanceCovered / 1000)

                previousLocation = currentLocation
            }

        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location: Location? ->
                startLocation = location
                previousLocation = location

                findViewById<TextView>(R.id.tvDepartureAddress).text =
                    Geocoder(this, Locale.getDefault()).getFromLocation(
                        startLocation!!.latitude,
                        startLocation!!.longitude,
                        1
                    )[0].getAddressLine(0)

                start = Date()

                val handler = Handler(Looper.getMainLooper())

                handler.post(object : Runnable {
                    override fun run() {
                        val durationInSeconds = (Date().time - start.time) / 1000

                        tvDuration.text = "${
                            (durationInSeconds / 3600).toString().padStart(2, '0')
                        }:${
                            (durationInSeconds / 60 % 3600).toString().padStart(2, '0')
                        }:${(durationInSeconds % 60).toString().padStart(2, '0')}"

                        handler.postDelayed(this, 1000)
                    }
                })

                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )
            }
    }

    fun cancel(view: View) {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        startActivity(Intent(this, NewTripActivity::class.java))
        finish()
    }

    fun destinationReached(view: View) {
        val vehicleId = intent.getIntExtra("vehicleId", 0)
        val mileage = intent.getStringExtra("mileage")

        val intent = Intent(this, TripFinishedActivity::class.java)

        val departureAddress = Geocoder(this, Locale.getDefault()).getFromLocation(
            startLocation!!.latitude,
            startLocation!!.longitude,
            1
        )[0]

        val arrivalAddress = Geocoder(this, Locale.getDefault()).getFromLocation(
            currentLocation!!.latitude,
            currentLocation!!.longitude,
            1
        )[0]

        val now = Date()

        with(intent) {
            putExtra("vehicleId", vehicleId)
            putExtra("mileage", mileage)
            putExtra("distance", Utils.formatKilometers(distanceCovered / 1000).toDouble())
            putExtra("start", Utils.formatDate(start))
            putExtra("end", Utils.formatDate(now))
            putExtra("startJson", Utils.dateToJson(start))
            putExtra("endJson", Utils.dateToJson(now))
            putExtra("departureAddress", departureAddress.getAddressLine(0))
            putExtra("departureStreet", departureAddress.thoroughfare)
            putExtra("departureHouseNumber", departureAddress.subThoroughfare)
            putExtra("departurePostalCode", departureAddress.postalCode)
            putExtra("departureLocality", departureAddress.locality)
            putExtra("departureCountry", departureAddress.countryName)
            putExtra("arrivalAddress", arrivalAddress.getAddressLine(0))
            putExtra("arrivalStreet", arrivalAddress.thoroughfare)
            putExtra("arrivalHouseNumber", arrivalAddress.subThoroughfare)
            putExtra("arrivalPostalCode", arrivalAddress.postalCode)
            putExtra("arrivalLocality", arrivalAddress.locality)
            putExtra("arrivalCountry", arrivalAddress.countryName)
        }

        startActivity(intent)
        finish()
    }

    //override on permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startRecording()
            }
            else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

}