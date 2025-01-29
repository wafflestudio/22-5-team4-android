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
    val performanceDates: PerformanceDuration,
    @Json(name = "performanceDates")
    val calendarDates: List<String>?,
    @Json(name = "posterUri") val posterUrl: String?,
    @Json(name = "backdropImageUri") val backdropUrl: String?,

    val category: String?,
    @Json(name = "hallName") val location: String,
){
    // 날짜 변환 로직 추가
    fun getFormattedDate(): String {
        return when {
            performanceDates.date != null -> performanceDates.date // 단일 날짜
            performanceDates.start != null && performanceDates.end != null ->
                "${performanceDates.start} - ${performanceDates.end}" // 시작 & 종료 날짜
            else -> "날짜 정보 없음"
        }
    }
}

data class Duration(
    val first: String? = null, // 기본값 설정
    val second: String? = null // 추가 필드도 동일하게 처리
)

data class PerformanceDuration(
    val date: String?,  // 단일 날짜 공연 (예: 2025-01-29)
    val start: String?, // 시작 날짜 (예: 2024-11-29)
    val end: String?    // 종료 날짜 (예: 2025-05-18)
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

data class PerformanceResponse(
    val page: Int,
    val totalPages: Int,
    val pageSize: Int,
    val totalResults: Int,
    val performances: List<Performance>
)