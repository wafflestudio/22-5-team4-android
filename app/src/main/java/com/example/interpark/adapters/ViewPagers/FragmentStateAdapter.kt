package com.example.interpark.adapters.ViewPagers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.interpark.fragments.CategoryPage.CategoryFragmentHome
import com.example.interpark.fragments.HomeFragmentHome
import com.example.interpark.fragments.MyPage.MyFragmentHome
import com.example.interpark.fragments.SearchPage.SearchFragmentHome

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private val fragments = listOf(
        HomeFragmentHome(),
        CategoryFragmentHome(),
        SearchFragmentHome(),
        MyFragmentHome()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
