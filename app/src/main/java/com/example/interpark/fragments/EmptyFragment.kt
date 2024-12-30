package com.example.interpark.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.interpark.R


class EmptyFragment : Fragment() {

    private lateinit var categoryTitleTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_empty, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TextView 초기화
        categoryTitleTextView = view.findViewById(R.id.categoryTitleTextView)

        // 전달받은 카테고리 데이터 가져오기
        val category = arguments?.getString("category") ?: "카테고리 없음"
        categoryTitleTextView.text = category
    }
}
