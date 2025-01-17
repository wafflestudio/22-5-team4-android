package com.example.interpark.fragments.MyPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.interpark.R

class MyFragmentAccount : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // fragment_my_account.xml을 인플레이트하여 반환
        return inflater.inflate(R.layout.fragment_my_account, container, false)
    }
}