package com.example.interpark

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import com.example.interpark.adapters.ViewPagers.ViewPagerAdapter
import com.example.interpark.auth.AuthManager
import com.example.interpark.auth.scheduleTokenRefresh
import com.example.interpark.fragments.MyPage.LoginFragment

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AuthManager.initialize(applicationContext)
        scheduleTokenRefresh(this)
        setContentView(R.layout.activity_main)


        val viewPager: ViewPager2 = findViewById(R.id.viewPager)

        // ViewPager2 어댑터 설정
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        // 버튼과 ViewPager2 연결
        findViewById<ImageButton>(R.id.btn_home).setOnClickListener {
            viewPager.currentItem = 0 // 첫 번째 페이지(Home)
        }
        findViewById<ImageButton>(R.id.btn_category).setOnClickListener {
            viewPager.currentItem = 1 // 두 번째 페이지(Category)
        }
        findViewById<ImageButton>(R.id.btn_search).setOnClickListener {
            viewPager.currentItem = 2 // 세 번째 페이지(Search)
        }
        findViewById<ImageButton>(R.id.btn_my).setOnClickListener {
            viewPager.currentItem = 3 // 네 번째 페이지(My)
        }
        handleOAuthRedirect(intent)
    }

    override fun onNewIntent(intent: Intent) {
        Log.d("intent", intent.toString())
        super.onNewIntent(intent)
        handleOAuthRedirect(intent)
    }

    private fun handleOAuthRedirect(intent: Intent?) {
        intent?.data?.let { uri ->
            val code = uri.getQueryParameter("code")  // OAuth 코드 추출
            if (code != null) {
                Log.d("OAuth", "카카오 로그인 성공, code: $code")
                // 여기서 서버로 OAuth 코드 전송 후 처리하면 됨
            } else {
                Log.e("OAuth", "로그인 실패 또는 취소됨")
            }
        }

    }
}
