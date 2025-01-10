package com.example.interpark.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.interpark.databinding.FragmentTimeSelectionBinding

class TimeSelectionFragment : Fragment() {
    private var _binding: FragmentTimeSelectionBinding? = null
    private val binding get() = _binding!!

    private val args: TimeSelectionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimeSelectionBinding.inflate(inflater, container, false)

        // 전달받은 날짜 표시
        binding.selectedDateText.text = "선택한 날짜: ${args.selectedDate}"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뒤로가기 버튼 동작 연결
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // 시간 선택 버튼 동작 연결
        binding.timeSelectionButton.setOnClickListener {
            // 선택된 시간 전달 (예: "18:00")
            val action = TimeSelectionFragmentDirections.actionTimeSelectionToSeatSelectionFragment(
                selectedDate = args.selectedDate,
                selectedTime = "18:00"
            )
            it.findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

