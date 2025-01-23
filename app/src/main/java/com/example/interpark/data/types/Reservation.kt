package com.example.interpark.data.types

// 요청 본문 매핑
data class ReservationRequest(
    val request: RequestData,
    val user: UserData
)

data class RequestData(
    val reservationId: String
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
