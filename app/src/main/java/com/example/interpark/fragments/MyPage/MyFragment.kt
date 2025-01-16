package com.example.interpark.fragments.MyPage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
        val nickNamePrompt = view.findViewById<TextView>(R.id.logged_in_prompt)
        val footerView = view.findViewById<LinearLayout>(R.id.footer)
        val logOutTextView = view.findViewById<TextView>(R.id.btn_logout)
        // "로그인 해주세요" 전체 클릭 이벤트 설정
        loginPromptLayout.setOnClickListener {
            val navController = requireActivity().findNavController(R.id.myNavHost)
            val action = MyFragmentDirections
                .actionMyFragmentToLoginFragment()
            navController.navigate(action)
        }

        logOutTextView.setOnClickListener {
            myPageViewModel.logout()
        }

        myPageViewModel.user.observe(viewLifecycleOwner){ user ->
            Log.d("user-value: ", user.toString())
            if(user != null){
                nickNamePrompt.text = "반갑습니다 ${user.nickname}님"
            }
        }

        myPageViewModel.isLoggedIn.observe(viewLifecycleOwner){isLoggedIn ->
            if(isLoggedIn){
                nickNamePrompt.visibility = View.VISIBLE
                loginPromptLayout.visibility = View.GONE
                footerView.visibility = View.VISIBLE
            }
            else{
                nickNamePrompt.visibility = View.GONE
                loginPromptLayout.visibility = View.VISIBLE
                footerView.visibility = View.GONE
            }
        }

//        view.findViewById<TextView>(R.id.account).setOnClickListener {
//
//
//            val action = MyFragmentDirections.actionMyFragmentToMyFragmentAccount()
//            findNavController().navigate(action)
//        }

    }
}