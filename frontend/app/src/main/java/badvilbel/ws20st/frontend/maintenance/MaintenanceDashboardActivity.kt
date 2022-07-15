package badvilbel.ws20st.frontend.maintenance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import badvilbel.ws20st.frontend.R

class MaintenanceDashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maintenance_dashboard)
    }

    fun showVehicles(view: View) {
        startActivity(Intent(this, VehicleListActivity::class.java))
    }
}