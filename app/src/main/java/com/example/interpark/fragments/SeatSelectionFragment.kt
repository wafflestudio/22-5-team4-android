package com.example.interpark.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interpark.R
import com.example.interpark.adapters.SeatAdapter
import com.example.interpark.data.Seat

class SeatSelectionFragment : Fragment() {

    private lateinit var recyclerViewSeats: RecyclerView
    private lateinit var seatAdapter: SeatAdapter
    private val seatList = mutableListOf<Seat>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_seat_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewSeats = view.findViewById(R.id.recyclerViewSeats)

        // 좌석 데이터 초기화
        initializeSeats()

        seatAdapter = SeatAdapter(seatList) { seat ->
            seat.isSelected = !seat.isSelected
            seatAdapter.notifyDataSetChanged()
        }

        recyclerViewSeats.layoutManager = GridLayoutManager(requireContext(), 10) // 열 수를 조정
        recyclerViewSeats.adapter = seatAdapter
    }

    private fun initializeSeats() {
        val rows = listOf("A", "B", "C", "D")
        for (row in rows) {
            for (number in 1..20) {
                seatList.add(Seat(row, number))
            }
        }
    }
}
