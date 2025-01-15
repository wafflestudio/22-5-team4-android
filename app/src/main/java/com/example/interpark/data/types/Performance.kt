package com.example.interpark.data.types

import com.squareup.moshi.Json
import java.time.LocalDate

data class Performance(
    val id: String,
    val title: String,
//    val content: String,
    val detail: String?,
    @Json(name = "performanceDates") val date: List<LocalDate>,
    @Json(name = "posterUri") val posterUrl: String?,
    @Json(name = "backdropImageUri") val backdropUrl: String?,

    val category: String,
    @Json(name = "hallName") val location: String,
)

