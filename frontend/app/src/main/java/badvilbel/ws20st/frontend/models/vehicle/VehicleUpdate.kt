package badvilbel.ws20st.frontend.models.vehicle

import com.google.gson.annotations.SerializedName

data class VehicleUpdate(
    @SerializedName("license_plate") val licensePlate: String? = null,
    val manufacturer: String? = null,
    val model: String? = null,
    val mileage: Double? = null,
)
