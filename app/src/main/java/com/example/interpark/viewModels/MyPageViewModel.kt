package com.example.interpark.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interpark.auth.AuthManager
import com.example.interpark.auth.SocialAuthManager
import com.example.interpark.data.API.moshi
import com.example.interpark.data.PerformanceRepository
import com.example.interpark.data.types.SignInResponse
import com.example.interpark.data.types.SignUpResponse
import com.example.interpark.data.types.SocialLinkResult
import com.example.interpark.data.types.SocialLoginError
import com.example.interpark.data.types.SocialLoginResponse
import com.example.interpark.data.types.SocialLoginResult
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

    private val _signUpFailed = MutableLiveData<Boolean>(false)
    val signUpFailed: LiveData<Boolean> get() = _signUpFailed

    private val _signUpSuccess = MutableLiveData<Boolean>(false)
    val signUpSuccess: LiveData<Boolean> get() = _signUpSuccess

    private val _signInFailed = MutableLiveData<Boolean>(false)
    val signInFailed: LiveData<Boolean> get() = _signInFailed

    fun signup(username: String, password: String, nickname: String, phoneNumber: String, email: String, role: String) {
        viewModelScope.launch {
            val result: SignUpResponse? = withContext(Dispatchers.IO){
                repository.signUp(username, password, nickname, phoneNumber, email, role)
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

    fun refresh_token(){
        viewModelScope.launch {
            repository.refresh_token()
        }
    }

    private val _linkDialog = MutableLiveData(false)
    val linkDialog: LiveData<Boolean> get() = _linkDialog

    fun socialLogin(provider: String, code: String){
        viewModelScope.launch {
            val result = repository.socialLogin(provider, code)
            Log.d("", result.toString())
            when(result?.code()) {
                200 -> {
                    val adapter = moshi.adapter(SocialLoginResponse::class.java)
                    val body = adapter.fromJson(result.body()?.string()?:"")
                    AuthManager.login(appContext, body?.user?.nickname!!, result.headers()["Set-Cookie"] ?: "" , body.accessToken!!, body.user)
                    isLoggedIn.value = AuthManager.isLoggedIn()
                    userName.value = AuthManager.getUserId()
                }
                404 -> {
                    val errorBody = result.errorBody()?.string()
                    val adapter = moshi.adapter(SocialLoginError::class.java)
                    _linkDialog.value = true

                    SocialAuthManager.provider = SocialLoginResult.Error(adapter.fromJson(errorBody)!!).errorData.provider
                    SocialAuthManager.providerId = SocialLoginResult.Error(adapter.fromJson(errorBody)!!).errorData.providerId
                }
                else -> {

                }
            }
        }
    }

    private val _linkError = MutableLiveData<String?>(null)
    val linkError: LiveData<String?> get() = _linkError

    fun socialLink(username: String, password: String){
        viewModelScope.launch {
            val result = repository.socialLink(username, password, SocialAuthManager.provider, SocialAuthManager.providerId, appContext)
            Log.d("result", result?.code().toString())
            Log.d("result", result?.errorBody().toString())
            _linkError.value = when(result?.code()){
                200 -> {
                    val result = repository.signIn(username, password, appContext)
                    isLoggedIn.value = AuthManager.isLoggedIn()
                    userName.value = AuthManager.getUserId()
                    ""
                }
                401 -> "비밀번호가 잘못됐습니다."
                404 -> "존재하지 않는 사용자입니다."
                else -> "서버 오류가 발생했습니다."
            }
        }
    }

}
