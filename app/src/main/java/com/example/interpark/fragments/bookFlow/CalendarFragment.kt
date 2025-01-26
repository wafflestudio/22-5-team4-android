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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.interpark.adapters.CalendarAdapter
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
    private lateinit var adapter: CalendarAdapter
    private val calendar: Calendar = Calendar.getInstance()

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

        // RecyclerView 초기화
        adapter = CalendarAdapter(emptyList(), emptySet()) { selectedDate ->
            onDateSelected(selectedDate)
        }
        binding.calendarRecyclerView.layoutManager = GridLayoutManager(requireContext(), 7)
        binding.calendarRecyclerView.adapter = adapter

        // ViewModel에서 performanceDates 가져오기
        viewModel.performanceDates.observe(viewLifecycleOwner) { availableDates ->
            if (availableDates != null) {
                Log.d("CalendarFragment", "Available dates: $availableDates")
                updateCalendar(availableDates)
            } else {
                Log.d("CalendarFragment", "No available dates")
            }
        }

        // ViewModel로 데이터 요청
        val performanceId = args.movieid
        Log.d("CalendarFragment", "Performance ID: $performanceId")
        viewModel.fetchPerformanceDates(performanceId)

        // 월 변경 버튼
        binding.prevMonthButton.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            updateCalendarFromCurrentMonth()
        }

        binding.nextMonthButton.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            updateCalendarFromCurrentMonth()
        }

        // 초기 캘린더 데이터 설정
        updateCalendarFromCurrentMonth()
    }

    private fun updateCalendarFromCurrentMonth() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        binding.currentMonthText.text = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(calendar.time)

        viewModel.performanceDates.value?.let { availableDates ->
            updateCalendar(availableDates)
        }
    }

    private fun updateCalendar(availableDates: List<String>) {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val validDates = availableDates.mapNotNull {
            try {
                formatter.parse(it)
            } catch (e: Exception) {
                Log.e("CalendarFragment", "Invalid date format: $it", e)
                null
            }
        }.toSet()

        Log.d("CalendarFragment", "Valid dates: $validDates") // 유효한 날짜 확인

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dates = generateCalendarDates(year, month)
        adapter.updateDates(dates, validDates)
    }

    private fun onDateSelected(date: Date) {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        selectedDate = formatter.format(date)

        if (adapter.isAvailableDate(date)) { // 선택된 날짜가 availableDates에 포함된 경우
            binding.selectedDateText.text = "선택한 날짜: $selectedDate"
            binding.confirmDateButton.visibility = View.VISIBLE

            // "확인" 버튼 동작
            binding.confirmDateButton.setOnClickListener {
                selectedDate?.let { date ->
                    val action = CalendarFragmentDirections
                        .actionCalendarFragmentToTimeSelectionFragment(date, args.movieid)
                    findNavController().navigate(action)
                }
            }
        } else {
            binding.selectedDateText.text = "선택할 수 없는 날짜입니다."
            binding.confirmDateButton.visibility = View.GONE
        }
    }

    private fun generateCalendarDates(year: Int, month: Int): List<Date?> {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, 1)

        val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        val dates = mutableListOf<Date?>()

        // 이전 달 빈 칸 채우기
        for (i in 1 until firstDayOfWeek) {
            dates.add(null)
        }

        // 현재 달의 날짜 채우기
        for (day in 1..maxDay) {
            calendar.set(Calendar.DAY_OF_MONTH, day)
            dates.add(calendar.time)
        }

        // 다음 달 빈 칸 채우기
        while (dates.size % 7 != 0) {
            dates.add(null)
        }

        return dates
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
