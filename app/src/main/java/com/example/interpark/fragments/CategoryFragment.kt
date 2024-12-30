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
import com.example.interpark.data.CategoryItem

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

        // RecyclerView 설정
        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView)

        // GridLayoutManager로 레이아웃 설정 (2열)
        val gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (categoryAdapter.getItemViewType(position)) {
                    CategoryAdapter.TYPE_CATEGORY -> 1 // Category는 1칸 사용 (2열)
                    CategoryAdapter.TYPE_FOOTER -> 2 // Footer는 전체 사용 (1열)
                    else -> 1
                }
            }
        }
        categoryRecyclerView.layoutManager = gridLayoutManager

        // 데이터 생성
        val items = listOf(
            // 카테고리 항목
            CategoryItem.Category("뮤지컬", R.drawable.ic_musical),
            CategoryItem.Category("콘서트", R.drawable.ic_concert),
            CategoryItem.Category("연극", R.drawable.ic_drama),
            CategoryItem.Category("클래식/무용", R.drawable.ic_classic),
            CategoryItem.Category("스포츠", R.drawable.ic_sports),

            // Footer 항목
            CategoryItem.FooterItem("랭킹", R.drawable.ic_chevron_right),
            CategoryItem.FooterItem("티켓오픈", R.drawable.ic_chevron_right),
            CategoryItem.FooterItem("이벤트", R.drawable.ic_chevron_right)
        )

        // 어댑터 설정
        categoryAdapter = CategoryAdapter(items) { category ->
            onCategoryClick(category)
        }
        categoryRecyclerView.adapter = categoryAdapter
    }

    private fun onCategoryClick(category: CategoryItem.Category) {
        // RecyclerView 숨기기
        categoryRecyclerView.visibility = View.GONE

        // 새로운 Fragment 추가
        val fragment = EmptyFragment()

        // 선택된 카테고리 이름 전달
        fragment.arguments = Bundle().apply {
            putString("category", category.name)
        }

        childFragmentManager.beginTransaction()
            .replace(R.id.categoryFragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}
