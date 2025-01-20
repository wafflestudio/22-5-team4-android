package com.example.interpark.viewModels

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.interpark.data.API.RetrofitInstance
import com.example.interpark.data.PerformanceRepository

class CalendarViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            val apiServiceDev = RetrofitInstance.api
            val apiServiceServer = RetrofitInstance.api1
            val repository = PerformanceRepository(apiServiceDev, apiServiceServer)
            return CalendarViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
