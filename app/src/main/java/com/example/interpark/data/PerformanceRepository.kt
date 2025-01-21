package com.example.interpark.data

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.interpark.auth.AuthManager
import com.example.interpark.data.API.ApiClient
import com.example.interpark.data.types.Performance
import com.example.interpark.data.types.PerformanceEvent
import com.example.interpark.data.types.Review
import com.example.interpark.data.types.SignInRequest
import com.example.interpark.data.types.SignInResponse
import com.example.interpark.data.types.SignUpRequest
import com.example.interpark.data.types.SignUpResponse
import com.example.interpark.data.types.User
import java.time.LocalDateTime

class PerformanceRepository(private val ApiClient: ApiClient) {

    suspend fun fetchPerformanceById(id: String): Performance? {
        val result = ApiClient.getPerformanceDetail(id) // 서버 API 호출
        Log.d("repository", result.toString()) // 결과를 로그로 출력
        return result.body()
    }

    suspend fun getPerformances(category: String?, title: String?): List<Performance>? {
        val result = ApiClient.getPerformances(title, category)
        return result.body()
    }
    suspend fun getPerformanceDetail(performanceId: String): Performance? {
        return try {
            val response = ApiClient.getPerformanceDetail(performanceId)
            if (response.isSuccessful) {
                response.body()
            } else {
                null // 실패 시 null 반환
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun signUp(username: String, password: String, nickname: String, phoneNumber: String, email: String): SignUpResponse? {
        val result = ApiClient.signup(SignUpRequest(username, password, nickname, phoneNumber, email))
        Log.d("SignUp", result.body().toString())
        return result.body()
    }

    suspend fun signIn(username: String, password: String, context: Context): SignInResponse?{
        val result = ApiClient.signin(SignInRequest(username, password))
        if(result.code().toString().first() == '2'){
            AuthManager.login(context, result.body()!!.user.nickname, result.body()!!.accessToken, result.body()!!.user)
        }
        return result.body()
    }

    suspend fun signOut( context: Context){
        val result = ApiClient.signout()
        if(result.code().toString() == "204"){
            AuthManager.logout(context)
        }
        AuthManager.logout(context)
    }

    suspend fun me(token: String?): User?{
        val result = ApiClient.me("Bearer $token")
        return result
    }

    suspend fun refresh_token(): String?{
        val result = ApiClient.refresh_token()
        return result.body()?.accessToken
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun fetchPerformanceReview(perfId: String?): List<Review>{

        val result = listOf(Review("1234", "qdrptd", "perf_id", 4.5f, "title_string", "content_string", "2025-01-21T09:44:17.456Z", "2025-01-21T09:44:17.456Z"),
            Review("1234", "qdrptd", "perf_id", 4.5f, "title_string", "content_string", "2025-01-21T09:44:17.456Z", "2025-01-21T09:44:17.456Z"),
            Review("1234", "qdrptd", "perf_id", 4.5f, "title_string", "content_string", "2025-01-21T09:44:17.456Z", "2025-01-21T09:44:17.456Z"),
            Review("1234", "qdrptd", "perf_id", 4.5f, "title_string", "content_string", "2025-01-21T09:44:17.456Z", "2025-01-21T09:44:17.456Z"),
            Review("1234", "qdrptd", "perf_id", 4.5f, "title_string", "content_string", "2025-01-21T09:44:17.456Z", "2025-01-21T09:44:17.456Z"),
            Review("1234", "qdrptd", "perf_id", 4.5f, "title_string", "content_string", "2025-01-21T09:44:17.456Z", "2025-01-21T09:44:17.456Z"),
            Review("1234", "qdrptd", "perf_id", 4.5f, "title_string", "content_string", "2025-01-21T09:44:17.456Z", "2025-01-21T09:44:17.456Z"),
            Review("1234", "qdrptd", "perf_id", 4.5f, "title_string", "content_string", "2025-01-21T09:44:17.456Z", "2025-01-21T09:44:17.456Z"))

        return result
    }

    suspend fun getPerformanceEvent(
        token: String?,
        performanceId: String,
        performanceDate: String
    ): List<PerformanceEvent>? {
        return try {
            // API 호출
            val response = ApiClient.getPerformanceEvent("Bearer $token", performanceId, performanceDate)

            if (response.isSuccessful) {
                // 응답 본문을 리스트로 파싱
                response.body()
            } else {
                Log.e("API Error", "Response failed with code: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getPosterUris(category: String?, title: String?): List<String>? {
        val performances = getPerformances(category, title) // 기존 getPerformances 호출
        return performances?.mapNotNull { it.posterUrl } // posterUri만 추출
    }


}