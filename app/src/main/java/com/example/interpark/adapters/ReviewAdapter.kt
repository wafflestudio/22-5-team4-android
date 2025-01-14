package com.example.interpark.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.interpark.R
import com.example.interpark.data.Performance
import com.example.interpark.data.types.Review


class ReviewAdapter(
    private val reviews: List<Review>,
    private val onClick: (Review) -> Unit
) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        private val reviewTitle: TextView = itemView.findViewById(R.id.reviewTitle)
        private val author: TextView = itemView.findViewById(R.id.author)
        private val replies: TextView = itemView.findViewById(R.id.replies)
        private val views: TextView = itemView.findViewById(R.id.views)

        fun bind(review: Review, onClick: (Review) -> Unit) {
            ratingBar.rating = review.rating
            reviewTitle.text = review.title
            author.text = review.author
            replies.text = "댓글 0"
            views.text = "조회 10"
            itemView.setOnClickListener{
                onClick(review)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviews[position], onClick)
    }

    override fun getItemCount(): Int = reviews.size
}
