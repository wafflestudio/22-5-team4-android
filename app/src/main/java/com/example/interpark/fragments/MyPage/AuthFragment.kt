package com.example.interpark.fragments.MyPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.interpark.R
import com.example.interpark.data.types.AdminPerformanceRequest
import com.example.interpark.databinding.FragmentAuthBinding
import com.example.interpark.viewModels.AdminViewModel
import com.example.interpark.viewModels.AdminViewModelFactory

class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AdminViewModel by viewModels { AdminViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.proceedButton.setOnClickListener {
            // 사용자 입력 값 가져오기
            val title = binding.titleEditText.text.toString()
            val detail = binding.detailEditText.text.toString()
            val posterUri = binding.posterUriEditText.text.toString()
            val backdropImageUri = binding.backdropUriEditText.text.toString()

            // Spinner에서 선택된 카테고리 가져오기
            val category = when (binding.categorySpinner.selectedItem.toString()) {
                "뮤지컬" -> "MUSICAL"
                "콘서트" -> "CONCERT"
                "연극" -> "PLAY"
                "클래식/무용" -> "CLASSIC"
                "스포츠" -> "SPORT"
                else -> "UNKNOWN"
            }

            // API 요청
            val performanceRequest = AdminPerformanceRequest(
                title = title,
                detail = detail,
                category = category,
                posterUri = posterUri,
                backdropImageUri = backdropImageUri
            )

            viewModel.createPerformance(performanceRequest) {
                val action = AuthFragmentDirections.actionAuthFragmentToAdminPerformanceHallFragment(
                    performanceId1 = viewModel.performanceId.value ?: ""
                )
                findNavController().navigate(action)
            }

        }
        binding.backButton.setOnClickListener{
            findNavController().navigateUp()
        }
//        binding.proceedButton.setOnClickListener {
//            // 사용자 입력 값 가져오기
//            val title = binding.titleEditText.text.toString()
//            val detail = binding.detailEditText.text.toString()
//            val category = binding.categoryEditText.text.toString()
//            val posterUri = binding.posterUriEditText.text.toString()
//            val backdropImageUri = binding.backdropUriEditText.text.toString()
//
//            // API 요청
//            val performanceRequest = AdminPerformanceRequest(
//                title = title,
//                detail = detail,
//                category = category,
//                posterUri = posterUri,
//                backdropImageUri = backdropImageUri
//            )
//
//            binding.proceedButton.setOnClickListener {
//                viewModel.createPerformance(performanceRequest) {
//                    findNavController().navigate(R.id.action_AuthFragment_to_AdminPerformanceHallFragment)
//                }
//            }
//            binding.backButton.setOnClickListener{
//                findNavController().navigateUp()
//            }
////
////            viewModel.createPerformance(performanceRequest) {
////                findNavController().navigate(R.id.action_AuthFragment_to_AdminPerformanceHallFragment)
////            }
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
