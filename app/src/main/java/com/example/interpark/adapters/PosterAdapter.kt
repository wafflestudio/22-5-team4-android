package com.example.interpark.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.interpark.R

class PosterAdapter(private val posters: List<String>) : RecyclerView.Adapter<PosterAdapter.PosterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_poster, parent, false)
        return PosterViewHolder(view)
    }

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        holder.bind(posters[position])
    }

    override fun getItemCount(): Int = posters.size

    class PosterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(posterUri: String) {
            val imageView = itemView.findViewById<ImageView>(R.id.posterImageView)
            Glide.with(imageView.context) // Glide를 사용하여 URI 기반 이미지 로드
                .load(posterUri)
                .into(imageView)
        }
    }
}
