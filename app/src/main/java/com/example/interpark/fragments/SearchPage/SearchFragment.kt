package com.example.interpark.fragments.SearchPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.interpark.R

class SearchFragment : Fragment() {

    private lateinit var editTextSearch: EditText
    private lateinit var buttonSearch: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 레이아웃을 인플레이트하고 뷰 반환
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        // 뷰 초기화
        editTextSearch = view.findViewById(R.id.editTextSearch)
        buttonSearch = view.findViewById(R.id.buttonSearch)

        // 버튼 클릭 리스너 설정
        buttonSearch.setOnClickListener {
            val query = editTextSearch.text.toString().trim()
            if (query.isNotEmpty()) {
                // NavController를 사용하여 화면 전환
                val action = SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(query)
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "검색어를 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
