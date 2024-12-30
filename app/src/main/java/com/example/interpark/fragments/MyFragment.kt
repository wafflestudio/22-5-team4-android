package com.example.interpark.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.interpark.R
import com.example.interpark.viewModels.LoginState
import com.example.interpark.viewModels.MyPageViewModel


class MyFragment : Fragment() {

    private lateinit var viewModel: MyPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MyPageViewModel::class.java)

        val loggedOutStep1Layout = view.findViewById<ScrollView>(R.id.layout_logged_out_step1) // 타입 수정
        val loggedOutStep2Layout = view.findViewById<ScrollView>(R.id.layout_logged_out_step2)
        val loadingLayout = view.findViewById<LinearLayout>(R.id.layout_loading)
        val loggedInLayout = view.findViewById<ScrollView>(R.id.layout_logged_in)

        // 상태 변화 관찰
        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            when (state) {
                LoginState.LOGGED_OUT_STEP1 -> {
                    loggedOutStep1Layout.visibility = View.VISIBLE
                    loggedOutStep2Layout.visibility = View.GONE
                    loadingLayout.visibility = View.GONE
                    loggedInLayout.visibility = View.GONE
                }
                LoginState.LOGGED_OUT_STEP2 -> {
                    loggedOutStep1Layout.visibility = View.GONE
                    loggedOutStep2Layout.visibility = View.VISIBLE
                    loadingLayout.visibility = View.GONE
                    loggedInLayout.visibility = View.GONE
                }
                LoginState.LOADING -> {
                    loggedOutStep1Layout.visibility = View.GONE
                    loggedOutStep2Layout.visibility = View.GONE
                    loadingLayout.visibility = View.VISIBLE
                    loggedInLayout.visibility = View.GONE
                }
                LoginState.LOGGED_IN -> {
                    loggedOutStep1Layout.visibility = View.GONE
                    loggedOutStep2Layout.visibility = View.GONE
                    loadingLayout.visibility = View.GONE
                    loggedInLayout.visibility = View.VISIBLE
                }
            }
        }

        // ScrollView 안의 LinearLayout 가져오기
        val loginPromptLayout = view.findViewById<TextView>(R.id.layout_login_prompt)

        // "로그인 해주세요" 전체 클릭 이벤트 설정
        loginPromptLayout.setOnClickListener {
            viewModel.loginState.value = LoginState.LOGGED_OUT_STEP2 // 로그인 입력 화면으로 전환
        }

        // 로그인 입력 화면 버튼 클릭
        view.findViewById<Button>(R.id.btn_login).setOnClickListener {
            viewModel.login()
        }

        // 로그아웃 버튼 클릭
        view.findViewById<TextView>(R.id.btn_logout).setOnClickListener {
            viewModel.logout()
        }

    }
}