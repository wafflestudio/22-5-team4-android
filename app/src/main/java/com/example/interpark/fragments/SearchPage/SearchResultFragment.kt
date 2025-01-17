package com.example.interpark.fragments.SearchPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interpark.R
import com.example.interpark.adapters.PerformanceAdapter
import com.example.interpark.data.types.Performance
import com.example.interpark.databinding.FragmentSearchResultBinding
import com.example.interpark.viewModels.PerformanceViewModel
import com.example.interpark.viewModels.PerformanceViewModelFactory

class SearchResultFragment : Fragment() {
    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!
    private val performanceViewModel: PerformanceViewModel by viewModels { PerformanceViewModelFactory(requireContext()) }

    private lateinit var searchTitleTextView: TextView
    private lateinit var performanceRecyclerView: RecyclerView
    private val args: SearchResultFragmentArgs by navArgs() // Safe Args로 전달된 데이터

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뒤로가기 버튼 동작 연결
        val backButton: ImageButton = view.findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val navController = requireActivity().findNavController(R.id.searchNavHost)
            navController.navigateUp() // 이전 화면으로 이동
        }

        // TextView 초기화 (검색어 표시)
        searchTitleTextView = view.findViewById(R.id.searchTitleTextView)
        searchTitleTextView.text = args.searchtitle // Safe Args로 전달받은 검색어

        // RecyclerView 설정
        performanceRecyclerView = view.findViewById(R.id.performanceRecyclerView)
        performanceRecyclerView.layoutManager = LinearLayoutManager(context)

        // ViewModel을 통해 공연 데이터 가져오기
        performanceViewModel.performanceList.observe(viewLifecycleOwner) { performanceList ->
            setRecyclerView(performanceList)
        }

        // ViewModel을 통해 검색어에 해당하는 공연 목록 가져오기
        performanceViewModel.fetchPerformanceList(category = null, title = args.searchtitle)
    }

    private fun setRecyclerView(data: List<Performance>) {
        performanceRecyclerView.adapter = PerformanceAdapter(data) { performance ->
            val navController = requireActivity().findNavController(R.id.searchNavHost)
            val action = SearchResultFragmentDirections
                .actionSearchResultFragmentToPerformanceDetailFragment(performance.id)
            navController.navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
