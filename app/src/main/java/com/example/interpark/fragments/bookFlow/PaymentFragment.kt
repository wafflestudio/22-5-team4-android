package com.example.interpark.fragments.bookFlow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.interpark.R
import com.example.interpark.data.types.RequestData

import com.example.interpark.data.types.ReservationRequest
import com.example.interpark.viewModels.SeatSelectionViewModel
import com.example.interpark.viewModels.SeatSelectionViewModelFactory

class PaymentFragment : Fragment(R.layout.fragment_payment) {
    private val args: PaymentFragmentArgs by navArgs()
    private val paymentViewModel: SeatSelectionViewModel by viewModels { SeatSelectionViewModelFactory(requireContext()) }

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

        // 결제 버튼 클릭 이벤트
        view.findViewById<View>(R.id.payButton).setOnClickListener {
            val reservationRequest = ReservationRequest(
                reservationId = args.title

            )


            paymentViewModel.reserveSeat(reservationRequest)
        }

        // 예약 성공/실패 처리
        paymentViewModel.reservationSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "예약 성공!", Toast.LENGTH_SHORT).show()
                // 예약 성공 시 다음 화면으로 이동
            }
        }

        paymentViewModel.reservationFailed.observe(viewLifecycleOwner) { failed ->
            if (failed) {
                Toast.makeText(requireContext(), "예약 실패. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }



}
