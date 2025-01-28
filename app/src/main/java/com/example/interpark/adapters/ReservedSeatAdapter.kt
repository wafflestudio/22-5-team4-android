package com.example.interpark.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.interpark.R
import com.example.interpark.data.MyReservation
import com.example.interpark.viewModels.SeatSelectionViewModel
import org.w3c.dom.Text

class ReservedSeatAdapter(private var reservedSeats: List<MyReservation>,
    private val seatSelectionViewModel: SeatSelectionViewModel) :
    RecyclerView.Adapter<ReservedSeatAdapter.ReservationViewHolder>() {

    inner class ReservationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val reservationId: TextView = view.findViewById(R.id.reservationId)
        val textTitle: TextView = view.findViewById(R.id.textPerformanceTitle)
        val textDate: TextView = view.findViewById(R.id.textPerformanceDate)
        val imagePoster: ImageView = view.findViewById(R.id.imagePoster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reserved_seat, parent, false)
        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val reservedSeat = reservedSeats[position]
        holder.textTitle.text = reservedSeat.performanceTitle
        holder.textDate.text = reservedSeat.performanceDate
        holder.reservationId.text = reservedSeat.id

        holder.itemView.findViewById<Button>(R.id.buttonCancel).setOnClickListener {
            seatSelectionViewModel.cancelReservation(reservedSeat.id)
        }
        // Glide를 사용하여 이미지 로드
        Glide.with(holder.itemView.context).load(reservedSeat.posterUri).into(holder.imagePoster)
    }

    override fun getItemCount(): Int = reservedSeats.size

    fun updateData(newReservedSeats: List<MyReservation>) {
        reservedSeats = newReservedSeats
        notifyDataSetChanged()
    }
}
