package com.example.interpark.data.types

// 요청에 필요한 데이터 클래스 정의
data class SignUpRequest(
    val username: String,
    val password: String,
    val nickname: String,
    val phoneNumber: String,
    val email: String,
    val role : String
)

data class User(
    val id: String,
    val username: String,
    val nickname: String,
    val phoneNumber: String,
    val email: String
)

data class SignUpResponse(
    val user: User
)

data class SignInRequest(
    val username: String,
    val password: String
)

data class SignInResponse(
    val user: User,
    val accessToken: String
)

data class UserProfile(
    val username: String,
    val nickname: String,
    val phoneNumber: String,
    val email: String
)

