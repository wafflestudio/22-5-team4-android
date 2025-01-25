package com.example.interpark.auth

import android.content.Context
import android.util.Log
import com.example.interpark.data.types.User

object AuthManager {

    private const val PREF_NAME = "user_session"
    private const val KEY_USER_ID = "userId"
    private const val KEY_AUTH_TOKEN = "authToken"
    private const val KEY_REFRESH_TOKEN = "refreshToken"

    private var isLoggedIn: Boolean = false
    private var refreshToken: String? = null
    private var authToken: String? = null
    private var userId: String? = null
    private var user: User? = null

    fun initialize(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        userId = sharedPreferences.getString(KEY_USER_ID, null)
        authToken = sharedPreferences.getString(KEY_AUTH_TOKEN, null)
        isLoggedIn = !userId.isNullOrEmpty() && !authToken.isNullOrEmpty()
    }

    fun login(context: Context, userId: String, refreshToken: String, authToken: String, user: User) {
        val refreshTokenSplit = refreshToken.split(';')[0].split('=')[1]
        Log.d("refreshToken", refreshTokenSplit.toString())
        this.userId = userId
        this.refreshToken = refreshTokenSplit
        this.authToken = authToken
        this.isLoggedIn = true
        this.user = user
        saveSession(context, userId, refreshToken, authToken)
    }

    fun logout(context: Context) {
        this.userId = null
        this.authToken = null
        this.isLoggedIn = false
        this.refreshToken = null
        clearSession(context)
    }

    fun refreshToken(context: Context, token: String) {
        this.authToken = token
    }

    fun isLoggedIn(): Boolean = isLoggedIn

    fun getUserId(): String? = userId

    fun getAuthToken(): String? = authToken

    fun getUser(): User? = user

    fun getRefreshToken(): String? = refreshToken

    private fun saveSession(context: Context, userId: String, refreshToken: String, authToken: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(KEY_USER_ID, userId)
            putString(KEY_REFRESH_TOKEN, refreshToken)
            putString(KEY_AUTH_TOKEN, authToken)
            apply()
        }
    }

    private fun clearSession(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }
}
