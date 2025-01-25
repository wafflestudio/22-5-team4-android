package com.example.interpark.auth

import okhttp3.Interceptor
import okhttp3.Response

class refreshInterceptor(private val refreshTokenProvider: () -> String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val token = refreshTokenProvider()

        return if (token != null) {
            val newRequest = request.newBuilder()
                .addHeader("Cookie", "refreshToken=${token}")
                .build()
            chain.proceed(newRequest)
        } else {
            chain.proceed(request)
        }
    }
}

val refreshTokenProvider: () -> String? = {
    AuthManager.getRefreshToken()
}

