package com.example.interpark.fragments.MyPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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

        fun EditText.value() = this.text.toString()

        binding.btnSignup.setOnClickListener {
            // Switch 상태 확인 (관리자 여부)
            val role = if (binding.adminCheck.isChecked) "ADMIN" else ""

            // ViewModel에 데이터 전달
            myPageViewModel.signup(
                username = binding.etUsername.text.toString(),
                password = binding.etPassword.text.toString(),
                nickname = binding.etName.text.toString(),
                phoneNumber = binding.etPhone.text.toString(),
                email = binding.etEmail.text.toString(),
                role = role
//                username = "adminname_value",
//                password = "760131Ab!",
//                nickname = "test_admin",
//                phoneNumber = "000-0000-0000",
//                email = "test@example.com",
                //role = "ADMIN"

            )
        }

        binding.logoInterpark.setOnClickListener {
            val navController = requireActivity().findNavController(R.id.myNavHost)
            val action = SignUpFragmentDirections.actionSignUpFragmentToMyFragment()
            navController.navigate(action)
        }

        val editTexts = listOf(binding.etUsername, binding.etPassword, binding.etPasswordConfirm, binding.etName, binding.etEmail, binding.etPhone)

        val focusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                val editText = view as EditText
                val textValue = editText.text.toString()
                when (view.id) {
                    R.id.et_username -> myPageViewModel.usernameLengthCheck(textValue)
                    R.id.et_password -> myPageViewModel.passwordLengthCheck(textValue)
                    R.id.et_password_confirm -> myPageViewModel.passwordSameCheck(binding.etPassword.text.toString(), textValue)
                }
            }
        }
        editTexts.forEach { it.onFocusChangeListener = focusChangeListener }

        myPageViewModel.usernameLength.observe(viewLifecycleOwner) { usernameLength ->
            if(usernameLength){
                binding.usernameLengthError.visibility = View.GONE
            }
            else{
                binding.usernameLengthError.visibility = View.VISIBLE
                binding.usernameLengthError.text = "아이디는 6~20자 영문, 숫자만 사용 가능합니다."
            }
        }

        myPageViewModel.passwordLength.observe(viewLifecycleOwner) { passwordLength ->
            if(passwordLength){
                binding.passwordLengthError.visibility = View.GONE
            }
            else{
                binding.passwordLengthError.visibility = View.VISIBLE
                binding.passwordLengthError.text = "비밀번호는 8~12자 영문, 숫자, 특수문자만 사용 가능합니다."
            }
        }

        myPageViewModel.passwordSame.observe(viewLifecycleOwner) { passwordSame ->
            if(passwordSame){
                binding.passwordSameError.visibility = View.GONE
            }
            else{
                binding.passwordSameError.visibility = View.VISIBLE
                binding.passwordSameError.text = "비밀번호가 일치하지 않습니다. 다시 입력해주세요."
            }
        }

        myPageViewModel.signUpFailed.observe(viewLifecycleOwner) { signUpFailed ->
            if(signUpFailed){
                binding.signUpFailedText.visibility = View.VISIBLE
                binding.signUpFailedText.text = "이미 있는 아이디입니다."
            }
        }

        myPageViewModel.signUpSuccess.observe(viewLifecycleOwner) { signUpSuccess ->
            if(signUpSuccess){
                val navController = requireActivity().findNavController(R.id.myNavHost)
                val action = SignUpFragmentDirections.actionSignUpFragmentToMyFragment()
                navController.navigate(action)
            }
        }

    }
}