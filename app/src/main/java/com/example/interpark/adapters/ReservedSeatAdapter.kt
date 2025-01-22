package com.example.interpark.adapters

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.interpark.R
import com.example.interpark.data.ReservedSeat

class ReservedSeatAdapter(private val reservedSeats: List<ReservedSeat>) :
    RecyclerView.Adapter<ReservedSeatAdapter.ReservedSeatViewHolder>() {

    inner class ReservedSeatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imagePoster: ImageView = view.findViewById(R.id.imagePoster)
        val textPerformanceTitle: TextView = view.findViewById(R.id.textPerformanceTitle)
        val textPerformanceHallName: TextView = view.findViewById(R.id.textPerformanceHallName)
        val textPerformanceDate: TextView = view.findViewById(R.id.textPerformanceDate)
        val textSeatInfo: TextView = view.findViewById(R.id.textSeatInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservedSeatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reserved_seat, parent, false)
        return ReservedSeatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservedSeatViewHolder, position: Int) {
        val reservedSeat = reservedSeats[position]
        holder.textPerformanceTitle.text = reservedSeat.performanceTitle
        holder.textPerformanceHallName.text = "장소: ${reservedSeat.performanceHallName}"
        holder.textPerformanceDate.text = "관람일시: ${reservedSeat.performanceStartAt}"
        holder.textSeatInfo.text =
            "좌석: ${reservedSeat.seat.seatNumber.first}열 ${reservedSeat.seat.seatNumber.second}번"

        // Glide를 사용하여 포스터 이미지 로드
        Glide.with(holder.itemView.context).load(reservedSeat.posterUri).into(holder.imagePoster)
    }

    override fun getItemCount(): Int = reservedSeats.size
}
