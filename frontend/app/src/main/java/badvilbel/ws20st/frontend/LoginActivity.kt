package badvilbel.ws20st.frontend

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import badvilbel.ws20st.frontend.driver.NewTripActivity
import badvilbel.ws20st.frontend.invoice.InvoiceDashboardActivity
import badvilbel.ws20st.frontend.maintenance.MaintenanceDashboardActivity
import badvilbel.ws20st.frontend.models.employee.EmployeeLogin
import badvilbel.ws20st.frontend.networking.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var etPersonnelNumber: EditText
    private lateinit var etPassword: EditText
    private lateinit var tvError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etPersonnelNumber = findViewById(R.id.etPersonnelNumber)
        etPassword = findViewById(R.id.etPassword)
        tvError = findViewById(R.id.tvLoginError)
    }

    fun login(view: View) {
        tvError.text = ""

        val personnel_number = etPersonnelNumber.text.toString()
        val password = etPassword.text.toString()
        if (personnel_number == "" || password == "") {
            tvError.text = getString(R.string.error_missing_input)
            return;
        }

        GlobalScope.launch(Dispatchers.Main) {
            val response =
                RetrofitInstance.api.login(EmployeeLogin(personnel_number.toInt(), password))

            if (response.isSuccessful && response.body() != null) {
                val sharedPref = getSharedPreferences("employee", Context.MODE_PRIVATE)

                val body = response.body()

                with(sharedPref.edit()) {
                    putInt("id", body!!.id)
                    putInt("personnel_number", body!!.personnelNumber)
                    putString("first_name", body!!.firstName)
                    putString("last_name", body!!.lastName)
                    putString("role", body!!.role)
                    putString("access_token", body!!.accessToken)
                    commit()
                }

                when (body!!.role) {
                    "driver" -> {
                        startActivity(
                            Intent(
                                this@LoginActivity,
                                NewTripActivity::class.java
                            )
                        )
                    }
                    "invoice" -> {
                        startActivity(
                            Intent(
                                this@LoginActivity,
                                InvoiceDashboardActivity::class.java
                            )
                        )
                    }
                    "maintenance" -> {
                        startActivity(
                            Intent(
                                this@LoginActivity,
                                MaintenanceDashboardActivity::class.java
                            )
                        )
                    }
                }
            } else {
                tvError.text = getString(R.string.error_generic)
            }
        }
    }
}