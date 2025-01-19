package com.example.interpark.fragments.bookFlow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.interpark.R

class PaymentFragment : Fragment(R.layout.fragment_payment) {
    private val args: PaymentFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 전달받은 좌석 리스트 출력
        val selectedSeats = args.selectedSeats
        selectedSeats.forEach { seat ->
            Log.d("PaymentFragment", "Selected Seat: $seat")
        }

        // UI에 선택한 좌석 표시
        val seatTextView: TextView = view.findViewById(R.id.selectedSeatsTextView)
        seatTextView.text = selectedSeats.joinToString(separator = "\n")
    }
}
