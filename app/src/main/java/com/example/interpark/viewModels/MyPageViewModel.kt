package com.example.interpark.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interpark.auth.AuthManager
import com.example.interpark.data.PerformanceRepository
import com.example.interpark.data.SharedPreferences.deleteAccessToken
import com.example.interpark.data.SharedPreferences.getAccessToken
import com.google.rpc.context.AttributeContext.Auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyPageViewModel(private val repository: PerformanceRepository, private val appContext: Context) : ViewModel() {

    val isLoggedIn = MutableLiveData<Boolean>().apply{
        value = AuthManager.isLoggedIn()
    }

    val userName = MutableLiveData<String?>().apply {
        value = AuthManager.getUserId()
    }

    fun signup(username: String, password: String, nickname: String, phoneNumber: String, email: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                repository.signUp(username, password, nickname, phoneNumber, email)
            }
            if(result != null){
                isLoggedIn.value = true
            }
        }
    }
    fun login(username: String, password: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                repository.signIn(username, password, appContext)
            }
            isLoggedIn.value = AuthManager.isLoggedIn()
            userName.value = AuthManager.getUserId()
        }
    }

    fun logout(){
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                repository.signOut(appContext)
            }
            isLoggedIn.value = AuthManager.isLoggedIn()
            userName.value = AuthManager.getUserId()
        }
    }


}
