package com.example.interpark.data.API

import com.example.interpark.data.Performance
import com.example.interpark.data.SignInRequest
import com.example.interpark.data.SignInResponse
import com.example.interpark.data.SignUpRequest
import com.example.interpark.data.UserProfile
import com.squareup.moshi.JsonClass
import retrofit2.Call
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

interface ApiClientDev {
    @GET("/performances")
    suspend fun geAllPerformances(
    ): ResponseWrapper<Performance>

    @GET("/performances/filter")
    suspend fun getPerformances(
        @Query("category") category: String?,
        @Query("title") title: String?
    ): ResponseWrapper<Performance>
}
