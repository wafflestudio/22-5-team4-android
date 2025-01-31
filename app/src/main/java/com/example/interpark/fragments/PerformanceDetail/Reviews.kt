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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interpark.R
import com.example.interpark.adapters.ReviewAdapter
import com.example.interpark.data.types.Comment
import com.example.interpark.data.types.Review
import com.example.interpark.databinding.FragmentPerformanceDetailReviewsBinding
import com.example.interpark.viewModels.PerformanceDetailViewModel
import com.example.interpark.viewModels.PerformanceDetailViewModelFactory
import com.example.interpark.viewModels.ReviewViewModel
import com.example.interpark.viewModels.ReviewViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class Reviews : Fragment() {
    private var _binding: FragmentPerformanceDetailReviewsBinding? = null
    private val binding get() = _binding!!
    private val args: PerformanceDetailFragmentArgs by navArgs()
    private lateinit var reviewAdapter: ReviewAdapter

    private val reviewViewModel: ReviewViewModel by viewModels {
        ReviewViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerformanceDetailReviewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reviewAdapter = ReviewAdapter(
            writeComment = { reviewId, content -> reviewViewModel.writeComment(requireContext(), reviewId, content) },
            readComment = { reviewId -> reviewViewModel.readComment(reviewId) }
        )

        binding.reviewRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = reviewAdapter
        }

        lifecycleScope.launch {
            reviewViewModel.reviews.collectLatest { pagingData ->
                reviewAdapter.submitData(pagingData)
            }
        }

        val performanceId = requireArguments().getString("key")
        reviewViewModel.loadReviews(performanceId!!)

        binding.NavWriteReviewButton.setOnClickListener {
            val action = PerformanceDetailFragmentDirections
                .actionPerformanceDetailFragmentToWriteReviewFragment(performanceId)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
