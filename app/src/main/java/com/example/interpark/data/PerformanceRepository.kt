package com.example.interpark.data

import android.util.Log
import com.example.interpark.data.API.ApiClient

class PerformanceRepository(private val ApiClient: ApiClient) {
    suspend fun getAllPerformances(): List<Performance> {
        val result = ApiClient.geAllPerformances()
        return result.result
    }

    suspend fun fetchPerformances(category: String?, title: String?): List<Performance> {
        val result = ApiClient.getPerformances(category, title)
        Log.d("repository", result.result.toString())
        return result.result
    }

    suspend fun fetchPerformanceById(id: String?): Performance {
        val result = ApiClient.getPerformanceById(id) // 서버 API 호출
        Log.d("repository", result.toString()) // 결과를 로그로 출력
        return result.result
    }
}