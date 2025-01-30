package com.example.interpark.data

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.interpark.auth.AuthManager
import com.example.interpark.data.API.ApiClient
import com.example.interpark.data.API.moshi
import com.example.interpark.data.types.AdminPerformanceEventRequest
import com.example.interpark.data.types.AdminPerformanceEventResponse
import com.example.interpark.data.types.AdminPerformanceHallRequest
import com.example.interpark.data.types.AdminPerformanceHallResponse
import com.example.interpark.data.types.AdminPerformanceRequest
import com.example.interpark.data.types.AdminPerformanceResponse
import com.example.interpark.data.types.Performance
import com.example.interpark.data.types.PerformanceEvent
import com.example.interpark.data.types.Review
import com.example.interpark.data.types.SignInRequest
import com.example.interpark.data.types.SignInResponse
import com.example.interpark.data.types.SignUpRequest
import com.example.interpark.data.types.SignUpResponse
import com.example.interpark.data.types.SocialLinkRequest
import com.example.interpark.data.types.SocialLinkResult
import com.example.interpark.data.types.SocialLoginError
import com.example.interpark.data.types.SocialLoginResponse
import com.example.interpark.data.types.SocialLoginResult
import com.example.interpark.data.types.User
import com.google.protobuf.Api
import okhttp3.ResponseBody
import retrofit2.Response

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

    suspend fun signUp(username: String, password: String, nickname: String, phoneNumber: String, email: String, role: String): SignUpResponse? {
        val result = ApiClient.signup(SignUpRequest(username, password, nickname, phoneNumber, email, role))
        Log.d("SignUp", result.body().toString())
        return result.body()
    }

    suspend fun signIn(username: String, password: String, context: Context): SignInResponse?{
        val result = ApiClient.signin(SignInRequest(username, password))
        if(result.code().toString().first() == '2'){
            AuthManager.login(context, result.body()!!.user.nickname, result.headers()["Set-Cookie"] ?: "" , result.body()!!.accessToken, result.body()!!.user)
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

    suspend fun adminCreatePerformance(token: String?,request: AdminPerformanceRequest): AdminPerformanceResponse {
        return ApiClient.createPerformance("Bearer $token", request)
    }

    suspend fun adminCreatePerformanceHall(token: String?,request: AdminPerformanceHallRequest): AdminPerformanceHallResponse {
        return ApiClient.createPerformanceHall("Bearer $token",request)
    }

    suspend fun adminCreatePerformanceEvent(token: String?,request: AdminPerformanceEventRequest): AdminPerformanceEventResponse {
        return ApiClient.createPerformanceEvent("Bearer $token",request)
    }

    suspend fun socialLogin(provider: String, code: String): Response<ResponseBody>? {
        try {
            val result = ApiClient.socialLogin(provider, code)
            return result

        }
        catch(e: Exception){
            return null
        }
    }

    suspend fun socialLink(username: String, password: String, provider: String?, providerId: String?, context: Context): Response<ResponseBody> {
        val result = ApiClient.socialLink(SocialLinkRequest(username, password, provider ?: "", providerId ?: ""))
//        if(result.code() == 200){
//            AuthManager.login(context, result.body()!!.user.nickname, result.headers()["Set-Cookie"] ?: "" , result.body()!!.accessToken, result.body()!!.user)
//        }
        return result
    }

}