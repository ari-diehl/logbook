package badvilbel.ws20st.frontend.invoice

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import badvilbel.ws20st.frontend.R
import badvilbel.ws20st.frontend.models.employee.EmployeeResponse
import badvilbel.ws20st.frontend.networking.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserListActivity : AppCompatActivity() {
    var users: List<EmployeeResponse> = listOf()

    lateinit var token: String

    lateinit var rvUsers: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        rvUsers = findViewById<RecyclerView>(R.id.rvUsers)

        token =
            getSharedPreferences("employee", Context.MODE_PRIVATE).getString("access_token", "")
                .toString()

        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.readEmployees("Bearer ${token!!}")

            if (response.isSuccessful && response.body() != null) {
                users = response.body()!!

                rvUsers.layoutManager = LinearLayoutManager(this@UserListActivity)
                rvUsers.adapter =
                    UserAdapter(users) { position -> onItemClick(position) }
                rvUsers.addItemDecoration(
                    DividerItemDecoration(
                        rvUsers.context,
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
        }
    }

    private fun onItemClick(position: Int) {
        val intent = Intent(this, UserDetailsActivity::class.java)
        intent.putExtra("user", users[position])
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.readEmployees("Bearer ${token!!}")

            if (response.isSuccessful && response.body() != null) {
                users = response.body()!!
                rvUsers.adapter =
                    UserAdapter(users) { position -> onItemClick(position) }
            }
        }
    }

    fun createUser(view: View) {
        startActivity(Intent(this, NewUserActivity::class.java))
    }
}