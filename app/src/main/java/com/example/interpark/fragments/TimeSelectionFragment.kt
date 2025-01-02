package com.example.interpark.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
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
}
