package com.example.interpark.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.interpark.R

class EmptyFragment : Fragment() {
    override fun onCreateView(
        inflater:
        LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 빈 레이아웃을 반환
        return inflater.inflate(R.layout.fragment_empty, container, false)
    }
}