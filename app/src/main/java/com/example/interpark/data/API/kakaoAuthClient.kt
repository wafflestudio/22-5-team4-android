package com.example.interpark.data.API

import android.content.Context
import com.kakao.sdk.user.UserApiClient

class KakaoAuthClient constructor() {
    fun loginWithKakaoTalk(
        context: Context,
        onSuccess: (String) -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                onFailure(error)
            } else if (token != null) {
                onSuccess(token.accessToken)
            }
        }
    }
}