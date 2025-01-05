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
    private const val BASE_URL = "http://54.180.136.166:3000/"
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

    val api: ApiClient by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiClient::class.java)
    }
}