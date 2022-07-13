package badvilbel.ws20st.frontend.models.vehicle_comment

import com.google.gson.annotations.SerializedName

data class VehicleCommentResponse(
    val id: Int,
    @SerializedName("employee_id") val employeeId: Int,
    @SerializedName("vehicle_id") val vehicleId: Int,
    val text: String,
    val datetime: String
)
