package com.example.interpark.fragments.MyPage

import android.app.AlertDialog
import android.content.ContentValues.TAG
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
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.interpark.BuildConfig
import com.example.interpark.R
import com.example.interpark.databinding.FragmentMyLinkBinding
import com.example.interpark.databinding.FragmentMyLoginBinding
import com.example.interpark.viewModels.MyPageViewModel
import com.example.interpark.viewModels.MyPageViewModelFactory
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.NaverIdLoginSDK.oauthLoginCallback
import com.navercorp.nid.oauth.OAuthLoginCallback
import java.net.URLEncoder

class LinkFragment : Fragment() {

    private val myPageViewModel: MyPageViewModel by viewModels { MyPageViewModelFactory(requireContext()) }
    private var _binding: FragmentMyLinkBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyLinkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener{
            myPageViewModel.socialLink(binding.etUsername.text.toString(), binding.etPassword.text.toString())
        }

        binding.textSignup.setOnClickListener {
            val navController = requireActivity().findNavController(R.id.myNavHost)
            val action = LinkFragmentDirections.actionLinkFragmentToSignUpFragment()
            navController.navigate(action)
        }

        myPageViewModel.linkError.observe(viewLifecycleOwner){ errorText ->
            Log.d("errorText", errorText.toString())
            if(errorText == null) {
                binding.errorText.visibility = View.GONE
            }
            else{
                binding.errorText.visibility = View.VISIBLE
                binding.errorText.setText(errorText)
            }
        }

//        myPageViewModel.signInFailed.observe(viewLifecycleOwner){ signInFailed ->
//            binding.errorText.visibility = when(signInFailed){
//                true -> View.VISIBLE
//                false -> View.GONE
//            }
//        }

        myPageViewModel.isLoggedIn.observe(viewLifecycleOwner){ isLoggedIn ->
            if(isLoggedIn){
                val navController = requireActivity().findNavController(R.id.myNavHost)
                val action = LinkFragmentDirections.actionLinkFragmentToMyFragment()
                navController.navigate(action)
            }
        }


        myPageViewModel.linkDialog.observe(viewLifecycleOwner){ show ->
            if(show){
                val builder = AlertDialog.Builder(context)
                builder.setTitle("알림")
                    .setMessage("소셜 계정을 연동하시겠습니까?")
                    .setPositiveButton("네", null)
                    .setNegativeButton("아니오", null)
                    .show()
            }

        }
    }


}