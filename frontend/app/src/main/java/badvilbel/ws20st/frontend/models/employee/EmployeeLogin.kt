package badvilbel.ws20st.frontend.models.employee

import com.google.gson.annotations.SerializedName

data class EmployeeLogin(
    @SerializedName("personnel_number") val personnelNumber: Int,
    val password: String
)
