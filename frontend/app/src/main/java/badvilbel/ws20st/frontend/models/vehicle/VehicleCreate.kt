package badvilbel.ws20st.frontend.models.vehicle

import com.google.gson.annotations.SerializedName

data class VehicleCreate(
    @SerializedName("license_plate") val licensePlate: String,
    val manufacturer: String,
    val model: String,
    val mileage: Double,
)
