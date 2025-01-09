package com.example.interpark.data

import android.util.Log
import com.example.interpark.data.API.ApiClientDev
import com.example.interpark.data.API.ResponseWrapper

class PerformanceRepository(private val ApiClientDev: ApiClientDev) {
    suspend fun getAllPerformances(): List<Performance> {
        val result = ApiClientDev.geAllPerformances()
        return result.result
    }

    suspend fun fetchPerformances(category: String?, title: String?): List<Performance> {
        val result = ApiClientDev.getPerformances(category, title)
        Log.d("repository", result.result.toString())
        return result.result
    }
}