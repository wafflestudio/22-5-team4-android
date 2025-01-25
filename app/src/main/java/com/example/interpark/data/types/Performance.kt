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
    val calendarDates: List<String>?,
    @Json(name = "posterUri") val posterUrl: String?,
    @Json(name = "backdropImageUri") val backdropUrl: String?,

    val category: String?,
    @Json(name = "hallName") val location: String,
)

data class Duration(
    val first: String? = null, // 기본값 설정
    val second: String? = null // 추가 필드도 동일하게 처리
)

data class PerformanceEvent(
    val id: String,
    val performanceId: String,
    val performanceHallId: String,
    val startAt: String,
    val endAt: String
)

data class PerformanceEventResponse(
    val performances: List<PerformanceEvent>
)