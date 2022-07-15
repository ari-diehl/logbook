package badvilbel.ws20st.frontend.models.employee

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EmployeeResponse(
    val id: Int,
    @SerializedName("personnel_number") val personnelNumber: Int,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    val role: String,
) : Serializable