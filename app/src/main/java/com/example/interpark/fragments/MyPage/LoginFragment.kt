package com.example.interpark.fragments.MyPage

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.interpark.R
import com.example.interpark.databinding.FragmentMyLoginBinding
import com.example.interpark.viewModels.MyPageViewModel
import com.example.interpark.viewModels.MyPageViewModelFactory
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient


class LoginFragment : Fragment() {

    private val myPageViewModel: MyPageViewModel by viewModels { MyPageViewModelFactory(requireContext()) }
    private var _binding: FragmentMyLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener{
            myPageViewModel.login(binding.etUsername.text.toString(), binding.etPassword.text.toString())
        }

        binding.textSignup.setOnClickListener {
            val navController = requireActivity().findNavController(R.id.myNavHost)
            val action = LoginFragmentDirections
                .actionLoginFragmentToSignUpFragment()
            navController.navigate(action)
        }

        binding.kakaoLogin.setOnClickListener {
            Log.d("로그인 버튼 클릭: ", "clicked")
            loginWithKakao()
        }



        binding.logoInterpark.setOnClickListener {
            val navController = requireActivity().findNavController(R.id.myNavHost)
            val action = LoginFragmentDirections.actionLoginFragmentToMyFragment()
            navController.navigate(action)
        }

        myPageViewModel.signInFailed.observe(viewLifecycleOwner){ signInFailed ->
            binding.errorText.visibility = when(signInFailed){
                true -> View.VISIBLE
                false -> View.GONE
            }
        }

        myPageViewModel.isLoggedIn.observe(viewLifecycleOwner){ isLoggedIn ->
            if(isLoggedIn){
                val navController = requireActivity().findNavController(R.id.myNavHost)
                val action = LoginFragmentDirections.actionLoginFragmentToMyFragment()
                navController.navigate(action)
            }
        }



    }

    private fun loginWithKakao() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "로그인 실패", error)
            } else if (token != null) {
                Log.i(TAG, "로그인 성공: ${token.accessToken}")
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoTalk(requireContext(), callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
        }
    }
}