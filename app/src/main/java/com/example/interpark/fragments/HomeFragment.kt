package com.example.interpark.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interpark.R
import com.example.interpark.adapters.HomeCategoryAdapter
import com.example.interpark.adapters.HomeCategoryRankCategory
import com.example.interpark.adapters.HomeCategoryRankCategoryListAdapter
import com.example.interpark.data.Category
import com.example.interpark.data.CategoryItem
import com.example.interpark.data.list_categories

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // RecyclerView 초기화
        val categoryRecyclerView: RecyclerView = view.findViewById(R.id.homeCategoryRecyclerView)
        categoryRecyclerView.layoutManager = GridLayoutManager(context, 4) // 2행 (4열)

        // 데이터 설정
        val categories = listOf(
            CategoryItem.Category("뮤지컬", R.drawable.ic_musical),
            CategoryItem.Category("콘서트", R.drawable.ic_concert),
            CategoryItem.Category("연극", R.drawable.ic_drama),
            CategoryItem.Category("클래식/무용", R.drawable.ic_classic),
            CategoryItem.Category("스포츠", R.drawable.ic_sports),
        )

        // 어댑터 설정
        val adapter = HomeCategoryAdapter(categories) { category ->
            Toast.makeText(context, "Clicked: ${category.name}", Toast.LENGTH_SHORT).show()
        }
        categoryRecyclerView.adapter = adapter

        val categoryRankCategoryListRecyclerView: RecyclerView = view.findViewById(R.id.homeCategoryRank_CategoryList)
        categoryRankCategoryListRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val homeCategoryRankCategories = list_categories.mapIndexed { index, category ->
            HomeCategoryRankCategory(
                category = category,
                selected = index == 0 // 첫 번째 항목만 true
            )
        }
        val categoryRankCategoryListAdapter = HomeCategoryRankCategoryListAdapter(homeCategoryRankCategories) { category ->
            categoryRankCategoryListClicked(category)
        }
        categoryRankCategoryListRecyclerView.adapter = categoryRankCategoryListAdapter
        return view
    }

    private fun categoryRankCategoryListClicked(category: Category){
        Toast.makeText(context, "Clicked: ${category.name}", Toast.LENGTH_SHORT).show()
    }
}
