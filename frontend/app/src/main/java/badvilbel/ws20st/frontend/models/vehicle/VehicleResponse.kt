package badvilbel.ws20st.frontend.models.vehicle

data class VehicleResponse(
    val id: String,
    val manufacturer: String,
    val model: String,
    val mileage: Int,
    val comment: String
)
