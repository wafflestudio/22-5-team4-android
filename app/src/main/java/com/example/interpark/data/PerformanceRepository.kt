package com.example.interpark.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.interpark.data.API.ApiClient
import com.example.interpark.data.API.ApiClientDev
import com.example.interpark.data.API.ResponseWrapper
import com.example.interpark.data.types.Review
import java.time.LocalDateTime

class PerformanceRepository(private val ApiClientDev: ApiClientDev, private val ApiClient: ApiClient) {
    suspend fun getAllPerformances(): List<Performance> {
        val result = ApiClientDev.getAllPerformances()
        return result.result
    }

    suspend fun fetchPerformances(category: String?, title: String?): List<Performance> {
        val result = ApiClientDev.getPerformances(category, title)
        return result.result
    }

    suspend fun fetchPerformanceById(id: String?): Performance {
        val result = ApiClientDev.getPerformanceById(id) // 서버 API 호출
        Log.d("repository", result.toString()) // 결과를 로그로 출력
        return result.result
    }

    suspend fun signUp(username: String, password: String, nickname: String, phoneNumber: String, email: String): SignUpResponse? {
        val result = ApiClient.signup(SignUpRequest(username, password, nickname, phoneNumber, email))
        Log.d("SignUp", result.body().toString())
        return result.body()
    }

    suspend fun signIn(username: String, password: String): SignInResponse?{
        val result = ApiClient.signin(SignInRequest(username, password))
        return result.body()
    }

    suspend fun signOut(token: String?){
        ApiClient.signout("Bearer $token")
    }

    suspend fun me(token: String?): User?{
        val result = ApiClient.me("Bearer $token")
        Log.d("result", result.toString())
        return result
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

}