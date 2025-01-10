package com.example.interpark.data.SharedPreferences

import android.content.Context

fun saveAccessToken(context: Context, token: String) {
    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("accessToken", token)
    editor.apply()
}

fun getAccessToken(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString("accessToken", null)
}

// SharedPreferences에서 accessToken을 삭제하는 함수
fun deleteAccessToken(context: Context) {
    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.remove("accessToken")  // "accessToken" 키를 삭제
    editor.apply()  // 변경 사항을 저장
}
