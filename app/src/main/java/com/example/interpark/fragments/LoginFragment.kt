package com.example.interpark.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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
import androidx.navigation.fragment.findNavController
import com.example.interpark.R
import com.example.interpark.viewModels.MyPageViewModel
import com.example.interpark.viewModels.MyPageViewModelFactory


class LoginFragment : Fragment() {

    private val myPageViewModel: MyPageViewModel by viewModels { MyPageViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usernameEditText: EditText = view.findViewById(R.id.et_username)
        val passwordEditText: EditText = view.findViewById(R.id.et_password)
        val loginButton: Button = view.findViewById(R.id.btn_login)

        loginButton.setOnClickListener{
            myPageViewModel.login(usernameEditText.text.toString(), passwordEditText.text.toString())
        }

        val signUpTextView: TextView = view.findViewById(R.id.text_signup)

        signUpTextView.setOnClickListener {
            Log.d("signup", "Text")
            val navController = requireActivity().findNavController(R.id.myNavHost)
            val action = LoginFragmentDirections
                .actionLoginFragmentToSignUpFragment()
            navController.navigate(action)
        }

    }
}