package com.example.interpark.fragments.PerformanceDetail

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interpark.R
import com.example.interpark.adapters.ReviewAdapter
import com.example.interpark.data.types.Review
import com.example.interpark.databinding.FragmentPerformanceDetailReviewsBinding
import com.example.interpark.databinding.FragmentPerformanceDetailWriteReviewBinding
import com.example.interpark.viewModels.PerformanceDetailViewModel
import com.example.interpark.viewModels.PerformanceDetailViewModelFactory


class WriteReview : Fragment() {
    private var _binding: FragmentPerformanceDetailWriteReviewBinding? = null
    private val binding get() = _binding!!
    private val args: PerformanceDetailFragmentArgs by navArgs()
    private lateinit var reviewRecyclerView: RecyclerView
    private val performanceDetailViewModel: PerformanceDetailViewModel by viewModels {
        PerformanceDetailViewModelFactory(requireContext())
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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
