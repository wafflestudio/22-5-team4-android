package com.example.interpark.data


data class ReservationRequest(
    val request: Request,
    val user: User
) {
    data class Request(
        val reservationId: String // 예약 ID (비워둘 경우 서버에서 생성)
    )

    data class User(
        val id: String,           // 사용자 ID
        val username: String,     // 사용자 이름
        val nickname: String,     // 사용자 닉네임
        val phoneNumber: String,  // 전화번호
        val email: String         // 이메일
    )
}


data class ReservationResponse(
    val reservationId: String // 서버에서 반환된 예약 ID
)
