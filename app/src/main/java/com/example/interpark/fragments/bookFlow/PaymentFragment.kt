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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.interpark.R
import com.example.interpark.data.types.RequestData

import com.example.interpark.data.types.ReservationRequest
import com.example.interpark.databinding.FragmentPaymentBinding
import com.example.interpark.databinding.FragmentSeatSelectionBinding
import com.example.interpark.viewModels.SeatSelectionViewModel
import com.example.interpark.viewModels.SeatSelectionViewModelFactory

class PaymentFragment : Fragment(R.layout.fragment_payment) {
    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!

    private val args: PaymentFragmentArgs by navArgs()
    private val paymentViewModel: SeatSelectionViewModel by viewModels {
        SeatSelectionViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 전달받은 좌석 정보 출력
        val eventId = args.eventId
        val seatId = args.seatId
        val selectedSeat = args.selectedSeat
        // 로그 출력
        Log.d("PaymentFragment", "Event ID: $eventId")
        Log.d("PaymentFragment", "Seat ID: $seatId")
        Log.d("PaymentFragment", "Selected Seat: $selectedSeat")
        // UI에 선택한 좌석 표시
        val seatTextView: TextView = view.findViewById(R.id.selectedSeatsTextView)
        seatTextView.text = "선택한 좌석: $selectedSeat"


        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // 결제 버튼 클릭 이벤트
        view.findViewById<View>(R.id.payButton).setOnClickListener {
            if (eventId.isNotEmpty() && seatId.isNotEmpty()) {
                // 예약 요청
                paymentViewModel.reserveSeat(
                    eventId = eventId,
                    seatId = seatId,
                    onSuccess = { reservationId ->
                        Toast.makeText(requireContext(), "예약 성공!", Toast.LENGTH_SHORT).show()
                        // 성공 시 다음 작업 (예: 완료 화면 이동)
                    },
                    onFailure = { errorMessage ->
                        Toast.makeText(requireContext(), "로그인을 먼저 해주세요!", Toast.LENGTH_SHORT).show()
                    }
                )
                val action = PaymentFragmentDirections.actionPaymentFragmentToHomeFragment()
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "잘못된 요청입니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
