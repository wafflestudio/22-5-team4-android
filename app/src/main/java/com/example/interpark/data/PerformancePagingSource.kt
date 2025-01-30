package com.example.interpark.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.interpark.data.API.ApiClient
import com.example.interpark.data.types.Performance

class PerformancePagingSource(
    private val apiService: ApiClient,
    private val title: String?,
    private val category: String?
) : PagingSource<String, Performance>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Performance> {
        val cursor = params.key  // 이전 요청에서 받은 cursor 값을 가져옴

        return try {
            val response = apiService.getPerformances2(title, category, cursor)

            LoadResult.Page(
                data = response.data,
                prevKey = null,  // 이전 페이지 없음
                nextKey = if (response.hasNext) response.nextCursor else null // 다음 요청에 사용할 cursor 설정
            )
        } catch (e: Exception) {
            LoadResult.Error(e) // 에러 발생 시 처리
        }
    }

    override fun getRefreshKey(state: PagingState<String, Performance>): String? {
        return null // 새로고침 시 처음부터 다시 불러오기
    }
}
