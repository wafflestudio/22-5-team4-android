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
import com.example.interpark.viewModels.PerformanceDetailViewModel
import com.example.interpark.viewModels.PerformanceDetailViewModelFactory


class Reviews : Fragment() {
    private var _binding: FragmentPerformanceDetailReviewsBinding? = null
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
        _binding = FragmentPerformanceDetailReviewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reviewRecyclerView = view.findViewById(R.id.reviewRecyclerView)
        reviewRecyclerView.layoutManager =LinearLayoutManager(context)
        performanceDetailViewModel.performanceReviews.observe(viewLifecycleOwner){ reviews ->
            setRecyclerView(reviews)
        }
        performanceDetailViewModel.fetchPerformanceReviews("")

        binding.NavWriteReviewButton.setOnClickListener {
            val action = PerformanceDetailFragmentDirections.actionPerformanceDetailFragmentToWriteReviewFragment("")
            findNavController().navigate(action)
        }
    }

    private fun setRecyclerView(data: List<Review>?){
        if(data == null) return
        reviewRecyclerView.adapter = ReviewAdapter(data, {
            Log.d("asdf", "asdf")
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
