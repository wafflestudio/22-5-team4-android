package com.example.interpark.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.interpark.R
import com.example.interpark.databinding.FragmentPerformanceDetailBinding
import com.example.interpark.viewModels.PerformanceDetailViewModel
import com.example.interpark.viewModels.PerformanceDetailViewModelFactory

class PerformanceDetailFragment : Fragment(R.layout.fragment_performance_detail) {
    private var _binding: FragmentPerformanceDetailBinding? = null
    private val binding get() = _binding!!
    private val args: PerformanceDetailFragmentArgs by navArgs()
    private val performanceDetailViewModel: PerformanceDetailViewModel by viewModels {
        PerformanceDetailViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerformanceDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel을 통해 공연 상세 정보 가져오기
        performanceDetailViewModel.fetchPerformanceDetail(args.title)

        // LiveData 관찰하여 UI 업데이트
        performanceDetailViewModel.performanceDetail.observe(viewLifecycleOwner) { performance ->
            binding.apply {
                titleTextView.text = performance.title
                contentTextView.text = performance.content
                detailTextView.text = performance.detail
                dateTextView.text = performance.date.joinToString { it.toString() }
                categoryTextView.text = performance.category
                locationTextView.text = performance.location

                // 포스터 이미지 로드 (예시: Glide 사용)
                Glide.with(this@PerformanceDetailFragment)
                    .load(performance.posterUrl)
                    .into(posterImageView)
            }
        }

        // 예매하기 버튼 동작 연결
        binding.bookButton.setOnClickListener {
            val action = PerformanceDetailFragmentDirections
                .actionPerformanceDetailFragmentToCalendarFragment()
            findNavController().navigate(action)
        }

        // 뒤로가기 버튼 동작 연결
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
