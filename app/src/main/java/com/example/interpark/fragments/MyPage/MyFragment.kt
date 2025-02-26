package com.example.interpark.fragments.MyPage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.interpark.R
import com.example.interpark.auth.AuthManager
import com.example.interpark.viewModels.MyPageViewModel
import com.example.interpark.viewModels.MyPageViewModelFactory
import com.example.interpark.viewModels.PerformanceDetailViewModel
import com.example.interpark.viewModels.PerformanceDetailViewModelFactory
import com.example.interpark.viewModels.SeatSelectionViewModel
import com.example.interpark.viewModels.SeatSelectionViewModelFactory
import org.w3c.dom.Text


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
        val reserveHistory = view.findViewById<TextView>(R.id.reserve_history)
        val authPage = view.findViewById<TextView>(R.id.btn_inquiry)
        val customerSupport = view.findViewById<TextView>(R.id.btn_customer_support)

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

        reserveHistory.setOnClickListener{
            if(AuthManager.getUser() == null){
                Toast.makeText(context, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val navController = requireActivity().findNavController(R.id.myNavHost)
            val action = MyFragmentDirections
                .actionMyFragmentToReservedSeatListFragment()
            navController.navigate(action)
        }

        authPage.setOnClickListener{
            val navController = requireActivity().findNavController(R.id.myNavHost)
            val action = MyFragmentDirections
                .actionMyFragmentToAuthFragment()
            navController.navigate(action)
        }

        customerSupport.setOnClickListener{
            myPageViewModel.refresh_token()
        }

        myPageViewModel.userName.observe(viewLifecycleOwner){ user ->
            Log.d("user-value: ", user.toString())
            if(user != null){
                nickNamePrompt.text = "반갑습니다 ${user}님"
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