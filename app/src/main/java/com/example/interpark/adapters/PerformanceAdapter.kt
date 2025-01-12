package com.example.interpark.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.interpark.R
import com.example.interpark.data.Performance

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
//            location.text = performance.location
//            dateRange.text = "${performance.date.first()} - ${performance.date.last()}"

            imageView.load(performance.posterUrl)
            itemView.setOnClickListener{
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
