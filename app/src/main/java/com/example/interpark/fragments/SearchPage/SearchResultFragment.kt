package com.example.interpark.fragments.SearchPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interpark.R
import com.example.interpark.adapters.PerformanceAdapter
import com.example.interpark.data.types.Performance
import com.example.interpark.databinding.FragmentSearchResultBinding
import com.example.interpark.fragments.CategoryPage.EmptyFragmentDirections
import com.example.interpark.viewModels.PerformanceViewModel
import com.example.interpark.viewModels.PerformanceViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchResultFragment : Fragment() {
    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!
    private val performanceViewModel: PerformanceViewModel by viewModels { PerformanceViewModelFactory(requireContext()) }

    private lateinit var performanceRecyclerView: RecyclerView
    private val args: SearchResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뒤로가기 버튼 설정
        binding.backButton.setOnClickListener {
            requireActivity().findNavController(R.id.searchNavHost).navigateUp()
        }

        // 검색어 표시
        binding.searchTitleTextView.text = args.searchtitle

        // RecyclerView 설정
        performanceRecyclerView = view.findViewById(R.id.performanceRecyclerView)
        performanceRecyclerView.layoutManager = LinearLayoutManager(context)
        performanceViewModel.performanceList.observe(viewLifecycleOwner){ performanceList ->
            setupRecyclerView()
        }
//        performanceViewModel.fetchPerformanceList(category = null, title = args.searchtitle)
    }

    private fun setupRecyclerView() {
        val adapter = PerformanceAdapter { performance ->
            val action = SearchResultFragmentDirections
                .actionSearchResultFragmentToPerformanceDetailFragment(performance.id)
            findNavController().navigate(action)
        }

        binding.performanceRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        lifecycleScope.launch {
            performanceViewModel.getPerformancePagingData(null, args.searchtitle)
                .collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
