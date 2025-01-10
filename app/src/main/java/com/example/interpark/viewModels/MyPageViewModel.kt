package com.example.interpark.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interpark.data.PerformanceRepository
import com.example.interpark.data.SharedPreferences.deleteAccessToken
import com.example.interpark.data.SharedPreferences.getAccessToken
import com.example.interpark.data.SharedPreferences.saveAccessToken
import com.example.interpark.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyPageViewModel(private val repository: PerformanceRepository, private val appContext: Context) : ViewModel() {

    val isLoggedIn = MutableLiveData<Boolean>().apply{
        value = getAccessToken(appContext) != null
    }

    val user = MutableLiveData<User?>()

    fun updateLoggedIn(){
        isLoggedIn.value = getAccessToken(appContext) != null
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
                repository.signIn(username, password)
            }
            if(result != null) {
                isLoggedIn.value = true
                result.accessToken.let { saveAccessToken(appContext, it) }
            }

            val token = getAccessToken(appContext)
            val setUser = withContext(Dispatchers.IO){
                when(isLoggedIn.value){
                    true -> repository.me(token)
                    else -> null
                }
            }
            user.value = setUser
        }
    }

    fun logout(){
        viewModelScope.launch {
            val token = getAccessToken(appContext)
            Log.d("accesstoken:", token.toString())
//            val result = withContext(Dispatchers.IO){
//                repository.signOut(token)
//            }
            deleteAccessToken(appContext)
            updateLoggedIn()
        }
    }

    fun me(){
        viewModelScope.launch {
            val token = getAccessToken(appContext)
            val result = withContext(Dispatchers.IO){
                when(isLoggedIn.value){
                    true -> repository.me(token)
                    else -> null
                }
            }
            user.value = result
        }
    }
}
