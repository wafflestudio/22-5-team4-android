package com.example.interpark.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.interpark.R
import androidx.navigation.fragment.navArgs
import com.example.interpark.databinding.FragmentPerformanceDetailBinding

class PerformanceDetailFragment : Fragment(R.layout.fragment_performance_detail) {
    private var _binding: FragmentPerformanceDetailBinding? = null
    private val binding get() = _binding!!
    private val args:  PerformanceDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPerformanceDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        titleTextView.text = args.title

        val titleView: TextView = view.findViewById(R.id.title)
        titleView.text = args.title

        // 예매하기 버튼 동작 연결
        val bookButton: Button = view.findViewById(R.id.bookButton)
        binding.bookButton.setOnClickListener {
            val action = PerformanceDetailFragmentDirections
                .actionPerformanceDetailFragmentToCalendarFragment()
            findNavController().navigate(action)
        }

        // 뒤로가기 버튼 동작 연결
        val backButton: ImageView = view.findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val navController = requireActivity().findNavController(R.id.categoryNavHost)
            navController.navigateUp() // 이전 화면으로 이동
        }

//        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
//        setupToolbar(toolbar)

    }

//    private fun setupToolbar(toolbar: Toolbar) {
//        (activity as AppCompatActivity).setSupportActionBar(toolbar)
//        toolbar.title = ""
//        toolbar.setLogo(R.drawable.main_logo)
//        toolbar.setNavigationIcon(R.drawable.ic_back)
//        toolbar.setNavigationOnClickListener {
//            findNavController().popBackStack()
//        }
//
//    }
}