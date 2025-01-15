package com.example.interpark.fragments.MyPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.interpark.R
import com.example.interpark.databinding.FragmentMySignupBinding
import com.example.interpark.viewModels.MyPageViewModel
import com.example.interpark.viewModels.MyPageViewModelFactory


class SignUpFragment : Fragment() {

    private var _binding: FragmentMySignupBinding? = null
    private val binding get() = _binding!!
    private val myPageViewModel: MyPageViewModel by viewModels { MyPageViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMySignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signUpButton = view.findViewById<Button>(R.id.btn_signup)
        val usernameEditText: EditText = view.findViewById(R.id.et_username)
        val passwordEditText: EditText = view.findViewById(R.id.et_password)
        val passwordConfirmEditText: EditText = view.findViewById(R.id.et_password_confirm)
        val nicknameEditText: EditText = view.findViewById(R.id.et_name)
        val emailEditText: EditText = view.findViewById(R.id.et_email)
        val phoneEditText: EditText = view.findViewById(R.id.et_phone)
        signUpButton.setOnClickListener{
            myPageViewModel.signup(usernameEditText.text.toString(), passwordEditText.text.toString(), nicknameEditText.text.toString(), emailEditText.text.toString(), phoneEditText.text.toString())
        }

        val logo = view.findViewById<ImageView>(R.id.logo_interpark)
        logo.setOnClickListener {
            val navController = requireActivity().findNavController(R.id.myNavHost)
            val action = SignUpFragmentDirections.actionSignUpFragmentToMyFragment()
            navController.navigate(action)
        }

    }
}