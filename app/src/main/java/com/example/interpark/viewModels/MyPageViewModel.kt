package com.example.interpark.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interpark.auth.AuthManager
import com.example.interpark.data.PerformanceRepository
import com.example.interpark.data.types.SignInResponse
import com.example.interpark.data.types.SignUpResponse
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

    private val _signUpFailed = MutableLiveData<Boolean>(false)
    val signUpFailed: LiveData<Boolean> get() = _signUpFailed

    private val _signUpSuccess = MutableLiveData<Boolean>(false)
    val signUpSuccess: LiveData<Boolean> get() = _signUpSuccess

    private val _signInFailed = MutableLiveData<Boolean>(false)
    val signInFailed: LiveData<Boolean> get() = _signInFailed

    fun signup(username: String, password: String, nickname: String, phoneNumber: String, email: String) {
        viewModelScope.launch {
            val result: SignUpResponse? = withContext(Dispatchers.IO){
                repository.signUp(username, password, nickname, phoneNumber, email)
            }
            if(result == null) _signUpFailed.value = true
            else _signUpSuccess.value = true
        }
    }

    fun login(username: String, password: String){
        viewModelScope.launch {
            var result: SignInResponse?
            withContext(Dispatchers.IO){
                result = repository.signIn(username, password, appContext)
            }
            if(result == null) _signInFailed.value = true
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

    private val _usernameLength = MutableLiveData<Boolean>(true)
    val usernameLength: LiveData<Boolean> get() = _usernameLength
    fun usernameLengthCheck(username: String) {
        _usernameLength.value = username.length in 6..20
    }


    private val _passwordLength = MutableLiveData<Boolean>(true)
    val passwordLength: LiveData<Boolean> get() = _passwordLength
    fun passwordLengthCheck(password: String){
        _passwordLength.value = password.length in 8..12

    }

    private val _passwordSame = MutableLiveData<Boolean>(true)
    val passwordSame: LiveData<Boolean> get() = _passwordSame
    fun passwordSameCheck(password: String, passwordCheck: String){
        _passwordSame.value = password == passwordCheck
    }

}
