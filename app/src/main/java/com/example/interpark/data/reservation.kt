package com.example.interpark.data


data class ReservationRequest(
    val request: Request,
    val user: User? = null // user를 nullable로 설정
) {
    data class Request(
        val reservationId: String // 예약 ID (비워둘 경우 서버에서 생성)
    )
    data class User(
        val id: String,
        val username: String,
        val nickname: String,
        val phoneNumber: String,
        val email: String
    )
}


data class ReservationResponse(
    val reservationId: String // 서버에서 반환된 예약 ID
)
