package com.example.interpark.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class LoginState {
    LOGGED_OUT, LOADING, LOGGED_IN
}

class MyPageViewModel : ViewModel() {
    val loginState = MutableLiveData<LoginState>().apply {
        value = LoginState.LOGGED_OUT
    }
}
