package badvilbel.ws20st.frontend

import badvilbel.ws20st.frontend.models.employee.EmployeeLogin
import badvilbel.ws20st.frontend.models.employee.EmployeeLoginResponse
import badvilbel.ws20st.frontend.models.employee.EmployeeResponse
import badvilbel.ws20st.frontend.models.trip.TripCreate
import badvilbel.ws20st.frontend.models.trip.TripResponse
import badvilbel.ws20st.frontend.models.vehicle.VehicleResponse
import badvilbel.ws20st.frontend.models.vehicle.VehicleUpdate
import retrofit2.Response
import retrofit2.http.*

interface LogbookApi {
    @Headers("Content-Type: application/json")
    @POST("/auth/employee_login")
    suspend fun login(@Body employeeLogin: EmployeeLogin): Response<EmployeeLoginResponse>

    @GET("/employees")
    suspend fun getEmployees(): Response<List<EmployeeResponse>>

    @GET("/employees/{employee_id}")
    suspend fun getEmployee(@Path("employee_id") id: Int): Response<EmployeeResponse>

    @GET("/vehicles/{vehicle_id}")
    suspend fun getVehicle(@Path("vehicle_id") id: String): Response<VehicleResponse>

    @Headers("Content-Type: application/json")
    @PUT("/vehicles/")
    suspend fun updateVehicle(@Body vehicle: VehicleUpdate): Response<VehicleResponse>

    @Headers("Content-Type: application/json")
    @POST("/trips/")
    suspend fun createTrip(@Body trip: TripCreate): Response<TripResponse>
}