package com.example.interpark.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interpark.R
import com.example.interpark.adapters.PerformanceAdapter
import com.example.interpark.data.Performance
import com.example.interpark.databinding.FragmentEmptyBinding

class EmptyFragment : Fragment() {
    private var _binding: FragmentEmptyBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoryTitleTextView: TextView
    private lateinit var performanceRecyclerView: RecyclerView
    private val args: EmptyFragmentArgs by navArgs() // Safe Args로 전달된 데이터

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEmptyBinding.inflate(inflater, container, false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뒤로가기 버튼 동작 연결
        val backButton: ImageButton = view.findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val navController = requireActivity().findNavController(R.id.categoryNavHost)
            navController.navigateUp() // 이전 화면으로 이동
        }


        // TextView 초기화
        categoryTitleTextView = view.findViewById(R.id.categoryTitleTextView)
        categoryTitleTextView.text = args.category // Safe Args로 전달받은 데이터

        // RecyclerView 설정
        performanceRecyclerView = view.findViewById(R.id.performanceRecyclerView)
        performanceRecyclerView.layoutManager = LinearLayoutManager(context)
        performanceRecyclerView.adapter = PerformanceAdapter(getDummyPerformances()) { performance ->
            val navController = requireActivity().findNavController(R.id.categoryNavHost)
            val action = EmptyFragmentDirections
                .actionEmptyFragmentToPerformanceDetailFragment(performance.title)
            navController.navigate(action)
        }
    }

    private fun getDummyPerformances(): List<Performance> {
        return listOf(
            Performance("라파치니의 정원", "플러스씨어터", "2025.01.30 - 2025.04.20", R.drawable.performance1),
            Performance("오지게 재밌는 가시나들", "국립극장 하늘극장", "2025.02.11 - 2025.02.27", R.drawable.performance2),
            Performance("미아 파밀리아", "링크아트센터드림 드림1관", "2024.12.19 - 2025.03.23", R.drawable.performance3),
            Performance("카포네 밀크", "예스24아트원 1관", "2024.12.18 - 2025.03.09", R.drawable.performance4)
        )
    }
}
