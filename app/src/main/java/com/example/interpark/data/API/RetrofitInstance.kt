package com.example.interpark.data.API

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.interpark.auth.AuthInterceptor
import com.example.interpark.auth.tokenProvider
import com.example.interpark.data.MoshiDateDeserializer
import com.example.interpark.data.MoshiDateTimeDeserializer
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
    .add(MoshiDateTimeDeserializer())
    .build()


@RequiresApi(Build.VERSION_CODES.O)
object RetrofitInstance {

    private const val BASE_URL_DEV = "http://192.168.1.51:3000/"
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

    // change


    private const val BASE_URL_SERVER = "http://172.30.1.6:80/"


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
        //.cookieJar(SimpleCookieJar())
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