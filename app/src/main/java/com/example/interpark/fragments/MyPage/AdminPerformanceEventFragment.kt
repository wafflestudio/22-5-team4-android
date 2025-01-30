package com.example.interpark.fragments.MyPage

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
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

        val performanceId = args.performanceId2
        val hallId = args.HallId

        Log.d("AdminPerformanceEventFragment", "Performance ID: $performanceId")
        Log.d("AdminPerformanceEventFragment", "Hall ID: $hallId")

        // 날짜 및 시간 선택 기능 설정
        setupDateTimePickers()

        // Proceed 버튼 설정
        setupProceedButton(performanceId, hallId)
    }

    private fun setupDateTimePickers() {
        val calendar = Calendar.getInstance()

        // Start Date Picker
        binding.startEditText.setOnClickListener {
            showDatePicker(binding.startEditText, calendar)
        }

        // End Date Picker
        binding.endEditText.setOnClickListener {
            showDatePicker(binding.endEditText, calendar)
        }

        // Start Time Picker
        binding.startTimeEditText.setOnClickListener {
            showTimePicker(binding.startTimeEditText, calendar)
        }

        // End Time Picker
        binding.endTimeEditText.setOnClickListener {
            showTimePicker(binding.endTimeEditText, calendar)
        }
    }

    private fun showDatePicker(editText: EditText, calendar: Calendar) {
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val formattedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth)
                editText.setText(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showTimePicker(editText: EditText, calendar: Calendar) {
        TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                val formattedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)
                editText.setText(formattedTime)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun setupProceedButton(performanceId: String, hallId: String) {
        binding.proceedButton.setOnClickListener {
            val startDate = binding.startEditText.text.toString()
            val endDate = binding.endEditText.text.toString()
            val startTime = binding.startTimeEditText.text.toString()
            val endTime = binding.endTimeEditText.text.toString()

            if (startDate.isEmpty() || endDate.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
                Toast.makeText(requireContext(), "모든 날짜 및 시간을 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                val startCalendar = Calendar.getInstance().apply {
                    time = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(startDate)!!
                }

                val endCalendar = Calendar.getInstance().apply {
                    time = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(endDate)!!
                }

                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                val startTimeFormatted = "T${startTime}:00Z"
                val endTimeFormatted = "T${endTime}:00Z"

                val apiCalls = mutableListOf<Deferred<Unit>>()

                val scope = CoroutineScope(Dispatchers.IO)

                scope.launch {
                    while (!startCalendar.after(endCalendar)) {
                        val currentDate = dateFormat.format(startCalendar.time)

                        val eventRequest = AdminPerformanceEventRequest(
                            performanceId = performanceId,
                            performanceHallId = hallId,
                            startAt = "$currentDate$startTimeFormatted",
                            endAt = "$currentDate$endTimeFormatted"
                        )

                        apiCalls.add(async {
                            viewModel.createPerformanceEvent(eventRequest) {
                                Log.d("AdminPerformanceEventFragment", "이벤트 생성 성공: $currentDate")
                            }
                        })

                        startCalendar.add(Calendar.DAY_OF_MONTH, 1)
                    }

                    apiCalls.awaitAll()

                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "이벤트가 성공적으로 생성되었습니다.", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_AdminPerformanceEventFragment_to_LoginFragment)
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "이벤트 생성 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
