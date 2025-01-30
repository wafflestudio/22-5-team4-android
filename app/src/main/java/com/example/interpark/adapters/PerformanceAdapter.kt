package com.example.interpark.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.interpark.R
import com.example.interpark.data.types.Performance

class PerformanceAdapter(
    private val performances: List<Performance>,
    private val onClick: (Performance) -> Unit
) : RecyclerView.Adapter<PerformanceAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.performanceTitle)
        private val location: TextView = itemView.findViewById(R.id.performanceLocation)
        private val dateRange: TextView = itemView.findViewById(R.id.performanceDateRange)
        private val imageView: ImageView = itemView.findViewById(R.id.performanceImage)

        fun bind(performance: Performance, onClick: (Performance) -> Unit) {
            title.text = performance.title
            location.text = performance.location
            dateRange.text = performance.getFormattedDate() // ✅ 안전한 날짜 변환
            imageView.load(performance.posterUrl)
            itemView.setOnClickListener {
                onClick(performance)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_performance, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(performances[position], onClick)
    }

    override fun getItemCount(): Int = performances.size
}
//package com.example.interpark.adapters
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.RecyclerView
//import coil.load
//import com.example.interpark.R
//import com.example.interpark.data.types.Performance
//import androidx.paging.PagingDataAdapter
//
//class PerformanceAdapter(
//    private val onClick: (Performance) -> Unit
//) : PagingDataAdapter<Performance, PerformanceAdapter.ViewHolder>(PerformanceDiffCallback()) {
//
//    class ViewHolder(itemView: ViewGroup) : RecyclerView.ViewHolder(
//        LayoutInflater.from(itemView.context).inflate(R.layout.item_performance, itemView, false)
//    ) {
//        private val title: TextView = itemView.findViewById(R.id.performanceTitle)
//        private val location: TextView = itemView.findViewById(R.id.performanceLocation)
//        private val dateRange: TextView = itemView.findViewById(R.id.performanceDateRange)
//        private val imageView: ImageView = itemView.findViewById(R.id.performanceImage)
//
//        fun bind(performance: Performance, onClick: (Performance) -> Unit) {
//            title.text = performance.title
//            location.text = performance.location
//            dateRange.text = performance.getFormattedDate() // ✅ 날짜 변환
//            imageView.load(performance.posterUrl)
//
//            itemView.setOnClickListener {
//                onClick(performance)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(parent)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        getItem(position)?.let { holder.bind(it, onClick) }
//    }
//}
//
//// ✅ DiffUtil을 사용하여 리스트 변경 감지 최적화
//class PerformanceDiffCallback : DiffUtil.ItemCallback<Performance>() {
//    override fun areItemsTheSame(oldItem: Performance, newItem: Performance): Boolean {
//        return oldItem.id == newItem.id
//    }
//
//    override fun areContentsTheSame(oldItem: Performance, newItem: Performance): Boolean {
//        return oldItem == newItem
//    }
//}
