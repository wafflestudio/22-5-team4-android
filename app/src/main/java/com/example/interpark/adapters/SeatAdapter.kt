package com.example.interpark.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.interpark.R
import com.example.interpark.data.Seat

class SeatAdapter(
    private val seatList: List<Seat>,
    private val onSeatClick: (Seat) -> Unit
) : RecyclerView.Adapter<SeatAdapter.SeatViewHolder>() {

    inner class SeatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val seatImage: ImageView = itemView.findViewById(R.id.imageViewSeat)
        private val seatNumber: TextView = itemView.findViewById(R.id.textViewSeatNumber)

        fun bind(seat: Seat) {
            seatNumber.text = seat.number.toString()
            seatImage.setImageResource(
                when {
                    seat.isSelected -> R.drawable.ic_seat_selected
                    seat.isAvailable -> R.drawable.ic_seat_available
                    else -> R.drawable.ic_seat_unavailable
                }
            )

            itemView.setOnClickListener {
                if (seat.isAvailable) {
                    onSeatClick(seat)
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
}
