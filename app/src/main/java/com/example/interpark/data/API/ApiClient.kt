package com.example.interpark.data.API

import com.example.interpark.data.Performance
import com.example.interpark.data.SignInRequest
import com.example.interpark.data.SignInResponse
import com.example.interpark.data.SignUpRequest
import com.example.interpark.data.SignUpResponse
import com.example.interpark.data.User
import com.example.interpark.data.UserProfile
import com.squareup.moshi.JsonClass
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
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
    @POST("/api/v1/signup")
    suspend fun signup(
        @Body signUpRequest: SignUpRequest
    ): Response<SignUpResponse>

    @POST("/api/v1/signin")
    suspend fun signin(
        @Body signInRequest: SignInRequest
    ): Response<SignInResponse>

    @POST("/api/v1/signout")
    suspend fun signout(
        @Header("Authorization") token: String
    ): Unit

    @GET("/api/v1/users/me")
    suspend fun me(
        @Header("Authorization") token:String
    ):User?

    @GET("/v1/performance/search")
    suspend fun getPerformances(
//        @Query("title") category: String?,
//        @Query("category") title: String?
    ): Response<List<Performance>>
}