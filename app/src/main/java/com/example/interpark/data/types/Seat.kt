package com.example.interpark.data

data class Seat(
    var id: String,
    val row: Int,
    val number: Int,
    var isAvailable: Boolean,
    var isSelected: Boolean = false, // 사용자가 선택하면 true로 변경
//    val reservationId: String // 좌석별 고유 reservationId 추가

)



data class SeatRequest(
    val row: Int,
    val number: Int
)

data class CancelRequest(
    val row: String,
    val number: Int,
    val performanceEventId: String
)

data class SeatResponse(
    val availableSeats: List<SeatDetail>
)

data class AvailableSeat(
    val reservationId: String,
    val seat: SeatDetail
)

data class SeatDetail(
    val id: String,
    val seatNumber: SeatNumber,
    val price: Int
)

data class SeatNumber(
    val first: Int,
    val second: Int
)

data class ReservedSeat(
    val id: String,
    val performanceTitle: String,
    val posterUri: String,
    val performanceHallName: String,
    val seat: SeatDetail,
    val performanceStartAt: String,
    val performanceEndAt: String,
    val reservationDate: String
)

data class MyReservationResponse(
    val myReservations: List<MyReservation>
)

data class MyReservation(
    val id: String,
    val performanceTitle: String,
    val posterUri: String,
    val performanceDate: String,
    val reservationDate: String
)
