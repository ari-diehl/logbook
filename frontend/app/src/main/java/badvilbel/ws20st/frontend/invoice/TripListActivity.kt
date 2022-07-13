package badvilbel.ws20st.frontend.invoice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import badvilbel.ws20st.frontend.R
import badvilbel.ws20st.frontend.networking.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TripListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_list)

        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.getTrips()

            if (response.isSuccessful && response.body() != null) {
                val rvTrips = findViewById<RecyclerView>(R.id.rvTrips)
                rvTrips.layoutManager = LinearLayoutManager(this@TripListActivity)
                rvTrips.adapter = TripAdapter(response.body()!!)
            }
        }
    }
}