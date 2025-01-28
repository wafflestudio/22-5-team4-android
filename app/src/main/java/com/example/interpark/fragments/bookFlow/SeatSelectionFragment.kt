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
import com.example.interpark.auth.AuthManager
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
    private lateinit var reserveId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeatSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AuthManager.initialize(requireContext())

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.titleText.setOnClickListener {
            Log.d("title", "herehere")
            findNavController().navigateUp()
        }

        binding.proceedToPaymentButton.setOnClickListener {
            val selectedSeat = seatList.find { it.isSelected }
            if (selectedSeat == null) {
                Toast.makeText(requireContext(), "좌석을 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val seatId = selectedSeat.id // 선택된 좌석의 ID 가져오기
            val eventId = args.title // 이벤트 ID 가져오기

            if (seatId.isNullOrEmpty()) {
                Log.e("SeatSelectionFragment", "Seat ID is null or empty")
                Toast.makeText(requireContext(), "좌석 ID가 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("SeatSelectionFragment", "Selected Seat ID: $seatId, Event ID: $eventId")

            val action = SeatSelectionFragmentDirections
                .actionSeatSelectionFragmentToPaymentFragment(
                    eventId = eventId,
                    seatId = seatId,
                    selectedSeat = "Row: ${selectedSeat.row}, Column: ${selectedSeat.number}"
                )
            findNavController().navigate(action)
        }




        // 좌석 초기화
        initializeSeats()

        // SeatAdapter 초기화
        seatAdapter = SeatAdapter(seatList) { selectedSeat ->
            if (selectedSeat.isAvailable) {
                // 모든 좌석 선택 해제
                seatList.forEach { it.isSelected = false }

                // 선택한 좌석만 활성화
                selectedSeat.isSelected = true
                seatAdapter.notifyDataSetChanged()
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

        viewModel.reservationId.observe(viewLifecycleOwner) { reservationId ->
            Log.d("ReservationIdObserver", "Observed reservationId: $reservationId")
            reserveId = reservationId[0]
        }


        // 좌석 데이터 관찰 및 UI 업데이트
        viewModel.seats.observe(viewLifecycleOwner) { seats ->
            seatList.forEach { localSeat ->
                val match = seats.find { it.row == localSeat.row && it.number == localSeat.number }
                if (match != null) {
                    Log.d("SeatSelectionFragment", "Match found: ID=${match.id}")
                    localSeat.id = match.id // Ensure ID is copied
                    localSeat.isAvailable = match.isAvailable
                } else {
                    localSeat.isAvailable = false
                }
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
                seatList.add(Seat("",row, column, isAvailable = true)) // 초기 상태는 모두 available
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