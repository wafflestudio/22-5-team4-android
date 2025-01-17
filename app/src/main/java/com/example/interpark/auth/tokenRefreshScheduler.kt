package com.example.interpark.auth

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

fun scheduleTokenRefresh(context: Context) {
    val workRequest = PeriodicWorkRequestBuilder<TokenRefresher>(
        15, TimeUnit.MINUTES // 1시간마다 실행
    ).build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "TokenRefreshWorker", // 작업의 고유 이름
        ExistingPeriodicWorkPolicy.KEEP, // 기존 작업 유지
        workRequest
    )
}