package badvilbel.ws20st.frontend.models.trip

import com.google.gson.annotations.SerializedName

data class TripResponse(
    val id: Int, val start: String,
    val end: String,
    @SerializedName("driver_id") val driverId: Int,
    @SerializedName("vehicle_id") val vehicleId: String,
    @SerializedName("location_from") val locationFrom: String,
    @SerializedName("location_to") val locationTo: String,
    val distance: Double
)
