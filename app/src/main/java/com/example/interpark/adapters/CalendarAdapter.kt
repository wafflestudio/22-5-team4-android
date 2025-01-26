package com.example.interpark.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.example.interpark.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CalendarAdapter(
    private var dates: List<Date?>,
    private var availableDates: Set<Date>,
    private val onDateClick: (Date) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    private var selectedDate: Date? = null // 선택된 날짜를 저장

    class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateText: TextView = itemView.findViewById(R.id.dateText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_day, parent, false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val date = dates[position]
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) // 날짜 포맷 설정

        if (date == null) {
            // 빈 칸 처리
            holder.dateText.text = ""
            holder.dateText.setBackgroundColor(Color.TRANSPARENT)
        } else {
            val formattedDate = dateFormat.format(date) // 날짜를 문자열로 변환
            holder.dateText.text = formattedDate.substring(8, 10) // 날짜만 표시 (ex: 09)

            if (date == selectedDate) {
                holder.dateText.setBackgroundResource(R.drawable.circle_purple_background) // 선택된 날짜 보라색 배경
                holder.dateText.setTextColor(Color.WHITE) // 흰색 텍스트
            } else if (availableDates.any { dateFormat.format(it) == formattedDate }) {
                holder.dateText.setBackgroundColor(Color.TRANSPARENT) // 선택 가능한 날짜: 기본 배경
                holder.dateText.setTextColor(Color.BLACK) // 검정 텍스트
            } else {
                holder.dateText.setBackgroundColor(Color.TRANSPARENT) // 선택 불가능한 날짜: 기본 배경
                holder.dateText.setTextColor(Color.GRAY) // 회색 텍스트
            }


            // 클릭 이벤트 처리
            holder.itemView.setOnClickListener {
                if (availableDates.any { dateFormat.format(it) == formattedDate }) {
                    selectedDate = date // 선택된 날짜 업데이트
                    notifyDataSetChanged() // UI 갱신
                    onDateClick(date) // 클릭 이벤트 전달
                }            }
        }
    }
    override fun getItemCount(): Int = dates.size

    fun updateDates(newDates: List<Date?>, newAvailableDates: Set<Date>) {
        dates = newDates
        availableDates = newAvailableDates
        notifyDataSetChanged()
    }

    fun isAvailableDate(date: Date): Boolean {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate = dateFormat.format(date) // 선택한 날짜를 문자열로 변환

        // availableDates의 모든 날짜를 문자열로 변환하여 비교
        return availableDates.any { dateFormat.format(it) == formattedDate }
    }

}
