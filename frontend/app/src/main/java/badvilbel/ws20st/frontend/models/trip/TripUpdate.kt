package badvilbel.ws20st.frontend.models.trip

import com.google.gson.annotations.SerializedName

data class TripUpdate(
    val start: String? = null,
    val end: String? = null,
    @SerializedName("driver_id") val driverId: Int? = null,
    @SerializedName("vehicle_id") val vehicleId: Int? = null,
    @SerializedName("departure_street") val departureStreet: String? = null,
    @SerializedName("departure_house_number") val departureHouseNumber: String? = null,
    @SerializedName("departure_postal_code") val departurePostalCode: String? = null,
    @SerializedName("departure_locality") val departureLocality: String? = null,
    @SerializedName("departure_country") val departureCountry: String? = null,
    @SerializedName("arrival_street") val arrivalStreet: String? = null,
    @SerializedName("arrival_house_number") val arrivalHouseNumber: String? = null,
    @SerializedName("arrival_postal_code") val arrivalPostalCode: String? = null,
    @SerializedName("arrival_locality") val arrivalLocality: String? = null,
    @SerializedName("arrival_country") val arrivalCountry: String? = null,
    val distance: Double? = null
)
