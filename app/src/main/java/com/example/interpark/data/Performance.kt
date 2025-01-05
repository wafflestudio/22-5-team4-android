package com.example.interpark.data

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import java.time.LocalDate

data class Performance(
    val id: String,
    val title: String,
    val content: String,
    val detail: String,
    val date: List<LocalDate>,
    @Json(name = "poster_url") val posterUrl: String,
    @Json(name = "backdrop_url") val backdropUrl: String,
    val category: String,
    val location: String,
)
