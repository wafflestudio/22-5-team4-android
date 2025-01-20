package com.example.interpark.data.API

import com.example.interpark.data.CancelRequest
import com.example.interpark.data.ReservationRequest
import com.example.interpark.data.ReservationResponse
import com.example.interpark.data.SeatRequest
import com.example.interpark.data.SeatResponse
import com.example.interpark.data.types.Performance
import com.example.interpark.data.types.PerformanceEvent
import com.example.interpark.data.types.SignInRequest
import com.example.interpark.data.types.SignInResponse
import com.example.interpark.data.types.SignUpRequest
import com.example.interpark.data.types.SignUpResponse
import com.example.interpark.data.types.User
import com.squareup.moshi.JsonClass
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

@JsonClass(generateAdapter = true)
data class ResponseWrapper<T>(
    val result: List<T>
)

data class IdResponseWrapper<T>(
    val result: T
)

interface ApiClientDev {
    @GET("/performances")
    suspend fun getAllPerformances(
    ): ResponseWrapper<Performance>

    @GET("/performances/filter")
    suspend fun getPerformances(
        @Query("category") category: String?,
        @Query("title") title: String?
    ): ResponseWrapper<Performance>

    @GET("/performances/id")
    suspend fun getPerformanceById(
        @Query("id") id: String?
    ): IdResponseWrapper<Performance>
}

interface ApiClient {
    @POST("/api/v1/local/signup")
    suspend fun signup(
        @Body signUpRequest: SignUpRequest
    ): Response<SignUpResponse>

    @POST("/api/v1/local/signin")
    suspend fun signin(
        @Body signInRequest: SignInRequest
    ): Response<SignInResponse>

    @POST("/api/v1/auth/signout")
    suspend fun signout(
        @Header("Authorization") token: String
    ): Unit

    @POST("/api/v1/refresh_token")
    suspend fun refresh_token(

    ): Response<SignInResponse>

    @GET("/api/v1/users/me")
    suspend fun me(
        @Header("Authorization") token:String
    ): User?

    @GET("/api/v1/performance/search")
    suspend fun getPerformances(
        @Query("title") category: String?,
        @Query("category") title: String?
    ): Response<List<Performance>>

    @GET("/api/v1/performance/{performanceId}")
    suspend fun getPerformanceDetail(
        @Path("performanceId") performanceId: String
    ): Response<Performance>

    @GET("api/v1/seat/{performanceEventId}/available")
    suspend fun getAvailableSeats(@Query("eventId") eventId: String): SeatResponse

    @POST("/api/v1/reservation/reserve")
    suspend fun reserveSeat(
        @Body reservationRequest: ReservationRequest
    ): Response<ReservationResponse>

    @POST("/api/v1/reservation/cancel")
    suspend fun cancelReservation(
        @Body cancelRequest: CancelRequest
    ): Response<SeatResponse>

    @GET("/api/v1/performance-event/{performanceId}/{performanceDate}")
    suspend fun getPerformanceEvent(
        @Header("Authorization") token:String,
        @Path("performanceId") performanceId: String,
        @Path("performanceDate") performanceDate: String,
        @Query("user") user: User
    ): Response<PerformanceEvent>

}