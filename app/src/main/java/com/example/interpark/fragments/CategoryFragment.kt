package com.example.interpark.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.interpark.R
import com.example.interpark.adapters.CategoryAdapter
import com.example.interpark.data.CategoryItem
import com.example.interpark.databinding.FragmentCategoryBinding


class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView 설정
        setupRecyclerView()


        // 데이터 생성 및 어댑터 설정
        val items = getCategoryItems()
        categoryAdapter = CategoryAdapter(items) { category ->
            val navController = requireActivity().findNavController(R.id.categoryNavHost)
            val action = CategoryFragmentDirections
                .actionCategoryFragmentToEmptyFragment(category.name)
            navController.navigate(action)
        }
        binding.categoryRecyclerView.adapter = categoryAdapter
    }

    private fun setupRecyclerView() {
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
        binding.categoryRecyclerView.layoutManager = gridLayoutManager
    }

    private fun getCategoryItems(): List<CategoryItem> {
        return listOf(
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
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
