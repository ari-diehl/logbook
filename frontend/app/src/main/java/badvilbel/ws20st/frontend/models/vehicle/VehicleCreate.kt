package badvilbel.ws20st.frontend.models.vehicle

data class VehicleCreate(
    val id: String,
    val manufacturer: String,
    val model: String,
    val mileage: Double,
    val comment: String
)
