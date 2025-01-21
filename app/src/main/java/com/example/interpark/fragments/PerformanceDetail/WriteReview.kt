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
import androidx.recyclerview.widget.RecyclerView
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
            Log.d("body title:", args.title)
            Log.d("rating: ", binding.ratingBar.rating.toString())
//            Log.d("title: ", binding.etTitle.text)
            ReviewViewModel.writeReview(args.title,
                binding.ratingBar.rating.toInt(), binding.etTitle.text.toString(), binding.etContent.text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
