package com.example.interpark.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interpark.R
import com.example.interpark.adapters.HomeCategoryAdapter
import com.example.interpark.adapters.HomeCategoryRankCategory
import com.example.interpark.adapters.HomeCategoryRankCategoryListAdapter
import com.example.interpark.adapters.PosterAdapter
import com.example.interpark.data.types.Category
import com.example.interpark.data.types.CategoryItem
import com.example.interpark.data.types.list_categories
import com.example.interpark.databinding.FragmentHomeBinding
import com.example.interpark.viewModels.PerformanceDetailViewModel
import com.example.interpark.viewModels.PerformanceDetailViewModelFactory
import com.example.interpark.viewModels.PerformanceViewModel
import com.example.interpark.viewModels.PerformanceViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val performanceViewModel: PerformanceViewModel by viewModels { PerformanceViewModelFactory(requireContext()) }

    private lateinit var homeCategoryAdapter: HomeCategoryAdapter
    private lateinit var categoryRankCategoryListAdapter: HomeCategoryRankCategoryListAdapter
    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false) // 바인딩 초기화
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel에서 포스터 URI 로드
        performanceViewModel.loadPosterUris(category = null, title = null)

        // ViewModel 관찰 설정
        setupObservers()

        setupCategoryRecyclerView()
        setupCategoryRankRecyclerView()


    }

    private fun setupObservers() {
        performanceViewModel.posterUris.observe(viewLifecycleOwner) { posterUris ->
            setupPosterSlider(posterUris) // ViewPager2를 서버에서 받아온 포스터 URI로 설정
        }
    }

    private fun setupPosterSlider(posters: List<String>) {
        val posterAdapter = PosterAdapter(posters)
        binding.viewPager.adapter = posterAdapter

        // TabLayout과 연결
        TabLayoutMediator(binding.tabIndicator, binding.viewPager) { _, _ -> }.attach()

        // 자동 슬라이드 시작
        startAutoSlide(posters.size)
    }

    private fun startAutoSlide(totalPages: Int) {
        val runnable = object : Runnable {
            override fun run() {
                if (currentPage == totalPages) currentPage = 0
                binding.viewPager.setCurrentItem(currentPage++, true)
                handler.postDelayed(this, 3000) // 3초마다 슬라이드
            }
        }
        handler.postDelayed(runnable, 3000)
    }

    private fun setupCategoryRecyclerView() {
        // RecyclerView 초기화
        val categoryRecyclerView: RecyclerView = binding.homeCategoryRecyclerView
        categoryRecyclerView.layoutManager = GridLayoutManager(context, 5) // 4열

        // 데이터 설정
        val categories = listOf(
            CategoryItem.Category("MUSICAL", R.drawable.ic_musical),
            CategoryItem.Category("CONCERT", R.drawable.ic_concert),
            CategoryItem.Category("PLAY", R.drawable.ic_drama),
            CategoryItem.Category("CLASSIC", R.drawable.ic_classic),
            CategoryItem.Category("SPORT", R.drawable.ic_sports)
        )

        // 어댑터 설정
        homeCategoryAdapter = HomeCategoryAdapter(categories) { category ->
            navigateToCategoryDetail(category.name)
        }
        categoryRecyclerView.adapter = homeCategoryAdapter
    }

    private fun setupCategoryRankRecyclerView() {
        val categoryRankCategoryListRecyclerView: RecyclerView = binding.homeCategoryRankCategoryList
        categoryRankCategoryListRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // 랭크 카테고리 데이터 설정
        val homeCategoryRankCategories = list_categories.mapIndexed { index, category ->
            HomeCategoryRankCategory(
                category = category,
                selected = index == 0 // 첫 번째 항목만 선택
            )
        }

        // 어댑터 설정
        categoryRankCategoryListAdapter = HomeCategoryRankCategoryListAdapter(homeCategoryRankCategories) { category ->
            categoryRankCategoryListClicked(category)
        }
        categoryRankCategoryListRecyclerView.adapter = categoryRankCategoryListAdapter
    }

    private fun navigateToCategoryDetail(categoryName: String) {
        val navController = requireActivity().findNavController(R.id.homeNavHost)
        val action = HomeFragmentDirections.actionHomeFragmentToEmptyFragment(categoryName)
        navController.navigate(action)
    }

    private fun categoryRankCategoryListClicked(category: Category) {
        val navController = requireActivity().findNavController(R.id.homeNavHost)
        val action = HomeFragmentDirections.actionHomeFragmentToRankingFragment()
        navController.navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 바인딩 해제
        handler.removeCallbacksAndMessages(null) // 핸들러 정리
    }
}
