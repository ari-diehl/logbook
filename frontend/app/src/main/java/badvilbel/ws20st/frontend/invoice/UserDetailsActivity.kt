package badvilbel.ws20st.frontend.invoice

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import badvilbel.ws20st.frontend.R
import badvilbel.ws20st.frontend.models.employee.EmployeeResponse
import badvilbel.ws20st.frontend.networking.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserDetailsActivity : AppCompatActivity() {
    lateinit var tvPersonnelNumber: TextView
    lateinit var tvFirstName: TextView
    lateinit var tvLastName: TextView
    lateinit var tvRole: TextView
    lateinit var btnDelete: Button

    lateinit var user: EmployeeResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        user = intent.getSerializableExtra("user") as EmployeeResponse

        tvPersonnelNumber = findViewById(R.id.tvUDPersonnelNumber)
        tvFirstName = findViewById(R.id.tvUDFirstName)
        tvLastName = findViewById(R.id.tvUDLastName)
        tvRole = findViewById(R.id.tvUDRole)
        btnDelete = findViewById(R.id.btnUDDelete)

        val employeeId = getSharedPreferences("employee", Context.MODE_PRIVATE).getInt("id", 0)

        if (employeeId == user.id) {
            btnDelete.visibility = View.GONE
        }

        tvPersonnelNumber.text = user.personnelNumber.toString()
        tvFirstName.text = user.firstName
        tvLastName.text = user.lastName
        tvRole.text = when (user.role) {
            "driver" -> getString(R.string.driver)
            "invoice" -> getString(R.string.invoice)
            "maintenance" -> getString(R.string.maintenance)
            else -> user.role
        }
    }

    fun deleteUser(view: View) {
        GlobalScope.launch(Dispatchers.Main) {
            val token =
                getSharedPreferences("employee", Context.MODE_PRIVATE).getString("access_token", "")

            val response = RetrofitInstance.api.deleteEmployee("Bearer ${token!!}", user.id)

            if (response.isSuccessful && response.body() != null) {
                val toast = Toast.makeText(
                    this@UserDetailsActivity,
                    getString(R.string.deleted),
                    Toast.LENGTH_SHORT
                )

                toast.show()

                finish()
            }
        }
    }
}