package badvilbel.ws20st.frontend.networking

import badvilbel.ws20st.frontend.models.employee.*
import badvilbel.ws20st.frontend.models.trip.TripCreate
import badvilbel.ws20st.frontend.models.trip.TripResponse
import badvilbel.ws20st.frontend.models.trip.TripUpdate
import badvilbel.ws20st.frontend.models.vehicle.VehicleCreate
import badvilbel.ws20st.frontend.models.vehicle.VehicleResponse
import badvilbel.ws20st.frontend.models.vehicle.VehicleUpdate
import badvilbel.ws20st.frontend.models.vehicle_comment.VehicleCommentCreate
import badvilbel.ws20st.frontend.models.vehicle_comment.VehicleCommentResponse
import badvilbel.ws20st.frontend.models.vehicle_comment.VehicleCommentUpdate
import retrofit2.Response
import retrofit2.http.*

interface LogbookApi {
    @Headers("Content-Type: application/json")
    @POST("/auth/employee_login")
    suspend fun login(@Body employeeLogin: EmployeeLogin): Response<EmployeeLoginResponse>

    @GET("/employees")
    suspend fun readEmployees(): Response<List<EmployeeResponse>>

    @GET("/employees/{employee_id}")
    suspend fun readEmployee(@Path("employee_id") id: Int): Response<EmployeeResponse>

    @Headers("Content-Type: application/json")
    @POST("/employees/")
    suspend fun createEmployee(@Body employee: EmployeeCreate): Response<EmployeeResponse>

    @Headers("Content-Type: application/json")
    @PUT("/employees/{employee_id}")
    suspend fun updateEmployee(
        @Path("employee_id}") employee_id: Int,
        @Body employee: EmployeeUpdate
    ): Response<EmployeeResponse>

    @DELETE("/employees/{employee_id}")
    suspend fun deleteEmployee(@Path("employee_id") employee_id: Int): Response<EmployeeResponse>

    @GET("/vehicles")
    suspend fun readVehicles(@Query("license_plate") licensePlate: String): Response<List<VehicleResponse>>

    @GET("/vehicles/{vehicle_id}")
    suspend fun readVehicle(@Path("vehicle_id") id: String): Response<VehicleResponse>

    @Headers("Content-Type: application/json")
    @POST("/vehicles/")
    suspend fun createVehicle(@Body vehicle: VehicleCreate): Response<VehicleResponse>

    @Headers("Content-Type: application/json")
    @PUT("/vehicles/{vehicle_id}")
    suspend fun updateVehicle(
        @Path("vehicle_id") vehicle_id: Int,
        @Body vehicle: VehicleUpdate
    ): Response<VehicleResponse>

    @DELETE("/vehicles/{vehicle_id}")
    suspend fun deleteVehicle(@Path("vehicle_id") vehicle_id: Int): Response<VehicleResponse>

    @GET("/trips")
    suspend fun readTrips(): Response<List<TripResponse>>

    @Headers("Content-Type: application/json")
    @POST("/trips/")
    suspend fun createTrip(@Body trip: TripCreate): Response<TripResponse>

    @Headers("Content-Type: application/json")
    @PUT("/employees/{trip_id}")
    suspend fun updateTrip(
        @Path("trip_id") trip_id: Int,
        @Body trip: TripUpdate
    ): Response<TripResponse>

    @DELETE("/trips/{trip_id}")
    suspend fun deleteTrip(@Path("trip_id") trip_id: Int): Response<TripResponse>

    @GET("/vehicle_comments")
    suspend fun readVehicleComments(): Response<List<VehicleCommentResponse>>

    @Headers("Content-Type: application/json")
    @POST("/vehicle_comments/")
    suspend fun createVehicleComment(@Body vehicle_comment: VehicleCommentCreate): Response<VehicleCommentResponse>

    @Headers("Content-Type: application/json")
    @PUT("/employees/{comment_id}")
    suspend fun updateVehicleComment(
        @Path("vehicle_comment_id") comment_id: Int,
        @Body vehicle_comment: VehicleCommentUpdate
    ): Response<VehicleCommentResponse>

    @DELETE("/vehicle_comments/{comment_id}")
    suspend fun deleteVehicleComment(@Path("vehicle_comment_id") comment_id: Int): Response<VehicleCommentResponse>
}