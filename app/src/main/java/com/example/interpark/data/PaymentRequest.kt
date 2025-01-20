package com.example.interpark.data


data class PaymentRequest(
    val request: Map<String, String>,
    val user: Map<String, String>
)
