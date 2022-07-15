package badvilbel.ws20st.frontend.invoice

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import badvilbel.ws20st.frontend.R
import badvilbel.ws20st.frontend.models.employee.EmployeeCreate
import badvilbel.ws20st.frontend.networking.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewUserActivity : AppCompatActivity() {
    lateinit var etPersonnelNumber: EditText
    lateinit var etFirstName: EditText
    lateinit var etLastName: EditText
    lateinit var etPassword: EditText
    lateinit var rgRole: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        etPersonnelNumber = findViewById(R.id.etNUPersonnelNumber)
        etFirstName = findViewById(R.id.etNUFirstName)
        etLastName = findViewById(R.id.etNULastName)
        etPassword = findViewById(R.id.etNUPassword)
        rgRole = findViewById(R.id.rgNURole)
    }

    fun createUser(view: View) {
        val personnelNumber = etPersonnelNumber.text.toString()
        val firstName = etFirstName.text.toString()
        val lastName = etLastName.text.toString()
        val password = etPassword.text.toString()
        val rbId = rgRole.checkedRadioButtonId

        if (personnelNumber == "" || firstName == "" || lastName == "" || password == "" || rbId == -1) {
            return
        }

        val role =
            when ((rgRole.getChildAt(rgRole.indexOfChild(rgRole.findViewById<RadioButton>(rbId))) as RadioButton).text.toString()) {
                getString(R.string.driver) -> "driver"
                getString(R.string.invoice) -> "invoice"
                getString(R.string.maintenance) -> "maintenance"
                else -> ""
            }

        GlobalScope.launch(Dispatchers.Main) {
            val token =
                getSharedPreferences("employee", Context.MODE_PRIVATE).getString("access_token", "")

            val response = RetrofitInstance.api.createEmployee(
                "Bearer ${token!!}",
                EmployeeCreate(personnelNumber.toInt(), firstName, lastName, role, password)
            )

            if (response.isSuccessful && response.body() != null) {
                val toast = Toast.makeText(
                    this@NewUserActivity,
                    getString(R.string.created),
                    Toast.LENGTH_SHORT
                )

                toast.show()

                finish()
            }
        }
    }
}