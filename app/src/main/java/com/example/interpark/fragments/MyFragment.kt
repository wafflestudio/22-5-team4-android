package com.example.interpark.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
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

        val loggedOutLayout = view.findViewById<LinearLayout>(R.id.layout_logged_out)
        val loadingLayout = view.findViewById<LinearLayout>(R.id.layout_loading)
        val loggedInLayout = view.findViewById<LinearLayout>(R.id.layout_logged_in)

        // 로그인 상태 변화 관찰
        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            when (state) {
                LoginState.LOGGED_OUT -> {
                    loggedOutLayout.visibility = View.VISIBLE
                    loadingLayout.visibility = View.GONE
                    loggedInLayout.visibility = View.GONE
                }
                LoginState.LOADING -> {
                    loggedOutLayout.visibility = View.GONE
                    loadingLayout.visibility = View.VISIBLE
                    loggedInLayout.visibility = View.GONE
                }
                LoginState.LOGGED_IN -> {
                    loggedOutLayout.visibility = View.GONE
                    loadingLayout.visibility = View.GONE
                    loggedInLayout.visibility = View.VISIBLE
                }
            }
        }

        // 로그인 버튼 클릭 이벤트 처리
        view.findViewById<Button>(R.id.btn_login).setOnClickListener {
            viewModel.loginState.value = LoginState.LOADING

            // 예제: 서버 로그인 시뮬레이션
            Handler(Looper.getMainLooper()).postDelayed({
                viewModel.loginState.value = LoginState.LOGGED_IN
            }, 2000) // 2초 후 로그인 완료로 변경
        }

        view.findViewById<Button>(R.id.btn_logout).setOnClickListener {
            viewModel.loginState.value = LoginState.LOGGED_OUT
        }

    }
}