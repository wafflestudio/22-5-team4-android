package com.example.interpark.auth

import android.content.Context
import android.util.Log
import com.example.interpark.data.types.User

object AuthManager {

    private const val PREF_NAME = "user_session"
    private const val KEY_USER_NICKNAME = "nickName"
    private const val KEY_AUTH_TOKEN = "authToken"
    private const val KEY_REFRESH_TOKEN = "refreshToken"
    private const val KEY_USER_ID = "ID"
    private const val KEY_USER_USERNAME = "username"
    private const val KEY_USER_PHONE = "phone"
    private const val KEY_USER_EMAIL = "email"


    private var isLoggedIn: Boolean = false
    private var refreshToken: String? = null
    private var authToken: String? = null
    private var nickName: String? = null
    private var user: User? = null

    fun initialize(context: Context) {
//        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
//        nickName = sharedPreferences.getString(KEY_USER_NICKNAME, null)
//        val phone = sharedPreferences.getString(KEY_USER_PHONE, null)
//        val id = sharedPreferences.getString(KEY_USER_ID, null)
//        val username = sharedPreferences.getString(KEY_USER_USERNAME, null)
//        val email = sharedPreferences.getString(KEY_USER_EMAIL, null)
//        if(id != null && username != null && nickName != null && phone != null && email != null) {
//            user = User(id, username, nickName!!, phone, email)
//        }
//        authToken = sharedPreferences.getString(KEY_AUTH_TOKEN, null)
//        isLoggedIn = !nickName.isNullOrEmpty() && !authToken.isNullOrEmpty()
    }

    fun login(context: Context, userId: String, refreshToken: String, authToken: String, user: User) {
        val refreshTokenSplit = refreshToken.split(';')[0].split('=')[1]
        Log.d("refreshToken", refreshTokenSplit.toString())
        this.nickName = userId
        this.refreshToken = refreshTokenSplit
        this.authToken = authToken
        this.isLoggedIn = true
        this.user = user
        saveSession(context, userId, refreshToken, authToken)
    }

    fun logout(context: Context) {
        this.nickName = null
        this.authToken = null
        this.isLoggedIn = false
        this.refreshToken = null
        clearSession(context)
    }

    fun refreshToken(context: Context, token: String) {
        this.authToken = token
    }

    fun isLoggedIn(): Boolean = isLoggedIn

    fun getUserId(): String? = nickName

    fun getAuthToken(): String? = authToken

    fun getUser(): User? = user

    fun getRefreshToken(): String? = refreshToken

    private fun saveSession(context: Context, userId: String, refreshToken: String, authToken: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(KEY_USER_ID, userId)
            putString(KEY_REFRESH_TOKEN, refreshToken)
            putString(KEY_AUTH_TOKEN, authToken)
            putString(KEY_USER_EMAIL, user?.email)
            putString(KEY_USER_PHONE, user?.phoneNumber)
            putString(KEY_USER_NICKNAME, user?.nickname)
            putString(KEY_USER_USERNAME, user?.username)
            apply()
        }
    }

    private fun clearSession(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }
}
