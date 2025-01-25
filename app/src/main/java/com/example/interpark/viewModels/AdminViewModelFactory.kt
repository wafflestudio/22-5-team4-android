package com.example.interpark.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.interpark.data.API.RetrofitInstance
import com.example.interpark.data.PerformanceRepository

class AdminViewModelFactory(private val context: Context):ViewModelProvider.Factory {
    @androidx.annotation.RequiresApi(android.os.Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(AdminViewModel::class.java)) {
        val apiServiceServer = RetrofitInstance.api1
        val repository = PerformanceRepository(apiServiceServer)
        return AdminViewModel(repository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
    }
}