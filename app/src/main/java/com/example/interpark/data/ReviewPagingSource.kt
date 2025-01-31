package com.example.interpark.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.interpark.data.API.RetrofitInstance
import com.example.interpark.data.types.Review

class ReviewPagingSource(
    private val performanceId: String
) : PagingSource<String, Review>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Review> {
        val cursor = params.key

        return try {
            val response = RetrofitInstance.api1.getReviews(performanceId, cursor)

            LoadResult.Page(
                data = response.data,
                prevKey = null,
                nextKey = if (response.hasNext) response.nextCursor else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Review>): String? {
        return null
    }
}
