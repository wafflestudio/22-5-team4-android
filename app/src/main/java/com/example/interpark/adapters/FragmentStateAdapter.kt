package com.example.interpark.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.interpark.fragments.CategoryFragment
import com.example.interpark.fragments.CategoryFragmentHome
import com.example.interpark.fragments.HomeFragment
import com.example.interpark.fragments.MyFragment
import com.example.interpark.fragments.MyFragmentHome
import com.example.interpark.fragments.SearchFragment
import com.example.interpark.fragments.SearchFragmentHome

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private val fragments = listOf(
        HomeFragment(),
        CategoryFragmentHome(),
        SearchFragmentHome(),
        MyFragmentHome()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
