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

        val loggedOutStep1Layout = view.findViewById<LinearLayout>(R.id.layout_logged_out_step1)
        val loggedOutStep2Layout = view.findViewById<LinearLayout>(R.id.layout_logged_out_step2)
        val loadingLayout = view.findViewById<LinearLayout>(R.id.layout_loading)
        val loggedInLayout = view.findViewById<LinearLayout>(R.id.layout_logged_in)

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

        // 로그인 안내 화면 버튼 클릭
        view.findViewById<Button>(R.id.btn_go_to_login).setOnClickListener {
            viewModel.moveToLoginInput()
        }

        // 로그인 입력 화면 버튼 클릭
        view.findViewById<Button>(R.id.btn_login).setOnClickListener {
            viewModel.login()
        }

        // 로그아웃 버튼 클릭
        view.findViewById<Button>(R.id.btn_logout).setOnClickListener {
            viewModel.logout()
        }

    }
}