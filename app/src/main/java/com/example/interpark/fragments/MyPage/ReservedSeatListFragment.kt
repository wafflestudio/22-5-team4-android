package com.example.interpark.fragments.MyPage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interpark.R
import com.example.interpark.adapters.ReservedSeatAdapter
import com.example.interpark.data.ReservedSeat
import com.example.interpark.viewModels.SeatSelectionViewModel
import com.example.interpark.viewModels.SeatSelectionViewModelFactory

class ReservedSeatListFragment : Fragment(R.layout.fragment_reserved_seat_list) {

    private lateinit var adapter: ReservedSeatAdapter
    private val reservedSeatViewModel: SeatSelectionViewModel by viewModels {
        SeatSelectionViewModelFactory(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView 설정
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewReservedSeats)
        adapter = ReservedSeatAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // ViewModel에서 데이터 가져오기
        reservedSeatViewModel.fetchReservations()

        // 데이터 관찰 및 UI 업데이트
        reservedSeatViewModel.reservations.observe(viewLifecycleOwner) { reservedSeats ->
            adapter.updateData(reservedSeats)
        }
    }
}
