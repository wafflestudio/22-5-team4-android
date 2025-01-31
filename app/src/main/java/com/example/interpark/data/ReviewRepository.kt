package com.example.interpark.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.interpark.auth.AuthManager
import com.example.interpark.data.API.ApiClient
import com.example.interpark.data.types.Comment
import com.example.interpark.data.types.CommentRequestBody
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

    suspend fun writeComment(reviewId: String, content: String): Response<Comment>{
        return reviewApiService.writeComment(reviewId, CommentRequestBody(content))
    }

    suspend fun readComment(reviewId: String): Response<List<Comment>>{
        return reviewApiService.readComment(reviewId)
    }
    fun getReviewPager(performanceId: String): Pager<String, Review> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ReviewPagingSource(performanceId) }
        )
    }
}