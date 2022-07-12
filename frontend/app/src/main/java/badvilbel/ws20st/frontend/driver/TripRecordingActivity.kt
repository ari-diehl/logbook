package badvilbel.ws20st.frontend.driver

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import badvilbel.ws20st.frontend.R
import com.google.android.gms.location.*
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timerTask

class TripRecordingActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private var startLocation: Location? = null
    private var previousLocation: Location? = null
    private var currentLocation: Location? = null

    private var distanceCovered: Int = 0

    private lateinit var tvDistanceCovered: TextView
    private lateinit var tvDuration: TextView

    private lateinit var start: Date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_recording)

        tvDistanceCovered = findViewById(R.id.tvDistanceCovered)
        tvDuration = findViewById(R.id.tvDuration)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )

            return
        }

        locationRequest = LocationRequest.create().apply {
            interval = TimeUnit.SECONDS.toMillis(60)
            fastestInterval = TimeUnit.SECONDS.toMillis(30)
            maxWaitTime = TimeUnit.MINUTES.toMillis(2)
            priority = Priority.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                currentLocation = locationResult.lastLocation

                distanceCovered += (currentLocation!!.distanceTo(previousLocation) / 1000).toInt()
                tvDistanceCovered.text = distanceCovered.toString()

                previousLocation = currentLocation
            }

        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                startLocation = location
                previousLocation = location

                findViewById<TextView>(R.id.tvLocationStart).text =
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
        startActivity(Intent(this, NewTripActivity::class.java))
        finish()
    }

    fun destinationReached(view: View) {
        val vehicleId = intent.getStringExtra("vehicleId")
        val mileage = intent.getStringExtra("mileage")

        val intent = Intent(this, TripFinishedActivity::class.java)

        val addressStart = Geocoder(this, Locale.getDefault()).getFromLocation(
            startLocation!!.latitude,
            startLocation!!.longitude,
            1
        )[0].getAddressLine(0)

        val addressEnd = Geocoder(this, Locale.getDefault()).getFromLocation(
            currentLocation!!.latitude,
            currentLocation!!.longitude,
            1
        )[0].getAddressLine(0)

        val format = SimpleDateFormat("dd.MM.yyyy HH:mm")
        val jsonFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'")

        val now = Date()

        with(intent) {
            putExtra("vehicleId", vehicleId)
            putExtra("mileage", mileage)
            putExtra("distance", distanceCovered)
            putExtra("start", format.format(start))
            putExtra("end", format.format(now))
            putExtra("startJson", jsonFormat.format(start))
            putExtra("endJson", jsonFormat.format(now))
            putExtra("locationFrom", addressStart)
            putExtra("locationTo", addressEnd)
        }

        startActivity(intent)
        finish()
    }
}