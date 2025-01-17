package com.example.interpark.data.types

import com.squareup.moshi.Json
import java.time.LocalDate

data class Performance(
    val id: String,
    val title: String,
//    val content: String,
    val detail: String?,
//    @Json(name = "performanceDates") val date: List<LocalDate>,
    @Json(name = "performanceDuration")
    val performanceDates: Duration?,
    @Json(name = "performanceDates")
    val calendarDates: List<Duration>?,
    @Json(name = "posterUri") val posterUrl: String?,
    @Json(name = "backdropImageUri") val backdropUrl: String?,

    val category: String?,
    @Json(name = "hallName") val location: String,
)

data class Duration(
    val first: String,
    val second: String
)