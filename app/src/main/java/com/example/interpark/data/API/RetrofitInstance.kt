package com.example.interpark.data.API

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.interpark.auth.AuthInterceptor
import com.example.interpark.auth.refreshInterceptor
import com.example.interpark.auth.refreshTokenProvider
import com.example.interpark.auth.tokenProvider
import com.example.interpark.data.MoshiDateDeserializer
import com.example.interpark.data.MoshiDateTimeDeserializer
import com.google.api.Http
//import com.example.interpark.data.SharedPreferences.SimpleCookieJar
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy


val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(MoshiDateDeserializer())
    .add(MoshiDateTimeDeserializer())
    .build()

val cookieManager = CookieManager().apply {
    setCookiePolicy(CookiePolicy.ACCEPT_ALL) // 모든 쿠키 허용
}

val cookieStore = mutableMapOf<String, String>()

@RequiresApi(Build.VERSION_CODES.O)
object RetrofitInstance {

    private const val BASE_URL_SERVER = "http://172.20.10.4:80/"



    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("accept", "application/json")
                .build()
            chain.proceed(request)
        }
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(AuthInterceptor(tokenProvider))
//        .addInterceptor(refreshInterceptor(refreshTokenProvider))
        .cookieJar(CookieJar())
        .build()

    val api1: ApiClient by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_SERVER)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiClient::class.java)
    }
}