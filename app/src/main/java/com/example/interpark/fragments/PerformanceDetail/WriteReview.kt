package com.example.interpark.fragments.PerformanceDetail

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.interpark.R
import com.example.interpark.auth.AuthManager
import com.example.interpark.data.types.Review
import com.example.interpark.data.types.ReviewError
import com.example.interpark.databinding.FragmentPerformanceDetailWriteReviewBinding
import com.example.interpark.viewModels.ReviewViewModel
import com.example.interpark.viewModels.ReviewViewModelFactory


class WriteReview : Fragment() {
    private var _binding: FragmentPerformanceDetailWriteReviewBinding? = null
    private val binding get() = _binding!!
    private val args: PerformanceDetailFragmentArgs by navArgs()
    private lateinit var reviewRecyclerView: RecyclerView
    private val ReviewViewModel: ReviewViewModel by viewModels {
        ReviewViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerformanceDetailWriteReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.writeReviewButton.setOnClickListener {

            if(AuthManager.getUser() == null){
                Toast.makeText(context, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("body title:", args.title)
            Log.d("rating: ", binding.ratingBar.rating.toString())
            ReviewViewModel.writeReview(args.title,
                binding.ratingBar.rating.toInt(), binding.etTitle.text.toString(), binding.etContent.text.toString())
        }

        lifecycleScope.launchWhenStarted {
            ReviewViewModel.reviewWriteStatus.collect { status ->
                if (status?.isSuccess == true) {
                    findNavController().popBackStack()
                }
            }
        }
//
//        lifecycleScope.launchWhenStarted {
//            ReviewViewModel.reviewWriteError.collect { error ->
//                when (error) {
//                    is ReviewError.Unauthorized -> {
//                        Toast.makeText(context, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show()
//                    }
//                    is ReviewError.ServerError -> {
//                        Toast.makeText(context, "서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
//                    }
//                    is ReviewError.NetworkError -> {
//                        Toast.makeText(context, "네트워크 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
//                    }
//                    is ReviewError.Unknown -> {
//                        Toast.makeText(context, "알 수 없는 문제가 발생했습니다: ${error.message}", Toast.LENGTH_SHORT).show()
//                    }
//                    null -> {
//                        // 초기 상태: 아무 작업도 하지 않음
//                    }
//                }
//                ReviewViewModel.resetStatus()
//            }
//        }

        binding.ratingBar.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { // 포커스를 잃었을 때만 호출
                ReviewViewModel.ratingCheck(binding.ratingBar.rating as Int)
            }
        }

        binding.etContent.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { // 포커스를 잃었을 때만 호출
                ReviewViewModel.contentCheck(binding.etContent.text.toString())
            }
        }

        binding.etTitle.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { // 포커스를 잃었을 때만 호출
                ReviewViewModel.titleCheck(binding.etTitle.text.toString())
            }
        }

        ReviewViewModel.ratingError.observe(viewLifecycleOwner) { value ->
            binding.ratingBarError.visibility = when(value){
                true -> View.VISIBLE
                false -> View.GONE
            }
        }

        ReviewViewModel.titleError.observe(viewLifecycleOwner) { value ->
            binding.titleEditTextError.visibility = when(value){
                true -> View.VISIBLE
                false -> View.GONE
            }
        }

        ReviewViewModel.contentError.observe(viewLifecycleOwner) { value ->
            binding.contentEditTextError.visibility = when(value){
                true -> View.VISIBLE
                false -> View.GONE
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
