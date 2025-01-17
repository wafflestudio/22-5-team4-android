package com.example.interpark.fragments.PerformanceDetail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import androidx.viewpager2.widget.ViewPager2
import com.example.interpark.R
import com.example.interpark.adapters.ViewPagers.FragmentViewPagerAdapter
import com.example.interpark.databinding.FragmentPerformanceDetailBinding
import com.example.interpark.viewModels.PerformanceDetailViewModel
import com.example.interpark.viewModels.PerformanceDetailViewModelFactory
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class PerformanceDetailFragment : Fragment() {
    private var _binding: FragmentPerformanceDetailBinding? = null
    private val binding get() = _binding!!
    private val args: PerformanceDetailFragmentArgs by navArgs()
    private val performanceDetailViewModel: PerformanceDetailViewModel by viewModels {
        PerformanceDetailViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        _binding = FragmentPerformanceDetailBinding.inflate(inflater, container, false)
        return inflater.inflate(R.layout.fragment_performance_detail, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel을 통해 공연 상세 정보 가져오기
        performanceDetailViewModel.fetchPerformanceDetail(args.title)
        val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
        val dateTextView = view.findViewById<TextView>(R.id.dateTextView)
        val categoryTextView = view.findViewById<TextView>(R.id.categoryTextView)
        val locationTextView = view.findViewById<TextView>(R.id.locationTextView)
        val title = view.findViewById<TextView>(R.id.title)
        // LiveData 관찰하여 UI 업데이트
        val posterImageView = view.findViewById<ImageView>(R.id.posterImageView)
        performanceDetailViewModel.performanceDetail.observe(viewLifecycleOwner) { performance ->
            performance?.let {
                    titleTextView.text = it.title
//                    contentTextView.text = it.content
//                    detailTextView.text = it.detail
                dateTextView.text = "${it.performanceDates?.first ?: "N/A"} - ${it.performanceDates?.second ?: "N/A"}"
                    categoryTextView.text = it.category
                    locationTextView.text = it.location
                    posterImageView.load(it.posterUrl)
//                    backdropImageView.load(it.backdropUrl)
//                    detailImageView.load(it.detail)

                    title.text = it.title
                    title.isSelected = true


            }
        }

        val performanceArgs = Bundle().apply {
            putString("key", args.title)
        }

        val topBar = view.findViewById<AppBarLayout>(R.id.topBar)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val adapter = FragmentViewPagerAdapter(this, performanceArgs)
        val scrollView = view.findViewById<NestedScrollView>(R.id.performanceDetailScrollView)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val customTab = LayoutInflater.from(context).inflate(R.layout.tab_customized, null)
            val tabTitle = customTab.findViewById<TextView>(R.id.tabTitle)
            tabTitle.text = when (position) {
                0 -> "공연정보"
                1 -> "판매정보"
                2 -> "관람후기"
                else -> null
            }
            tab.customView = customTab
        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                scrollView.post {
                    val tabLocation = IntArray(2)
                    tabLayout.getLocationOnScreen(tabLocation)
                    val tabTopY = tabLocation[1]

                    val scrollViewLocation = IntArray(2)
                    scrollView.getLocationOnScreen(scrollViewLocation)
                    val scrollViewTopY = scrollViewLocation[1]

                    // AppBarLayout의 높이 가져오기
                    val appBarHeight = topBar.height

                    // TabLayout이 화면 맨 위로 스크롤되되 AppBarLayout 높이만큼 오프셋 유지
                    val scrollOffset = tabTopY - scrollViewTopY - appBarHeight
                    scrollView.smoothScrollTo(0, scrollView.scrollY + scrollOffset)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })


        // 예매하기 버튼 동작 연결
        val bookButton = view.findViewById<Button>(R.id.bookButton)
        bookButton.setOnClickListener {
            val action = PerformanceDetailFragmentDirections
                .actionPerformanceDetailFragmentToCalendarFragment()
            findNavController().navigate(action)
        }

        val backButton = view.findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
