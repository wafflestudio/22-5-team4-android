package com.example.interpark.data

import android.util.Log
import com.example.interpark.data.API.ApiClient
import com.example.interpark.data.API.ApiClientDev
import com.example.interpark.data.API.ResponseWrapper

class PerformanceRepository(private val ApiClientDev: ApiClientDev, private val ApiClient: ApiClient) {
    suspend fun getAllPerformances(): List<Performance> {
        val result = ApiClientDev.getAllPerformances()
        return result.result
    }

    suspend fun fetchPerformances(category: String?, title: String?): List<Performance> {
        val result = ApiClientDev.getPerformances(category, title)
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

}