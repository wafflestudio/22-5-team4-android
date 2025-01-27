package com.example.interpark.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interpark.auth.AuthManager
import com.example.interpark.data.PerformanceRepository
import com.example.interpark.data.types.AdminPerformanceEventRequest
import com.example.interpark.data.types.AdminPerformanceHallRequest
import com.example.interpark.data.types.AdminPerformanceRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdminViewModel(private val repository: PerformanceRepository) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    private val _performanceId = MutableStateFlow<String?>(null)
    val performanceId: StateFlow<String?> get() = _performanceId

    private val _hallId = MutableStateFlow<String?>(null)
    val hallId: StateFlow<String?> get() = _hallId

    fun createPerformance(request: AdminPerformanceRequest, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val token = AuthManager.getAuthToken()

                // API 호출 및 응답 처리
                val response = repository.adminCreatePerformance(token, request)

                // Performance ID 저장
                _performanceId.value = response.id

                // 로그 출력
                Log.d("AdminViewModel", "Performance ID 설정됨 (Response ID): ${response.id}")
                Log.d("AdminViewModel", "Performance ID 설정됨 (StateFlow 값): ${_performanceId.value}")

                // 성공 콜백 실행
                onSuccess()
            } catch (e: Exception) {
                // 에러 처리
                _error.value = e.message
                Log.e("AdminViewModel", "에러 발생: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }


    fun createPerformanceHall(request: AdminPerformanceHallRequest, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val token = AuthManager.getAuthToken()

                // API 호출 및 응답 처리
                val response = repository.adminCreatePerformanceHall(token, request)

                // Hall ID 저장
                _hallId.value = response.id

                // 로그 출력
                Log.d("AdminViewModel", "Hall ID 설정됨 (Response ID): ${response.id}")
                Log.d("AdminViewModel", "Hall ID 설정됨 (StateFlow 값): ${_hallId.value}")

                // 성공 콜백 실행
                onSuccess()
            } catch (e: Exception) {
                // 에러 처리
                _error.value = e.message
                Log.e("AdminViewModel", "에러 발생: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }


    fun createPerformanceEvent(request: AdminPerformanceEventRequest, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val token = AuthManager.getAuthToken()

                repository.adminCreatePerformanceEvent(token, request)
                onSuccess()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
}
