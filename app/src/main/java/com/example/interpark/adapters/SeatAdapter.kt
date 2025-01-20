package com.example.interpark.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.interpark.R
import com.example.interpark.data.Seat

class SeatAdapter(
    private val seatList: List<Seat>,
    private val onSeatClick: (Seat) -> Unit
) : RecyclerView.Adapter<SeatAdapter.SeatViewHolder>() {

    inner class SeatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val seatImage: ImageView = itemView.findViewById(R.id.imageViewSeat)

        fun bind(seat: Seat) {
            seatImage.setImageResource(
                when {
                    seat.isSelected -> R.drawable.ic_seat_selected // 선택된 좌석 아이콘
                    seat.isAvailable -> R.drawable.ic_seat_available // 예약 가능 좌석 아이콘
                    else -> R.drawable.ic_seat_unavailable // 예약 불가 좌석 아이콘
                }
            )

            // 좌석 클릭 리스너
            itemView.setOnClickListener {
                if (seat.isAvailable) {
                    onSeatClick(seat) // 클릭 이벤트 전달
                } else {
                    Toast.makeText(itemView.context, "해당 좌석은 예약할 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_seat, parent, false)
        return SeatViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeatViewHolder, position: Int) {
        holder.bind(seatList[position])
    }

    override fun getItemCount() = seatList.size

    // 좌석 리스트 갱신 메서드
    fun updateSeats(newSeats: List<Seat>) {
        (seatList as MutableList).clear()
        seatList.addAll(newSeats)
        notifyDataSetChanged()
    }
}