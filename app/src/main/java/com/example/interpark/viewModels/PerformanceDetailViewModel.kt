package com.example.interpark.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interpark.data.Performance
import com.example.interpark.data.PerformanceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PerformanceDetailViewModel(private val repository: PerformanceRepository) : ViewModel() {

    private val _performanceDetail = MutableLiveData<Performance>()
    val performanceDetail: LiveData<Performance> get() = _performanceDetail

    fun fetchPerformanceDetail(id: String) {
        viewModelScope.launch {
            val performance = withContext(Dispatchers.IO) {
                repository.fetchPerformanceById(id) // 서버에서 ID로 공연 정보 가져오기
            }
            _performanceDetail.postValue(performance)
            Log.d("PerformanceDetailVM", "Fetched performance: $performance")
        }
    }
}
