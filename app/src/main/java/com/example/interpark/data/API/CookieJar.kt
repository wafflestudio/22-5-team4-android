package com.example.interpark.data.API

import android.util.Log
import okhttp3.Cookie
import okhttp3.HttpUrl

class CookieJar : okhttp3.CookieJar {
    private var cookieStore = mutableMapOf<String, List<Cookie>>()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        Log.d("Saved cookie:",cookies.first().toString())
        cookieStore["refresh_token"] = cookies
//        [url.host] = cookies
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        Log.d("Loaded cookies:", cookieStore.toString())
        return cookieStore["refresh_token"] ?: emptyList()
//        return cookieStore[url.host] ?: emptyList()
    }
}