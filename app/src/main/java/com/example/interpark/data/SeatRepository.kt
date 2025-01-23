package com.example.interpark.data

import android.util.Log
import com.example.interpark.data.API.ApiClient
import com.example.interpark.data.types.User
import com.google.android.gms.common.api.Response

class SeatRepository(private val seatApiService: ApiClient) {

    suspend fun fetchAvailableSeats(performanceEventId: String): SeatResponse {
        // API 호출
        Log.d("SeatRepository", "Performance Event ID: $performanceEventId")
        return seatApiService.getAvailableSeats(performanceEventId)
    }

//    suspend fun reserveSeat(reservationRequest: ReservationRequest): retrofit2.Response<ReservationResponse> {
//        return seatApiService.reserveSeat(reservationRequest)
//    }

    suspend fun reserveSeat(reservationRequest: ReservationRequest): ReservationResponse? {
        val result = seatApiService.reserveSeat(reservationRequest)
        Log.d("ReserveSeat", "Observed reservationId: $reservationRequest")
        Log.d("ReserveSeat", result.body().toString())
        return result.body()
    }


    suspend fun cancelReservation(cancelRequest: CancelRequest) {
        seatApiService.cancelReservation(cancelRequest)
    }

    suspend fun fetchReservations(user: User?): List<MyReservation> {
        return try {
            val response = seatApiService.getReservations(user)
            if (response.isSuccessful) {
                response.body()?.myReservations ?: emptyList()
            } else {
                Log.e("ReservationRepository", "Error: ${response.code()} - ${response.message()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("ReservationRepository", "Exception occurred", e)
            emptyList()
        }
    }
}