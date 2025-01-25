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

                    // reservationId 추출
                    val reservationIds = response.availableSeats.map { it.reservationId }
                    _reservationId.postValue(reservationIds)

                    response.availableSeats.map { availableSeat ->
                        Seat(
                            row = availableSeat.seat.seatNumber.first,
                            number = availableSeat.seat.seatNumber.second,
                            isAvailable = true, // API에 따로 예약 가능 여부가 없으므로 기본값 설정
                            //price = availableSeat.seat.price

                        )

                    }
                } catch (e: Exception) {
                    Log.e("SeatSelectionViewModel", "Error fetching seats", e)
                    listOf<Seat>() // 오류 발생 시 빈 리스트 반환
                }
            }
            _seats.postValue(seats)
        }
    }


    fun reserveSeat(reservationRequest: ReservationRequest) {
        viewModelScope.launch {
            try {
                // Nullable 타입을 처리
//                val result: ReservationResponse? = withContext(Dispatchers.IO) {
//                    repository.reserveSeat(reservationRequest)
//                }
                val result = repository.reserveSeat(reservationRequest)
                if (result != null) {
                    // 성공적으로 예약된 경우
                    _reservationSuccess.value = true
                    Log.d("SeatReservationViewModel", "Reservation successful: ${result.reservationId}")
                } else {
                    // 실패한 경우
                    _reservationFailed.value = true
                    Log.e("SeatReservationViewModel", "Error: Reservation result is null")
                }
            } catch (e: Exception) {
                // 예외 처리
                _reservationFailed.value = true
                Log.e("SeatReservationViewModel", "Error reserving seat", e)
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