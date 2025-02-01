package com.example.interpark.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interpark.data.PerformanceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CalendarViewModel(private val repository: PerformanceRepository) : ViewModel() {

    private val _performanceDates = MutableLiveData<List<String>?>()
    val performanceDates: MutableLiveData<List<String>?> get() = _performanceDates

    // 특정 공연 ID에 대한 날짜 가져오기
    fun fetchPerformanceDates(id: String) {
        viewModelScope.launch {
            try {
                val performance = withContext(Dispatchers.IO) {
                    repository.fetchPerformanceById(id) // 서버에서 ID로 공연 정보 가져오기
                }
                _performanceDates.postValue(performance?.calendarDates)
                Log.d("CalendarViewModel", "Fetched dates: ${performance?.calendarDates}")
            } catch (e: Exception) {
                Log.e("CalendarViewModel", "Error fetching dates", e)
            }
        }
    }
}
