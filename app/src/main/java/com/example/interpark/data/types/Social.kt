package com.example.interpark.data.types

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// 성공 응답 데이터 클래스
@JsonClass(generateAdapter = true)
data class SocialLoginResponse(
    val user: User?,
    val accessToken: String?,
    val provider: String,
    val providerId: String
)

@JsonClass(generateAdapter = true)
data class SocialLoginError(
    val error: String,
    val errorCode: Int,
    val provider: String,
    val providerId: String
)

@JsonClass(generateAdapter = true, generator = "sealed:type")
sealed class SocialLoginResult {
    @JsonClass(generateAdapter = true)
    data class Success(
        val data: SocialLoginResponse) : SocialLoginResult()

    @JsonClass(generateAdapter = true)
    data class Error(
        val errorData: SocialLoginError) : SocialLoginResult()
}


data class SocialLinkRequest(
    val username: String,
    val password: String,
    val provider: String,
    val providerId: String
)

data class SocialLinkResult(
    val error: String,
    val errorCode: Int
)