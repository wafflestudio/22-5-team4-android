package com.example.interpark.data.types

data class AdminPerformanceRequest(
    val title: String,
    val detail: String,
    val category: String,
    val posterUri: String,
    val backdropImageUri: String
)

data class AdminPerformanceHallRequest(
    val name: String,
    val address: String,
    val maxAudience: Int
)

data class AdminPerformanceEventRequest(
    val performanceId: String,
    val performanceHallId: String,
    val startAt: String,
    val endAt: String
)

data class AdminPerformanceResponse(
    val id: String,
    val title: String,
    val hallName: String,
    val performanceDates: List<String>,
    val detail: String,
    val category: String,
    val posterUri: String,
    val backdropImageUri: String
)

data class AdminPerformanceHallResponse(
    val id: String,
    val name: String,
    val address: String,
    val maxAudience: Int
)

data class AdminPerformanceEventResponse(
    val id: String,
    val performanceId: String,
    val performanceHallId: String,
    val startAt: String,
    val endAt: String
)
