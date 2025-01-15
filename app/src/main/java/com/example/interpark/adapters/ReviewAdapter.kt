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
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    companion object {
        private const val VIEW_TYPE_NORMAL = 0
        private const val VIEW_TYPE_OPENED = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == selectedPosition) VIEW_TYPE_OPENED else VIEW_TYPE_NORMAL
    }

    inner class NormalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        private val reviewTitle: TextView = itemView.findViewById(R.id.reviewTitle)
        private val author: TextView = itemView.findViewById(R.id.author)
        private val replies: TextView = itemView.findViewById(R.id.replies)
        private val views: TextView = itemView.findViewById(R.id.views)

        fun bind(review: Review) {
            ratingBar.rating = review.rating
            reviewTitle.text = review.title
            author.text = review.author
            replies.text = "댓글 0"
            views.text = "조회 10"
        }
    }

    inner class OpenedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        private val reviewTitle: TextView = itemView.findViewById(R.id.reviewTitle)
        private val author: TextView = itemView.findViewById(R.id.author)
        private val replies: TextView = itemView.findViewById(R.id.replies)
        private val views: TextView = itemView.findViewById(R.id.views)

        fun bind(review: Review) {
            ratingBar.rating = review.rating
            reviewTitle.text = review.title
            author.text = review.author
            replies.text = "댓글 0"
            views.text = "조회 10"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_OPENED) {
            val view = inflater.inflate(R.layout.item_review_opened, parent, false)
            OpenedViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.item_review, parent, false)
            NormalViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val review = reviews[position]
        if (holder is NormalViewHolder) {
            holder.bind(review)
        } else if (holder is OpenedViewHolder) {
            holder.bind(review)
        }

        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = if (position == selectedPosition) {
                RecyclerView.NO_POSITION
            } else {
                position
            }
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
        }
    }

    override fun getItemCount(): Int = reviews.size
}
