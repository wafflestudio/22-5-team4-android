package com.example.interpark.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.interpark.R
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {

    private lateinit var calendarView: CalendarView
    private lateinit var selectedDateText: TextView
    private lateinit var confirmDateButton: Button

    private var selectedDate: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        calendarView = view.findViewById(R.id.calendarView)
        selectedDateText = view.findViewById(R.id.selectedDateText)
        confirmDateButton = view.findViewById(R.id.confirmDateButton)

        // 날짜 선택 시 동작
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)

            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            selectedDate = formatter.format(calendar.time)

            selectedDateText.text = "선택한 날짜: $selectedDate"
            confirmDateButton.visibility = View.VISIBLE
        }

        // "확인" 버튼 클릭 시 동작
        confirmDateButton.setOnClickListener {
            selectedDate?.let {
                val action = CalendarFragmentDirections
                    .actionCalendarFragmentToTimeSelectionFragment(it)
                findNavController().navigate(action)
            }
        }

        return view
    }
}
