package com.example.interpark.data.API

import com.example.interpark.data.Performance
import com.squareup.moshi.JsonClass
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

@JsonClass(generateAdapter = true)
data class ResponseWrapper<T>(
    val result: List<T>
)

interface ApiClient {
    @GET("/performances")
    suspend fun geAllPerformances(
    ): ResponseWrapper<Performance>


    @GET("/performances/filter")
    suspend fun getPerformances(
        @Query("category") category: String?,
        @Query("title") title: String?
    ): ResponseWrapper<Performance>

    @GET("/performances/search")
    suspend fun searchPerformances(
        @Query("query") query: String
    ): ResponseWrapper<Performance>
}