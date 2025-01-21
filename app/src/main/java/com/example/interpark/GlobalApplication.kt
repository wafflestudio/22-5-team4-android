package com.example.interpark


import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 카카오 SDK 초기화
        KakaoSdk.init(this, "YOUR_NATIVE_APP_KEY") // 네이티브 앱 키 입력
    }
}
