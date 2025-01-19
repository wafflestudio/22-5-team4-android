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


class SeatSelectionViewModel(private val repository: SeatRepository) : ViewModel() {

    private val _seats = MutableLiveData<List<Seat>>()
    val seats: LiveData<List<Seat>> get() = _seats

    private val _reservationResult = MutableLiveData<SeatResponse>()
    val reservationResult: LiveData<SeatResponse> get() = _reservationResult


    fun fetchAvailableSeats(eventId: String) {
        viewModelScope.launch {
            val seats = withContext(Dispatchers.IO) {
                try {
                    val response = repository.fetchAvailableSeats(eventId)
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
    fun reserveSeat(seatRequest: SeatRequest) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.reserveSeat(seatRequest)
                }
                _reservationResult.postValue(response) // 예약 결과를 LiveData로 전달
            } catch (e: Exception) {
                Log.e("SeatSelectionViewModel", "Error reserving seat", e)
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


}