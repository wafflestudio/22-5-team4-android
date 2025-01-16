package com.example.interpark.fragments.bookFlow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.interpark.databinding.FragmentCalendarBinding
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private var selectedDate: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)

        // 날짜 선택 시 동작
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)

            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            selectedDate = formatter.format(calendar.time)

            binding.selectedDateText.text = "선택한 날짜: $selectedDate"
            binding.confirmDateButton.visibility = View.VISIBLE
        }

        // "확인" 버튼 클릭 시 동작
        binding.confirmDateButton.setOnClickListener {
            selectedDate?.let {
                val action = CalendarFragmentDirections
                    .actionCalendarFragmentToTimeSelectionFragment(it)
                findNavController().navigate(action)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뒤로가기 버튼 동작 연결
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
