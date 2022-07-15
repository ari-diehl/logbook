package badvilbel.ws20st.frontend.models.trip

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TripResponse(
    val id: Int,
    val start: String,
    val end: String,
    @SerializedName("driver_id") val driverId: Int,
    @SerializedName("vehicle_id") val vehicleId: Int,
    @SerializedName("departure_street") val departureStreet: String,
    @SerializedName("departure_house_number") val departureHouseNumber: String,
    @SerializedName("departure_postal_code") val departurePostalCode: String,
    @SerializedName("departure_locality") val departureLocality: String,
    @SerializedName("departure_country") val departureCountry: String,
    @SerializedName("arrival_street") val arrivalStreet: String,
    @SerializedName("arrival_house_number") val arrivalHouseNumber: String,
    @SerializedName("arrival_postal_code") val arrivalPostalCode: String,
    @SerializedName("arrival_locality") val arrivalLocality: String,
    @SerializedName("arrival_country") val arrivalCountry: String,
    val distance: Double
) : Serializable
