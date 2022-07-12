package badvilbel.ws20st.frontend

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import badvilbel.ws20st.frontend.driver.NewTripActivity
import badvilbel.ws20st.frontend.models.employee.EmployeeLogin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var etEmployeeId: EditText
    private lateinit var etPassword: EditText
    private lateinit var tvLoginError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmployeeId = findViewById(R.id.etEmployeeId)
        etPassword = findViewById(R.id.etPassword)
        tvLoginError = findViewById(R.id.tvLoginError)
    }

    fun login(view: View) {
        tvLoginError.text = ""

        val id = etEmployeeId.text.toString()
        val password = etPassword.text.toString()

        if (id != "" && password != "") {
            GlobalScope.launch(Dispatchers.Main) {
                val response = RetrofitInstance.api.login(EmployeeLogin(id.toInt(), password))

                if (response.isSuccessful && response.body() != null) {

                    val sharedPref = getSharedPreferences("employee", Context.MODE_PRIVATE)

                    val body = response.body()

                    with(sharedPref.edit()) {
                        putInt("id", body!!.id)
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
                            finish()
                        }
                    }
                } else {
                    tvLoginError.text = getString(R.string.error1)
                }
            }
        }
    }
}