package com.example.interpark.viewModels

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.interpark.data.API.RetrofitInstance
import com.example.interpark.data.PerformanceRepository
import com.example.interpark.data.SeatRepository

class SeatSelectionViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SeatSelectionViewModel::class.java)) {
            val apiService = RetrofitInstance.api1
            val repository = SeatRepository(apiService)
            return SeatSelectionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
