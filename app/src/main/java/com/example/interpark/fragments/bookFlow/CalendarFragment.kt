package com.example.interpark.fragments.bookFlow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.interpark.databinding.FragmentCalendarBinding
import com.example.interpark.viewModels.CalendarViewModel
import com.example.interpark.viewModels.CalendarViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private val args: CalendarFragmentArgs by navArgs()

    private var selectedDate: String? = null
    private lateinit var viewModel: CalendarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)

        // ViewModel 초기화
        val factory = CalendarViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, factory)[CalendarViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뒤로가기 버튼 동작
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // ViewModel에서 performanceDates 가져오기
        viewModel.performanceDates.observe(viewLifecycleOwner) { availableDates ->
            if (availableDates != null) {
                Log.d("CalendarFragment", "Available dates: $availableDates")
                setupCalendar(availableDates)
            } else {
                Log.d("CalendarFragment", "No available dates")
            }
        }

        // ViewModel로 데이터 요청
        val performanceId = args.movieid
        Log.d("CalendarFragment", "Performance ID: $performanceId")
        viewModel.fetchPerformanceDates(performanceId)
    }

    private fun setupCalendar(availableDates: List<String>) {
        // 날짜 형식 설정
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val validDates = availableDates.mapNotNull {
            try {
                formatter.parse(it)
            } catch (e: Exception) {
                Log.e("CalendarFragment", "Invalid date format: $it", e)
                null
            }
        }

        Log.d("CalendarFragment", "Setting up calendar with valid dates: $validDates")

        // 날짜 선택 이벤트 처리
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(year, month, dayOfMonth)
            selectedCalendar.set(Calendar.HOUR_OF_DAY, 0)
            selectedCalendar.set(Calendar.MINUTE, 0)
            selectedCalendar.set(Calendar.SECOND, 0)
            selectedCalendar.set(Calendar.MILLISECOND, 0)
            val selectedDate = selectedCalendar.time

            if (validDates.contains(selectedDate)) {
                val formattedDate = formatter.format(selectedDate)
                this.selectedDate = formattedDate
                binding.selectedDateText.text = "선택한 날짜: $formattedDate"
                binding.confirmDateButton.visibility = View.VISIBLE
                Log.d("CalendarFragment", "Date available: $formattedDate")
            } else {
                binding.selectedDateText.text = "선택할 수 없는 날짜입니다."
                binding.confirmDateButton.visibility = View.GONE
                Log.d("CalendarFragment", "Date not available: $selectedDate")
            }
        }

        // "확인" 버튼 동작 처리
        binding.confirmDateButton.setOnClickListener {
            selectedDate?.let { date ->
                val action = CalendarFragmentDirections
                    .actionCalendarFragmentToTimeSelectionFragment(date)
                findNavController().navigate(action)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
