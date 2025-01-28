package com.example.interpark.data.types

// 요청 본문 매핑
data class ReservationRequest(
    val performanceEventId: String,
    val seatId: String
)



data class RequestData(
    val reservationId: String // reservationId를 포함하는 데이터 클래스
)

data class UserData(
    val id: String,
    val username: String,
    val nickname: String,
    val phoneNumber: String,
    val email: String
)

// 응답 매핑
data class ReservationResponse(
    val reservationId: String
)
