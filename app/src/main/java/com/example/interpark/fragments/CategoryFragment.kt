package com.example.interpark.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interpark.R
import com.example.interpark.adapters.CategoryAdapter
import com.example.interpark.data.Category

class CategoryFragment : Fragment() {

    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView)
        categoryRecyclerView.layoutManager = GridLayoutManager(context, 2)

        // 목 데이터
        val categories = listOf(
            Category("뮤지컬", R.drawable.ic_musical),
            Category("콘서트", R.drawable.ic_concert),
            Category("연극", R.drawable.ic_drama),
            Category("클래식/무용", R.drawable.ic_classic),
            Category("스포츠", R.drawable.ic_sports)
        )

        categoryAdapter = CategoryAdapter(categories) { category ->
            onCategoryClick(category)
        }
        categoryRecyclerView.adapter = categoryAdapter
    }

    private fun onCategoryClick(category: Category) {
        // 클릭된 카테고리에 따른 처리
    }
}
