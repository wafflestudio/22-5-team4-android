package com.example.interpark.viewModels

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class LoginState {
    LOGGED_OUT_STEP1, LOGGED_OUT_STEP2 , LOADING, LOGGED_IN
}

class MyPageViewModel : ViewModel() {
    val loginState = MutableLiveData<LoginState>().apply {
        value = LoginState.LOGGED_OUT_STEP1
    }

    fun moveToLoginInput(){
        loginState.value = LoginState.LOGGED_OUT_STEP2
    }

    fun login() {
        loginState.value = LoginState.LOADING
        Handler(Looper.getMainLooper()).postDelayed({
            loginState.value = LoginState.LOGGED_IN
        }, 2000)
    }

    fun logout(){
        loginState.value = LoginState.LOGGED_OUT_STEP1
    }
}
