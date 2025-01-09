package com.example.interpark.viewModels

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interpark.data.PerformanceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MyPageViewModel(private val repository: PerformanceRepository) : ViewModel() {
//    enum class LoginState {
//        LOGGED_OUT_STEP1, LOGGED_OUT_STEP2 , LOADING, LOGGED_IN
//    }
//    val loginState = MutableLiveData<LoginState>().apply {
//        value = LoginState.LOGGED_OUT_STEP1
//    }

    fun signup(username: String, password: String, nickname: String, phoneNumber: String, email: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                repository.signUp(username, password, nickname, phoneNumber, email)
            }
            Log.d("result", result.toString())
        }
    }

//    fun moveToLoginInput(){
//        loginState.value = LoginState.LOGGED_OUT_STEP2
//    }
//
    fun login(username: String, password: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                repository.signIn(username, password)
            }
            Log.d("result", result.toString())
        }
    }
//
//    fun logout(){
//        loginState.value = LoginState.LOGGED_OUT_STEP1
//    }
}
