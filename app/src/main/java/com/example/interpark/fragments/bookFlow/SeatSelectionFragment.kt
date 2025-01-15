package com.example.interpark.fragments.bookFlow

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interpark.R
import com.example.interpark.adapters.SeatAdapter
import com.example.interpark.data.types.Seat
import com.example.interpark.databinding.FragmentSeatSelectionBinding

class SeatSelectionFragment : Fragment(R.layout.fragment_seat_selection) {
    private var _binding: FragmentSeatSelectionBinding? = null
    private val binding get() = _binding!!
    private val args: SeatSelectionFragmentArgs by navArgs()
    private lateinit var recyclerViewSeats: RecyclerView
    private lateinit var seatAdapter: SeatAdapter
    private val seatList = mutableListOf<Seat>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSeatSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewSeats = view.findViewById(R.id.recyclerViewSeats)

        // 좌석 데이터 초기화
        initializeSeats()

        seatAdapter = SeatAdapter(seatList) { seat ->
            seat.isSelected = !seat.isSelected
            showSeatPopup(seat) // 좌석 클릭 시 팝업 띄우기
            seatAdapter.notifyDataSetChanged()
        }

        // 뒤로가기 버튼 동작 연결
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        recyclerViewSeats.layoutManager = GridLayoutManager(requireContext(), 10) // 열 수를 조정
        recyclerViewSeats.adapter = seatAdapter
    }

    private fun initializeSeats() {
        val rows = ('A'..'J') // A부터 J까지 10개의 열
        for (row in rows) {
            for (number in 1..10) { // 각 열에 10개의 좌석
                seatList.add(Seat(row.toString(), number))
            }
        }
    }

    private fun showSeatPopup(seat: Seat) {
        val message = "${seat.row}열 ${seat.number}번\n가격: 10,000원"
        AlertDialog.Builder(requireContext())
            .setTitle("좌석 정보")
            .setMessage(message)
            .setPositiveButton("확인", null)
            .show()
    }


}
