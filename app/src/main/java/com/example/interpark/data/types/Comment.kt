package com.example.interpark.data.types

import java.time.LocalDateTime

data class Comment(
    val id: String,
    val author: String,
    val content: String,
    val createdAt: String,
    val updatedAt: String
)

data class CommentRequestBody(
    val content: String
)


sealed class CommentError {
    object Unauthorized : ReviewError() // 로그인이 안 되어 있는 경우
    object ServerError : ReviewError() // 서버 문제
    object NetworkError : ReviewError() // 네트워크 문제
    data class Unknown(val message: String?) : ReviewError() // 기타 원인
}