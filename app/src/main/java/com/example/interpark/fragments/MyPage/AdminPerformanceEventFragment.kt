package com.example.interpark.fragments.MyPage

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.interpark.R
import com.example.interpark.data.types.AdminPerformanceEventRequest
import com.example.interpark.databinding.FragmentAdminPerformanceEventBinding
import com.example.interpark.viewModels.AdminViewModel
import com.example.interpark.viewModels.AdminViewModelFactory
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

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

        // 날짜 선택 기능 설정
        setupDatePickers()

        // Proceed 버튼 설정
        setupProceedButton(performanceId, hallId)
    }

    private fun setupDatePickers() {
        val calendar = Calendar.getInstance()

        // Start Date Picker
        binding.startEditText.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    val formattedDate = String.format(
                        Locale.getDefault(),
                        "%04d-%02d-%02d",
                        year,
                        month + 1,
                        dayOfMonth
                    )
                    binding.startEditText.setText(formattedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // End Date Picker
        binding.endEditText.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    val formattedDate = String.format(
                        Locale.getDefault(),
                        "%04d-%02d-%02d",
                        year,
                        month + 1,
                        dayOfMonth
                    )
                    binding.endEditText.setText(formattedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun setupProceedButton(performanceId: String, hallId: String) {
        binding.proceedButton.setOnClickListener {
            val startAtInput = binding.startEditText.text.toString()
            val endAtInput = binding.endEditText.text.toString()

            if (startAtInput.isEmpty() || endAtInput.isEmpty()) {
                // 입력되지 않은 경우
                Toast.makeText(requireContext(), "모든 날짜를 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                // ISO 8601 형식으로 변환
                val startAt = "${startAtInput}T00:00:00Z"
                val endAt = "${endAtInput}T23:59:59Z"

                val eventRequest = AdminPerformanceEventRequest(
                    performanceId = performanceId,
                    performanceHallId = hallId,
                    startAt = startAt,
                    endAt = endAt
                )

                // API 호출
                viewModel.createPerformanceEvent(eventRequest) {
                    // 성공한 경우 Toast 메시지
                    Toast.makeText(requireContext(), "이벤트가 성공적으로 생성되었습니다.", Toast.LENGTH_SHORT).show()

                    findNavController().navigate(R.id.action_AdminPerformanceEventFragment_to_LoginFragment)
                }
            } catch (e: Exception) {
                // 오류 발생 시 Toast 메시지
                Toast.makeText(requireContext(), "이벤트 생성 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
