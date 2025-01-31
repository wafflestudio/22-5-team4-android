package com.example.interpark.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interpark.R
import com.example.interpark.data.types.Comment
import com.example.interpark.data.types.Review
import com.example.interpark.viewModels.ReviewViewModel
import com.google.api.Distribution.BucketOptions.Linear

class ReviewAdapter(
    private val writeComment: (reviewId: String, content: String) -> Unit,
    private val readComment: (reviewId: String) -> Unit
) : PagingDataAdapter<Review, RecyclerView.ViewHolder>(ReviewDiffCallback()) {

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
            ratingBar.rating = review.rating.toFloat()
            reviewTitle.text = review.title
            author.text = review.author
            replies.text = "댓글 ${review.replyCount}"
            views.text = "조회 ${review.likeCount}"
        }
    }

    inner class OpenedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        private val reviewTitle: TextView = itemView.findViewById(R.id.reviewTitle)
        private val author: TextView = itemView.findViewById(R.id.author)
        private val replies: TextView = itemView.findViewById(R.id.replies)
        private val views: TextView = itemView.findViewById(R.id.views)
        private val content: TextView = itemView.findViewById(R.id.reviewContent)
        private val commentLayout: LinearLayout = itemView.findViewById(R.id.CommentWriteLayout)
        private val commentButton: Button = itemView.findViewById(R.id.commentButton)
        private val commentWriteButton: Button = itemView.findViewById(R.id.commentWriteButton)
        private val commentWriteEditText: EditText = itemView.findViewById(R.id.commentWriteEditText)
        private val commentRecyclerView: RecyclerView = itemView.findViewById(R.id.commentRecyclerView)

        fun bind(review: Review) {
            ratingBar.rating = review.rating.toFloat()
            reviewTitle.text = review.title
            author.text = review.author
            replies.text = "댓글 ${review.replyCount}"
            views.text = "조회 ${review.likeCount}"
            content.text = review.content

            commentButton.setOnClickListener {
                commentLayout.visibility = if (commentLayout.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }

            commentWriteButton.setOnClickListener {
                if (commentWriteEditText.text.isNotBlank()) {
                    writeComment(review.id, commentWriteEditText.text.toString())
                    commentWriteEditText.text = null
                    commentLayout.visibility = View.GONE
                }
            }

            commentRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
            readComment(review.id)
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
        getItem(position)?.let { review ->
            if (holder is NormalViewHolder) {
                holder.bind(review)
            } else if (holder is OpenedViewHolder) {
                holder.bind(review)
            }

            holder.itemView.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = if (position == selectedPosition) RecyclerView.NO_POSITION else position
                readComment(review.id)
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)
            }
        }
    }

    class ReviewDiffCallback : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean = oldItem == newItem
    }
}
