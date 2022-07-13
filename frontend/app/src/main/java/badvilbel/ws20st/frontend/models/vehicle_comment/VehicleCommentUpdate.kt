package badvilbel.ws20st.frontend.models.vehicle_comment

import com.google.gson.annotations.SerializedName

data class VehicleCommentUpdate(
    @SerializedName("employee_id") val employeeId: Int? = null,
    @SerializedName("vehicle_id") val vehicleId: Int? = null,
    val text: String? = null,
    val datetime: String? = null
)
