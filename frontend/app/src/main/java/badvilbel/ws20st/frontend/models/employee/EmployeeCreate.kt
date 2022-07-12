package badvilbel.ws20st.frontend.models.employee

import com.google.gson.annotations.SerializedName

data class EmployeeCreate(
    val id: Int,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    val role: String,
    val password: String
)
