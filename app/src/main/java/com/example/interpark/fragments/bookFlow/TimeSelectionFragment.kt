package com.example.interpark.fragments.bookFlow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.interpark.data.types.PerformanceEvent
import com.example.interpark.data.types.User
import com.example.interpark.databinding.FragmentTimeSelectionBinding
import com.example.interpark.viewModels.PerformanceViewModel
import com.example.interpark.viewModels.PerformanceViewModelFactory


class TimeSelectionFragment : Fragment() {
    private var _binding: FragmentTimeSelectionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PerformanceViewModel by viewModels {
        PerformanceViewModelFactory(requireContext())
    }
    private val args: TimeSelectionFragmentArgs by navArgs()
    private var firstEventId: String = "" // 기본값 설정
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimeSelectionBinding.inflate(inflater, container, false)

        // 전달받은 날짜 표시
        // 예제 데이터
        binding.timeSelectionButton.text ="${args.selectedDate}"



        // 데이터 요청
        viewModel.fetchPerformanceEvents(args.title, args.selectedDate)

        // 응답 결과 처리
        viewModel.performanceEvents.observe(viewLifecycleOwner) { performanceEvents: List<PerformanceEvent>? ->
            performanceEvents?.let {
                if (it.isNotEmpty()) {
                    firstEventId = it[0].id // 0번째 event.id를 변수에 저장
                    Log.d("PerformanceEvent", "First Event ID: $firstEventId")
                } else {
                    Log.e("PerformanceEvent", "No events found")
                }
            } ?: Log.e("PerformanceEvent", "PerformanceEvents is null")
        }
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
                firstEventId
            )
            it.findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

