package badvilbel.ws20st.frontend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun login(view: View) {
        val loginJSONBody = JSONObject();

        loginJSONBody.put("id", findViewById<EditText>(R.id.etId).text.toString())
        loginJSONBody.put("password", findViewById<EditText>(R.id.etPassword).text.toString())

        RQSingleton.getInstance(this).addToRequestQueue(
            JsonObjectRequest(Request.Method.POST, getString(R.string.backend_url).toString() + "/auth/employee_login", loginJSONBody,
                Response.Listener { response ->
                    println("JWT: ${response["access_token"]}")
                },
                Response.ErrorListener {
                    findViewById<TextView>(R.id.tvError).text = "Login fehlgeschlagen"
                })
        )
    }
}