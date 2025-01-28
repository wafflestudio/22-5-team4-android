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

class ReservedSeatAdapter(
    private var reservedSeats: List<MyReservation>,
    private val onDeleteClicked: (String) -> Unit
) : RecyclerView.Adapter<ReservedSeatAdapter.ReservationViewHolder>() {

    inner class ReservationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textTitle: TextView = view.findViewById(R.id.textPerformanceTitle)
        val textDate: TextView = view.findViewById(R.id.textPerformanceDate)
        val imagePoster: ImageView = view.findViewById(R.id.imagePoster)
        val buttonCancel: Button = view.findViewById(R.id.buttonCancel) // 취소 버튼 추가
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

        // Glide를 사용하여 이미지 로드
        Glide.with(holder.itemView.context).load(reservedSeat.posterUri).into(holder.imagePoster)

        // 취소 버튼 클릭 이벤트
        holder.buttonCancel.setOnClickListener {
            onDeleteClicked(reservedSeat.id) // 예약 ID를 전달하여 삭제 요청
        }
    }

    override fun getItemCount(): Int = reservedSeats.size

    fun updateData(newReservedSeats: List<MyReservation>) {
        reservedSeats = newReservedSeats
        notifyDataSetChanged()
    }
}
