package com.example.interpark.data.types

import java.time.LocalDateTime


data class Review(
    val id: String,
    val author: String,
    val rating: Float,
    val title: String,
    val content: String,
    val createdAt: String,
    val updatedAt: String,
    val likeCount: Int,
    val replyCount: Int
)

data class ReviewRequestBody(
    val rating: Int,
    val title: String,
    val content: String
)

data class ReviewRequest(
    val request: ReviewRequestBody,
    val user: User
)

sealed class ReviewError {
    object Unauthorized : ReviewError() // 로그인이 안 되어 있는 경우
    object ServerError : ReviewError() // 서버 문제
    object NetworkError : ReviewError() // 네트워크 문제
    data class Unknown(val message: String?) : ReviewError() // 기타 원인
}