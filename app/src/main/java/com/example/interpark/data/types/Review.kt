package com.example.interpark.data.types

import java.time.LocalDateTime


data class Review(
    val id: String,
    val author: String,
    val performance: String,
    val rating: Float,
    val title: String,
    val content: String,
    val createdAt: String,
    val updatedAt: String
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
