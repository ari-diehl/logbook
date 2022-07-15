package badvilbel.ws20st.frontend.invoice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import badvilbel.ws20st.frontend.R

class InvoiceDashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice_dashboard)
    }

    fun showTrips(view: View) {
        startActivity(Intent(this, TripListActivity::class.java))
    }

    fun showUsers(view: View) {
        startActivity(Intent(this, UserListActivity::class.java))
    }
}