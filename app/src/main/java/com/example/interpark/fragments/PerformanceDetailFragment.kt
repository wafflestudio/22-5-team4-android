package com.example.interpark.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.interpark.R
import com.example.interpark.adapters.FragmentViewPagerAdapter
import com.example.interpark.databinding.FragmentPerformanceDetailBinding
import com.example.interpark.viewModels.PerformanceDetailViewModel
import com.example.interpark.viewModels.PerformanceDetailViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.w3c.dom.Text

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel을 통해 공연 상세 정보 가져오기
        performanceDetailViewModel.fetchPerformanceDetail(args.title)

        val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
        val contentTextView = view.findViewById<TextView>(R.id.contentTextView)
        val detailTextView = view.findViewById<TextView>(R.id.detailTextView)
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
                    detailTextView.text = it.detail
                    dateTextView.text = it.date.joinToString { date -> date.toString() }
                    categoryTextView.text = it.category
                    locationTextView.text = it.location
                    posterImageView.load(it.posterUrl)
//                    backdropImageView.load(it.backdropUrl)
//                    detailImageView.load(it.detail)

//                    // 포스터 이미지 로드 (Glide 사용, 플레이스홀더 추가)
//                    Glide.with(this@PerformanceDetailFragment)
                    title.text = it.title
                    title.isSelected = true
                    // 포스터 이미지 로드 (Glide 사용, 플레이스홀더 추가)
                    Glide.with(this@PerformanceDetailFragment)
//                        .load(it.posterUrl)
                        .load(R.drawable.performance3)
                        .placeholder(R.drawable.performance3) // 로드 중 보여줄 이미지
                        .error(R.drawable.performance3)       // 오류 시 보여줄 이미지
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(posterImageView)

            }
        }

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
//        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val adapter = FragmentViewPagerAdapter(this)
        viewPager.adapter = adapter
//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            tab.text = when (position) {
//                0 -> "Tab 1"
//                1 -> "Tab 2"
//                2 -> "Tab 3"
//                else -> null
//            }
//        }.attach()

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
