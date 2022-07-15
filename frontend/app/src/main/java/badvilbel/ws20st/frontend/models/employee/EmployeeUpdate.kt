package badvilbel.ws20st.frontend.models.employee

import com.google.gson.annotations.SerializedName

data class EmployeeUpdate(
    @SerializedName("personnel_number") val personnelNumber: Int? = null,
    @SerializedName("first_name") val firstName: String? = null,
    @SerializedName("last_name") val lastName: String? = null,
    val role: String? = null,
    val password: String? = null
)
