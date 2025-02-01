package com.example.interpark.fragments.MyPage

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.interpark.BuildConfig
import com.example.interpark.R
import com.example.interpark.databinding.FragmentMyLoginBinding
import com.example.interpark.viewModels.MyPageViewModel
import com.example.interpark.viewModels.MyPageViewModelFactory
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.NaverIdLoginSDK.oauthLoginCallback
import com.navercorp.nid.oauth.OAuthLoginCallback
import java.net.URLEncoder

class LoginFragment : Fragment() {

    private val myPageViewModel: MyPageViewModel by viewModels { MyPageViewModelFactory(requireContext()) }
    private var _binding: FragmentMyLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var webView: WebView
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

        binding.kakaoLogin.setOnClickListener {
            loginWithKakao()
        }

        setNaverOAuth()

        myPageViewModel.linkDialog.observe(viewLifecycleOwner){ show ->
            if(show){
                val builder = AlertDialog.Builder(context)
                builder.setTitle("알림")
                    .setMessage("기존 계정의 아이디/비밀번호를 입력하고 \n소셜 계정을 연동하시겠습니까?")
                    .setPositiveButton("네", { _, _ ->
                        val navController = requireActivity().findNavController(R.id.myNavHost)
                        val action = LoginFragmentDirections.actionLoginFragmentToLinkFragment()
                        navController.navigate(action)
                    })
                    .setNegativeButton("아니오", null)
                    .show()
            }

        }
    }

    private fun loginWithKakao(){
        UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
            if (error != null) {
                Log.e(TAG, "로그인 실패", error)
                Toast.makeText(context, "먼저 카카오톡에 로그인 해 주세요!", Toast.LENGTH_SHORT).show()
            }
            else if (token != null) {
                Log.i(TAG, "로그인 성공 ${token.accessToken}")
                UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                    if(error != null){
                        Log.d(TAG, tokenInfo.toString())
                    }
                    else if (tokenInfo != null) {
                        myPageViewModel.socialLogin("KAKAO", token.accessToken)
                        Log.i(TAG, tokenInfo.appId.toString())
                    }
                }
            }
        }
    }

    private fun setNaverOAuth(){
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                val token = NaverIdLoginSDK.getAccessToken()
                myPageViewModel.socialLogin("NAVER", token?: "")
                Log.d("token", token.toString())
            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(context,"errorCode:$errorCode, errorDesc:$errorDescription",Toast.LENGTH_SHORT).show()
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }
        binding.naverLogin.setOnClickListener {
//            Toast.makeText(context, "현재 지원하지 않는 기능입니다.", Toast.LENGTH_SHORT).show()
            NaverIdLoginSDK.authenticate(requireContext(), oauthLoginCallback)
        }

        binding.googleLogin.setOnClickListener {
            Toast.makeText(context, "현재 지원하지 않는 기능입니다.", Toast.LENGTH_SHORT).show()
        }
//        binding.buttonOAuthLoginImg.setOAuthLogin(oauthLoginCallback)
    }
}