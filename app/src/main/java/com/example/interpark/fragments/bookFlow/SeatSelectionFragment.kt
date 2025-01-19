package com.example.interpark.fragments.bookFlow

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.interpark.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.interpark.adapters.SeatAdapter
import com.example.interpark.data.Seat

import com.example.interpark.data.SeatRequest
import com.example.interpark.data.SeatResponse
import com.example.interpark.databinding.FragmentSeatSelectionBinding
import com.example.interpark.fragments.bookFlow.SeatSelectionFragmentArgs
import com.example.interpark.viewModels.SeatSelectionViewModel
import com.example.interpark.viewModels.SeatSelectionViewModelFactory

class SeatSelectionFragment : Fragment(R.layout.fragment_seat_selection) {
    private var _binding: FragmentSeatSelectionBinding? = null
    private val binding get() = _binding!!
    private lateinit var seatAdapter: SeatAdapter
    private val seatList = mutableListOf<Seat>() // 좌석 리스트 선언
    private val args: SeatSelectionFragmentArgs by navArgs()
    private val viewModel: SeatSelectionViewModel by viewModels {
        SeatSelectionViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSeatSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            Log.d("seat", "herehere")
            findNavController().navigateUp()
        }

        binding.titleText.setOnClickListener {
            Log.d("title", "herehere")
            findNavController().navigateUp()
        }
        binding.proceedToPaymentButton.setOnClickListener {
            val selectedSeats = seatList
                .filter { it.isSelected }
                .map { "Row: ${it.row}, Column: ${it.number}" }

            if (selectedSeats.isEmpty()) {
//                Toast.makeText(requireContext(), "좌석을 선택해주세요.", Toast.LENGTH_SHORT).show()
                val action = SeatSelectionFragmentDirections
                    .actionSeatSelectionFragmentToPaymentFragment(selectedSeats.toTypedArray(), args.title)
                findNavController().navigate(action)
            } else {
                val action = SeatSelectionFragmentDirections
                    .actionSeatSelectionFragmentToPaymentFragment(selectedSeats.toTypedArray(), args.title)
                findNavController().navigate(action)
            }
        }


        // 좌석 초기화
        initializeSeats()

        // SeatAdapter 초기화
        seatAdapter = SeatAdapter(seatList) { seat ->
            if (seat.isAvailable) {
                seat.isSelected = !seat.isSelected
                seatAdapter.notifyDataSetChanged() // UI 갱신
                val seatRequest = SeatRequest(seat.row, seat.number)
                viewModel.reserveSeat(seatRequest)
            } else {
                Toast.makeText(requireContext(), "해당 좌석은 예약할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        // RecyclerView 설정
        binding.recyclerViewSeats.apply {
            layoutManager = GridLayoutManager(requireContext(), 10) // 10x10 그리드
            adapter = seatAdapter
        }

        // 서버에서 좌석 데이터 가져오기
        val eventId = args.title
        viewModel.fetchAvailableSeats(eventId)

        // 좌석 데이터 관찰 및 UI 업데이트
        viewModel.seats.observe(viewLifecycleOwner) { seats ->
            val availableSeatsSet = seats.map { it.row to it.number }.toSet() // row와 number 쌍으로 Set 생성

            seatList.forEach { localSeat ->
                localSeat.isAvailable = (localSeat.row to localSeat.number) in availableSeatsSet
            }

            seatAdapter.notifyDataSetChanged()
        }


        // 예약 결과 관찰 및 UI 처리
        viewModel.reservationResult.observe(viewLifecycleOwner) { result ->
            showReservationResult(result)
        }
    }

    private fun initializeSeats() {
        val totalRows = 10
        val totalColumns = 10
        for (row in 1..totalRows) {
            for (column in 1..totalColumns) {
                seatList.add(Seat(row, column, isAvailable = true)) // 초기 상태는 모두 available
            }
        }
    }

    private fun showReservationResult(response: SeatResponse) {
        AlertDialog.Builder(requireContext())
            .setTitle("예약 결과")
            .setMessage("좌석 예약이 완료되었습니다.")
            .setPositiveButton("확인", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}