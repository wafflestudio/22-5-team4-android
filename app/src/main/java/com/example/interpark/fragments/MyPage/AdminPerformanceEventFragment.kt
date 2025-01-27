package com.example.interpark.fragments.MyPage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.interpark.databinding.FragmentAdminPerformanceEventBinding
import com.example.interpark.viewModels.AdminViewModel
import com.example.interpark.viewModels.AdminViewModelFactory
import com.example.interpark.viewModels.PerformanceViewModel

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.interpark.R
import com.example.interpark.data.types.AdminPerformanceEventRequest
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AdminPerformanceEventFragment : Fragment() {

    private var _binding: FragmentAdminPerformanceEventBinding? = null
    private val binding get() = _binding!!


    private val viewModel: AdminViewModel by activityViewModels { AdminViewModelFactory(requireContext()) }
    private val args: AdminPerformanceEventFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminPerformanceEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 초기 값 처리
        val performanceId = args.performanceId2
        val hallId = args.HallId

        Log.d("AdminPerformanceEventFragment", "Performance ID: $performanceId")
        Log.d("AdminPerformanceEventFragment", "Hall ID: $hallId")

        setupProceedButton(performanceId, hallId)

//        // 갱신된 값 처리
//        lifecycleScope.launch {
//            viewModel.performanceId.collect { newPerformanceId ->
//                viewModel.hallId.collect { newHallId ->
//                    if (newPerformanceId != null) {
//                        setupProceedButton(newPerformanceId, newHallId)
//                    }
//                }
//            }
//        }
    }


    private fun setupProceedButton(performanceId: String, hallId: String) {
        binding.proceedButton.setOnClickListener {
            val startAt = binding.startEditText.text.toString()
            val endAt = binding.endEditText.text.toString()


//            if (performanceId != null && hallId != null) {
                val eventRequest = AdminPerformanceEventRequest(
                    performanceId = performanceId,
                    performanceHallId = hallId,
                    startAt = startAt,
                    endAt = endAt
                )

                // API 호출
                viewModel.createPerformanceEvent(eventRequest) {
                    findNavController().navigate(R.id.action_AdminPerformanceEventFragment_to_LoginFragment)
                }
//            } else {
//                // 예외 처리
//                Toast.makeText(requireContext(), "ID가 설정되지 않았습니다.", Toast.LENGTH_SHORT).show()
//            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
