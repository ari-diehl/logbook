package badvilbel.ws20st.frontend.invoice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import badvilbel.ws20st.frontend.R
import badvilbel.ws20st.frontend.models.trip.TripResponse

class TripDetailsActivity : AppCompatActivity() {
    lateinit var trip: TripResponse

    lateinit var tvDepartureStreet: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_details)

        trip = intent.getSerializableExtra("trip") as TripResponse

        tvDepartureStreet = findViewById(R.id.tvTDDepartureStreet)

        tvDepartureStreet.text = trip.departureStreet
    }
}