package com.example.interpark.fragments.MyPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.interpark.databinding.FragmentAdminPerformanceEventBinding
import com.example.interpark.viewModels.AdminViewModel
import com.example.interpark.viewModels.AdminViewModelFactory
import com.example.interpark.viewModels.PerformanceViewModel

import androidx.lifecycle.lifecycleScope
import com.example.interpark.data.types.AdminPerformanceEventRequest
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AdminPerformanceEventFragment : Fragment() {

    private var _binding: FragmentAdminPerformanceEventBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AdminViewModel by viewModels { AdminViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminPerformanceEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // performanceId와 hallId 수집
        lifecycleScope.launch {
            viewModel.performanceId.collect { performanceId ->
                viewModel.hallId.collect { hallId ->
                    setupProceedButton(performanceId, hallId)
                }
            }
        }
    }

    private fun setupProceedButton(performanceId: String?, hallId: String?) {
        binding.proceedButton.setOnClickListener {
            val startAt = binding.startEditText.text.toString()
            val endAt = binding.endEditText.text.toString()

            if (performanceId != null && hallId != null) {
                val eventRequest = AdminPerformanceEventRequest(
                    performanceId = performanceId,
                    performanceHallId = hallId,
                    startAt = startAt,
                    endAt = endAt
                )

                // API 호출
//                viewModel.createPerformanceEvent(eventRequest) {
//                    findNavController().navigate(R.id.action_performanceEventFragment_to_completionFragment)
//                }
            } else {
                // 예외 처리
                Toast.makeText(requireContext(), "ID가 설정되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
