package com.example.interpark.data

import com.example.interpark.auth.AuthManager
import com.example.interpark.data.API.ApiClient
import com.example.interpark.data.types.Review
import com.example.interpark.data.types.ReviewRequestBody
import com.example.interpark.data.types.User
import retrofit2.Response

class ReviewRepository(private val reviewApiService: ApiClient) {

    suspend fun writeReview(performanceId: String, reviewRequest: ReviewRequestBody, user: User): Response<Review> {
        return reviewApiService.writeReview(performanceId, reviewRequest)
    }

    suspend fun readReview(performanceId: String): Response<List<Review>>{
        return reviewApiService.readReview(performanceId)
    }

}