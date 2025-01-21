package com.example.interpark.auth

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.interpark.data.API.RetrofitInstance
import com.example.interpark.data.PerformanceRepository

class TokenRefresher(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        val repository = PerformanceRepository(RetrofitInstance.api1)
        val newAccessToken = repository.refresh_token()
        if (newAccessToken != null) {
            AuthManager.refreshToken(applicationContext, newAccessToken)
            return Result.success()
        }
        return Result.failure()
    }
}
