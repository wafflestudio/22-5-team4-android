package com.example.interpark.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.interpark.fragments.CategoryFragment
import com.example.interpark.fragments.CategoryFragmentHome
import com.example.interpark.fragments.HomeFragment
import com.example.interpark.fragments.HomeFragmentHome
import com.example.interpark.fragments.MyFragment
import com.example.interpark.fragments.MyFragmentHome
import com.example.interpark.fragments.SearchFragment
import com.example.interpark.fragments.SearchFragmentHome
import com.example.interpark.fragments.detailFragments.PerformanceInformation
import com.example.interpark.fragments.detailFragments.Reviews
import com.example.interpark.fragments.detailFragments.SalesInformation

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
