package com.example.interpark.data

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.interpark.auth.AuthManager
import com.example.interpark.data.API.ApiClient
import com.example.interpark.data.API.ApiClientDev
import com.example.interpark.data.types.Performance
import com.example.interpark.data.types.PerformanceEvent
import com.example.interpark.data.types.Review
import com.example.interpark.data.types.SignInRequest
import com.example.interpark.data.types.SignInResponse
import com.example.interpark.data.types.SignUpRequest
import com.example.interpark.data.types.SignUpResponse
import com.example.interpark.data.types.User
import java.time.LocalDateTime

class PerformanceRepository(private val ApiClientDev: ApiClientDev, private val ApiClient: ApiClient) {

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
            AuthManager.login(context, username, password)
        }
        return result.body()
    }

    suspend fun signOut( context: Context){

//        TODO(API 수정된 후 작업 필요)
//        ApiClient.signout()
//        if(API 요청이 성공적)
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

        val result = listOf(Review("1234", "qdrptd", "perf_id", 4.5f, "title_string", "content_string", LocalDateTime.now(), LocalDateTime.now()),
            Review("1234", "qdrptd", "perf_id", 4.5f, "title_string", "content_string", LocalDateTime.now(), LocalDateTime.now()),
            Review("1234", "qdrptd", "perf_id", 4.5f, "title_string", "content_string", LocalDateTime.now(), LocalDateTime.now()),
            Review("1234", "qdrptd", "perf_id", 4.5f, "title_string", "content_string", LocalDateTime.now(), LocalDateTime.now()),
            Review("1234", "qdrptd", "perf_id", 4.5f, "title_string", "content_string", LocalDateTime.now(), LocalDateTime.now()),
            Review("1234", "qdrptd", "perf_id", 4.5f, "title_string", "content_string", LocalDateTime.now(), LocalDateTime.now()),
            Review("1234", "qdrptd", "perf_id", 4.5f, "title_string", "content_string", LocalDateTime.now(), LocalDateTime.now()),
            Review("1234", "qdrptd", "perf_id", 4.5f, "title_string", "content_string", LocalDateTime.now(), LocalDateTime.now()))
        return result
    }

    suspend fun getPerformanceEvent(
        token: String?,
        performanceId: String,
        performanceDate: String,
        user: User
    ): PerformanceEvent? {
        return try {
            val response = ApiClient.getPerformanceEvent("Bearer $token",performanceId, performanceDate, user)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}