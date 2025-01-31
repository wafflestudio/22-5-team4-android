package com.example.interpark.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.interpark.R
import com.example.interpark.data.types.Performance
import androidx.paging.PagingDataAdapter

class PerformanceAdapter(
    private val onClick: (Performance) -> Unit
) : PagingDataAdapter<Performance, PerformanceAdapter.ViewHolder>(PerformanceDiffCallback()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.performanceTitle)
        private val location: TextView = view.findViewById(R.id.performanceLocation)
        private val dateRange: TextView = view.findViewById(R.id.performanceDateRange)
        private val imageView: ImageView = view.findViewById(R.id.performanceImage)

        fun bind(performance: Performance, onClick: (Performance) -> Unit) {
            title.text = performance.title
            location.text = performance.location
            dateRange.text = performance.getFormattedDate()
            imageView.load(performance.posterUrl)

            itemView.setOnClickListener {
                onClick(performance)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_performance, parent, false)
        return ViewHolder(view) // ✅ 뷰를 생성자에 전달
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, onClick) }
    }
}


class PerformanceDiffCallback : DiffUtil.ItemCallback<Performance>() {
    override fun areItemsTheSame(oldItem: Performance, newItem: Performance): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Performance, newItem: Performance): Boolean {
        return oldItem == newItem
    }
}
