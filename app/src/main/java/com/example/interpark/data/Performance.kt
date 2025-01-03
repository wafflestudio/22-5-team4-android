package com.example.interpark.data

data class Performance(
    val title: String,
    val location: String,
    val dateRange: String,
    val imageUrl: Int // 실제 이미지는 drawable 리소스 사용
)
