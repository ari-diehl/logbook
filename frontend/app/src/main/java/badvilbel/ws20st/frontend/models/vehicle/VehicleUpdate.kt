package badvilbel.ws20st.frontend.models.vehicle

data class VehicleUpdate(
    val id: String,
    val manufacturer: String? = null,
    val model: String? = null,
    val mileage: Int? = null,
    val comment: String? = null,
)
