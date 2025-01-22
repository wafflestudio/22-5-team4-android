package com.example.interpark.fragments.MyPage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interpark.R
import com.example.interpark.adapters.ReservedSeatAdapter
import com.example.interpark.data.ReservedSeat
import com.example.interpark.data.SeatDetail
import com.example.interpark.data.SeatNumber

class ReservedSeatListFragment : Fragment(R.layout.fragment_reserved_seat_list) {
    private lateinit var adapter: ReservedSeatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val reservedSeats = listOf(
            ReservedSeat(
                id = "1",
                performanceTitle = "뮤지컬 <여신님이 보고 계셔>",
                posterUri = "https://ticketimage.interpark.com/Play/image/large/P0/P0004046_p.gif",
                performanceHallName = "인터파크 유니플렉스 1관",
                seat = SeatDetail(
                    id = "1",
                    seatNumber = SeatNumber(first = 1, second = 2),
                    price = 50000
                ),
                performanceStartAt = "2025-01-24T16:00:00",
                performanceEndAt = "2025-01-24T18:00:00",
                reservationDate = "2025-01-17"
            ),
            ReservedSeat(
                id = "2",
                performanceTitle = "뮤지컬 <여신님이 보고 계셔>",
                posterUri = "https://ticketimage.interpark.com/Play/image/large/25/25000084_p.gif",
                performanceHallName = "인터파크 유니플렉스 1관",
                seat = SeatDetail(
                    id = "1",
                    seatNumber = SeatNumber(first = 1, second = 2),
                    price = 50000
                ),
                performanceStartAt = "2025-01-24T16:00:00",
                performanceEndAt = "2025-01-24T18:00:00",
                reservationDate = "2025-01-17"
            )

        )

        adapter = ReservedSeatAdapter(reservedSeats)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewReservedSeats)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }
}
