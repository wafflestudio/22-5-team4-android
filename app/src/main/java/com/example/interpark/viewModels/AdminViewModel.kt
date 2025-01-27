package com.example.interpark.viewModels

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
                val response = repository.adminCreatePerformance(token, request)
                _performanceId.value = response.id
                onSuccess()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun createPerformanceHall(request: AdminPerformanceHallRequest, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val response = repository.adminCreatePerformanceHall(request)
                _hallId.value = response.id
                onSuccess()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun createPerformanceEvent(request: AdminPerformanceEventRequest, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                _loading.value = true
                repository.adminCreatePerformanceEvent(request)
                onSuccess()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
}
