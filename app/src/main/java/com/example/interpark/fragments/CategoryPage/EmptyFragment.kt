package com.example.interpark.fragments.CategoryPage

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
import com.example.interpark.databinding.FragmentEmptyBinding
import com.example.interpark.viewModels.MyPageViewModel
import com.example.interpark.viewModels.MyPageViewModelFactory
import com.example.interpark.viewModels.PerformanceViewModel
import com.example.interpark.viewModels.PerformanceViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EmptyFragment : Fragment() {
    private var _binding: FragmentEmptyBinding? = null
    private val binding get() = _binding!!
    private val performanceViewModel: PerformanceViewModel by viewModels { PerformanceViewModelFactory(requireContext()) }
    private val myPageViewModel: MyPageViewModel by viewModels { MyPageViewModelFactory(requireContext()) }

    private lateinit var categoryTitleTextView: TextView
    private lateinit var performanceRecyclerView: RecyclerView
    private val args: EmptyFragmentArgs by navArgs() // Safe Args로 전달된 데이터

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEmptyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton: ImageButton = view.findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val navController = requireActivity().findNavController(R.id.categoryNavHost)
            navController.navigateUp() // 이전 화면으로 이동
        }
        binding.backButton.setOnClickListener{
            findNavController().navigateUp()
        }



        // TextView 초기화
        categoryTitleTextView = view.findViewById(R.id.categoryTitleTextView)
        categoryTitleTextView.text = args.category // Safe Args로 전달받은 데이터



        // RecyclerView 설정
        performanceRecyclerView = view.findViewById(R.id.performanceRecyclerView)
        performanceRecyclerView.layoutManager = LinearLayoutManager(context)
        performanceViewModel.performanceList.observe(viewLifecycleOwner){ performanceList ->
            setupRecyclerView()
        }
        performanceViewModel.fetchPerformanceList(category = args.category, title = null)
    }

    private fun setupRecyclerView() {
        val adapter = PerformanceAdapter { performance ->
            val action = EmptyFragmentDirections
                .actionEmptyFragmentToPerformanceDetailFragment(performance.id)
            findNavController().navigate(action)
        }

        binding.performanceRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        lifecycleScope.launch {
            performanceViewModel.getPerformancePagingData(null, null)
                .collectLatest { pagingData ->
                    adapter.submitData(pagingData) // ✅ PagingDataAdapter는 submitData() 사용해야 함
                }
        }
    }


}
