package com.example.interpark.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.interpark.R

class TimeSelectionFragment : Fragment() {

    private val args: TimeSelectionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_time_selection, container, false)
        val selectedDateText: TextView = view.findViewById(R.id.selectedDateText)

        // 전달받은 날짜 표시
        selectedDateText.text = "선택한 날짜: ${args.selectedDate}"

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뒤로가기 버튼 동작 연결
        val backButton: ImageView = view.findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val navController = requireActivity().findNavController(R.id.categoryNavHost)
            navController.navigateUp() // 이전 화면으로 이동
        }

        // 시간 선택 버튼 동작 연결
        val timeSelectionButton: View = view.findViewById(R.id.timeSelectionButton)
        timeSelectionButton.setOnClickListener {
            // 선택된 시간 전달 (예: "18:00")
            val action = TimeSelectionFragmentDirections.actionTimeSelectionToSeatSelectionFragment(
                selectedDate = args.selectedDate,
                selectedTime = "18:00"
            )
            it.findNavController().navigate(action)
        }
    }
}

