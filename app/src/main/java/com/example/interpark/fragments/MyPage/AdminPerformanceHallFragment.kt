package com.example.interpark.fragments.MyPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.interpark.R
import com.example.interpark.data.types.AdminPerformanceHallRequest
import com.example.interpark.databinding.FragmentAdminPerformanceHallBinding
import com.example.interpark.viewModels.AdminViewModel
import com.example.interpark.viewModels.AdminViewModelFactory

class AdminPerformanceHallFragment : Fragment() {

    private var _binding: FragmentAdminPerformanceHallBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AdminViewModel by viewModels { AdminViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminPerformanceHallBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.proceedButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val address = binding.addressEditText.text.toString()
            val hallRequest = AdminPerformanceHallRequest(
                name = name,
                address = address,
                maxAudience = 100
            )

            viewModel.createPerformanceHall(hallRequest) {
                //findNavController().navigate(R.id.action_performanceHallFragment_to_performanceEventFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
