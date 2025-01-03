package com.example.interpark.data

data class Seat(
    val row: String, // 좌석의 행 정보 (예: "A", "B")
    val number: Int, // 좌석 번호 (예: 1, 2)
    var isAvailable: Boolean = true, // 좌석 예약 가능 여부
    var isSelected: Boolean = false // 사용자가 선택했는지 여부
)
