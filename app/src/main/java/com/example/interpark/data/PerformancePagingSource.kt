package com.example.interpark.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.interpark.data.API.ApiClient
import com.example.interpark.data.API.RetrofitInstance
import com.example.interpark.data.types.Performance

class PerformancePagingSource(
    private val category: String?,
    private val title: String?
) : PagingSource<String, Performance>() {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun load(params: LoadParams<String>): LoadResult<String, Performance> {
        val cursor = params.key // 이전 페이지에서 넘겨준 커서 값 (null이면 첫 페이지)

        return try {
            val response = RetrofitInstance.api1.getPerformances2(title,category, cursor)

            LoadResult.Page(
                data = response.data,
                prevKey = null, // 첫 번째 페이지에는 이전 페이지 없음
                nextKey = if (response.hasNext) response.nextCursor else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Performance>): String? {
        return null // 새로고침 시 처음부터 다시 불러오기
    }
}
