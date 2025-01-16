package com.example.interpark.auth

import android.content.Context

object AuthManager {

    private const val PREF_NAME = "user_session"
    private const val KEY_USER_ID = "userId"
    private const val KEY_AUTH_TOKEN = "authToken"

    private var isLoggedIn: Boolean = false
    private var authToken: String? = null
    private var userId: String? = null

    fun initialize(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        userId = sharedPreferences.getString(KEY_USER_ID, null)
        authToken = sharedPreferences.getString(KEY_AUTH_TOKEN, null)
        isLoggedIn = !userId.isNullOrEmpty() && !authToken.isNullOrEmpty()
    }

    fun login(context: Context, userId: String, authToken: String) {
        this.userId = userId
        this.authToken = authToken
        this.isLoggedIn = true
        saveSession(context, userId, authToken)
    }

    fun logout(context: Context) {
        this.userId = null
        this.authToken = null
        this.isLoggedIn = false
        clearSession(context)
    }

    fun isLoggedIn(): Boolean = isLoggedIn

    fun getUserId(): String? = userId

    fun getAuthToken(): String? = authToken

    private fun saveSession(context: Context, userId: String, authToken: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(KEY_USER_ID, userId)
            putString(KEY_AUTH_TOKEN, authToken)
            apply()
        }
    }

    private fun clearSession(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }
}
