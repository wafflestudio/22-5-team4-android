package com.example.interpark.viewModels

import android.util.Log
import androidx.lifecycle.*
import androidx.navigation.fragment.navArgs
import com.example.interpark.data.API.ApiClient
import com.example.interpark.data.CancelRequest
import com.example.interpark.data.Seat
import com.example.interpark.data.SeatRepository
import com.example.interpark.data.SeatRequest
import com.example.interpark.data.SeatResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.navigation.fragment.navArgs
import com.example.interpark.data.MyReservation
import com.example.interpark.data.ReservationResponse
import com.example.interpark.data.types.ReservationRequest
import com.example.interpark.data.types.User


class SeatSelectionViewModel(private val repository: SeatRepository) : ViewModel() {

    private val _seats = MutableLiveData<List<Seat>>()
    val seats: LiveData<List<Seat>> get() = _seats

    private val _reservationId = MutableLiveData<List<String>>()
    val reservationId : LiveData<List<String>> get() = _reservationId

    private val _reservationResult = MutableLiveData<SeatResponse>()
    val reservationResult: LiveData<SeatResponse> get() = _reservationResult

    private val _reservationSuccess = MutableLiveData<Boolean>()
    val reservationSuccess: LiveData<Boolean> get() = _reservationSuccess

    private val _reservationFailed = MutableLiveData<Boolean>()
    val reservationFailed: LiveData<Boolean> get() = _reservationFailed


    fun fetchAvailableSeats(eventId: String) {
        viewModelScope.launch {
            val seats = withContext(Dispatchers.IO) {
                try {

                    val response = repository.fetchAvailableSeats(eventId)

                    response.availableSeats.map { availableSeat ->

                        Seat(
                            id = availableSeat.id, // Seat ID 설정
                            row = availableSeat.seatNumber.first,
                            number = availableSeat.seatNumber.second,
                            isAvailable = true // 기본적으로 예약 가능으로 설정
                        )
                    }
                } catch (e: Exception) {
                    Log.e("SeatSelectionViewModel", "Error fetching seats", e)
                    listOf<Seat>() // 오류 발생 시 빈 리스트 반환
                }
            }

            Log.d("SeatSelectionViewModel", "Seats loaded: $seats")
            _seats.postValue(seats)
        }
    }

    fun reserveSeat2(eventId: String, seatId: String) {
        viewModelScope.launch {
            repository.reserveSeat(eventId, seatId)

        }
    }


    fun reserveSeat(eventId: String, seatId: String, onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.reserveSeat(eventId, seatId)
                }
                onSuccess(response.reservationId)
            } catch (e: Exception) {
                onFailure(e.message ?: "예약 중 오류가 발생했습니다.")
            }
        }
    }




    fun cancelReservation(cancelRequest: CancelRequest) {
        viewModelScope.launch {
            try {
                repository.cancelReservation(cancelRequest)
                fetchAvailableSeats(cancelRequest.performanceEventId) // 좌석 상태 갱신
            } catch (e: Exception) {
                e.printStackTrace() // 에러 처리
            }
        }
    }

    private val _reservations = MutableLiveData<List<MyReservation>>()
    val reservations: LiveData<List<MyReservation>> get() = _reservations

    fun fetchReservations() {
        viewModelScope.launch {
            val reservations = repository.fetchReservations(null)
            _reservations.postValue(reservations)
        }
    }

}