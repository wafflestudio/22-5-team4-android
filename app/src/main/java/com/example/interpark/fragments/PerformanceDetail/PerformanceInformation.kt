package com.example.interpark.fragments.PerformanceDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.example.interpark.databinding.FragmentPerformanceDetailPerformanceInformationBinding
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
                detailImageView.load(performance!!.detail)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

//package com.example.interpark.fragments.PerformanceDetail
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import coil.load
//import com.example.interpark.databinding.FragmentPerformanceDetailPerformanceInformationBinding
//import com.example.interpark.viewModels.PerformanceDetailViewModel
//import com.example.interpark.viewModels.PerformanceDetailViewModelFactory
//
//class PerformanceInformation : Fragment() {
//    private var _binding: FragmentPerformanceDetailPerformanceInformationBinding? = null
//    private val binding get() = _binding!!
//    private val performanceDetailViewModel: PerformanceDetailViewModel by viewModels {
//        PerformanceDetailViewModelFactory(requireContext())
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentPerformanceDetailPerformanceInformationBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val perfId = arguments?.getString("key") ?: return  // ✅ perfId가 없으면 처리 중단
//        performanceDetailViewModel.fetchPerformanceDetail(perfId)
//
//        performanceDetailViewModel.performanceDetail.observe(viewLifecycleOwner) { performance ->
//            performance?.let {
//                binding.detailImageView.load(it.detail ?: "https://upload.wikimedia.org/wikipedia/commons/a/a3/Image-not-found.png")
//            }
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
