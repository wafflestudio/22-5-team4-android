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
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.interpark.R
import com.example.interpark.viewModels.MyPageViewModel
import com.example.interpark.viewModels.MyPageViewModelFactory


class MyFragment : Fragment() {

    private val myPageViewModel: MyPageViewModel by viewModels { MyPageViewModelFactory(requireContext()) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // ScrollView 안의 LinearLayout 가져오기
        val loginPromptLayout = view.findViewById<TextView>(R.id.layout_login_prompt)

        // "로그인 해주세요" 전체 클릭 이벤트 설정
        loginPromptLayout.setOnClickListener {
            val navController = requireActivity().findNavController(R.id.myNavHost)
            val action = MyFragmentDirections
                .actionMyFragmentToLoginFragment()
            navController.navigate(action)
        }

    }
}