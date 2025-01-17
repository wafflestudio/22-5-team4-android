package com.example.interpark.data.API

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.interpark.data.MoshiDateDeserializer
//import com.example.interpark.data.SharedPreferences.SimpleCookieJar
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.CookieJar
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.CookieManager


val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(MoshiDateDeserializer())
    .build()


@RequiresApi(Build.VERSION_CODES.O)
object RetrofitInstance {

    private const val BASE_URL_DEV = "http://192.168.219.104:3000/"
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
    // change

    private const val BASE_URL_SERVER = "http://192.168.1.31:8080/"
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
//        .cookieJar(SimpleCookieJar())
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