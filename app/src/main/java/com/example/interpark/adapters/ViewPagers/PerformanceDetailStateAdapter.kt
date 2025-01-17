package com.example.interpark.adapters.ViewPagers

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.interpark.fragments.PerformanceDetail.PerformanceInformation
import com.example.interpark.fragments.PerformanceDetail.Reviews
import com.example.interpark.fragments.PerformanceDetail.SalesInformation

class FragmentViewPagerAdapter(fragment: Fragment, perfId: Bundle) : FragmentStateAdapter(fragment) {
    private val fragments = listOf(
        PerformanceInformation().apply{
            arguments = perfId
        },
        SalesInformation().apply{
            arguments = perfId
        },
        Reviews().apply{
            arguments = perfId
        }
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
