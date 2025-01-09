package com.example.interpark.data.API

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.interpark.data.MoshiDateDeserializer
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(MoshiDateDeserializer())
    .build()

@RequiresApi(Build.VERSION_CODES.O)
object RetrofitInstance {
    private const val BASE_URL_DEV = "http://54.180.136.166:3000/"
    private val clientDev = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("accept", "application/json")
                .build()
            chain.proceed(request)
        }
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val api: ApiClientDev by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_DEV)
            .client(clientDev)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiClientDev::class.java)
    }

    private const val BASE_URL_SERVER = "http://localhost:8080/api/v1/"
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
        .build()

}