package com.example.interpark.data

import com.example.interpark.data.API.ApiClient

class SeatRepository(private val seatApiService: ApiClient) {

    suspend fun fetchAvailableSeats(eventId: String): SeatResponse {
        return seatApiService.getAvailableSeats(eventId)
    }


    suspend fun reserveSeat(seatRequest: SeatRequest): SeatResponse {
        return seatApiService.reserveSeat(seatRequest)
    }

    suspend fun cancelReservation(cancelRequest: CancelRequest) {
        seatApiService.cancelReservation(cancelRequest)
    }
}