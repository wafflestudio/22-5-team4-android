package com.example.interpark.fragments.MyPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.interpark.adapters.ReservedSeatAdapter
import com.example.interpark.databinding.FragmentReservedSeatListBinding
import com.example.interpark.viewModels.SeatSelectionViewModel
import com.example.interpark.viewModels.SeatSelectionViewModelFactory

class ReservedSeatListFragment : Fragment() {

    // View Binding
    private var _binding: FragmentReservedSeatListBinding? = null
    private val binding get() = _binding!!

    // Adapter
    private lateinit var adapter: ReservedSeatAdapter

    // ViewModel
    private val seatSelectionViewModel: SeatSelectionViewModel by viewModels {
        SeatSelectionViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservedSeatListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter = ReservedSeatAdapter(
            reservedSeats = emptyList(),
            onDeleteClicked = { reservationId -> cancelReservation(reservationId) } // 삭제 기능 추가
        )


        binding.recyclerViewReservedSeats.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewReservedSeats.adapter = adapter
        binding.emptyText.visibility = View.VISIBLE
        binding.recyclerViewReservedSeats.visibility = View.GONE

        // 데이터 관찰 및 UI 업데이트
        seatSelectionViewModel.reservations.observe(viewLifecycleOwner) { reservedSeats ->
            adapter.updateData(reservedSeats)
            binding.emptyText.visibility = when(reservedSeats.isEmpty()){
                true -> View.VISIBLE
                else -> View.GONE
            }
            binding.recyclerViewReservedSeats.visibility = when(reservedSeats.isEmpty()){
                true -> View.GONE
                else -> View.VISIBLE
            }

        }

        // ViewModel에서 데이터 가져오기
        seatSelectionViewModel.fetchReservations()

        // 뒤로가기 버튼 클릭 이벤트
        binding.backArrow.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun cancelReservation(reservationId: String) {
        seatSelectionViewModel.deleteReservation(reservationId) { success ->
            if (success) {
                Toast.makeText(requireContext(), "예약이 취소되었습니다.", Toast.LENGTH_SHORT).show()
                seatSelectionViewModel.fetchReservations() // 최신 데이터 가져오기
            } else {
                Toast.makeText(requireContext(), "예약 취소 실패. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
