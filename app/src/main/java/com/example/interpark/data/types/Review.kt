package com.example.interpark.data.types

import java.time.LocalDateTime


data class Review(
    val id: String,
    val author: String,
    val performance: String,
    val rating: Float,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

