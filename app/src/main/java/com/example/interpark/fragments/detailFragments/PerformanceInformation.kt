package com.example.interpark.fragments.detailFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.interpark.R
import com.example.interpark.databinding.FragmentPerformanceDetailBinding
import com.example.interpark.databinding.FragmentPerformanceDetailPerformanceInformationBinding
import com.example.interpark.fragments.PerformanceDetailFragmentArgs
import com.example.interpark.fragments.PerformanceDetailFragmentDirections
import com.example.interpark.viewModels.PerformanceDetailViewModel
import com.example.interpark.viewModels.PerformanceDetailViewModelFactory


class PerformanceInformation : Fragment() {
    private var _binding: FragmentPerformanceDetailPerformanceInformationBinding? = null
    private val binding get() = _binding!!
    private val performanceDetailViewModel: PerformanceDetailViewModel by viewModels {
        PerformanceDetailViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerformanceDetailPerformanceInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val perfId = arguments?.getString("key") ?: "No Data"
        performanceDetailViewModel.fetchPerformanceDetail(perfId)
        performanceDetailViewModel.performanceDetail.observe(viewLifecycleOwner) { performance ->
            binding.apply{
                detailImageView.load(performance.detail)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
