package badvilbel.ws20st.frontend.models.vehicle

import com.google.gson.annotations.SerializedName

data class VehicleResponse(
    val id: Int,
    @SerializedName("license_plate") val licensePlate: String,
    val manufacturer: String,
    val model: String,
    val mileage: Double,
)
