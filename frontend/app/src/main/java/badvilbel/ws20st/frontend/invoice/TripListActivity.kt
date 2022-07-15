package badvilbel.ws20st.frontend.invoice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import badvilbel.ws20st.frontend.R
import badvilbel.ws20st.frontend.models.trip.TripResponse
import badvilbel.ws20st.frontend.networking.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TripListActivity : AppCompatActivity() {
    var trips: List<TripResponse> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_list)

        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.readTrips()

            if (response.isSuccessful && response.body() != null) {
                trips = response.body()!!

                val rvTrips = findViewById<RecyclerView>(R.id.rvTrips)

                rvTrips.layoutManager = LinearLayoutManager(this@TripListActivity)
                rvTrips.adapter =
                    TripAdapter(trips) { position -> onItemClick(position) }
                rvTrips.addItemDecoration(
                    DividerItemDecoration(
                        rvTrips.context,
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
        }
    }

    private fun onItemClick(position: Int) {
        val intent = Intent(this, TripDetailsActivity::class.java)
        intent.putExtra("trip", trips[position])
        startActivity(intent)
    }
}