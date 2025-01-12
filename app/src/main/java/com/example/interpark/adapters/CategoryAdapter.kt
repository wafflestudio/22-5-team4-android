package com.example.interpark.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.interpark.R
import com.example.interpark.data.CategoryItem

class CategoryAdapter(
    private val items: List<CategoryItem>,
    private val onCategoryClick: (CategoryItem.Category) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_CATEGORY = 0
        const val TYPE_FOOTER = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is CategoryItem.Category -> TYPE_CATEGORY
            else -> TYPE_FOOTER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_CATEGORY) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category, parent, false)
            CategoryViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_footer, parent, false)
            FooterViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is CategoryItem.Category -> (holder as CategoryViewHolder).bind(item, onCategoryClick)
            is CategoryItem.FooterItem -> (holder as FooterViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.categoryName)
        private val icon: ImageView = itemView.findViewById(R.id.categoryIcon)

        fun bind(item: CategoryItem.Category, onClick: (CategoryItem.Category) -> Unit) {
            name.text = when (item.name) {
                "MUSICAL" -> "뮤지컬"
                "CONCERT" -> "콘서트"
                "PLAY" -> "연극"
                "CLASSIC" -> "클래식/무용"
                "SPORT" -> "스포츠"
                else -> item.name // 기본값으로 원래 이름 표시
            }
            icon.setImageResource(item.iconResId)
            itemView.setOnClickListener { onClick(item) }
        }
    }

    class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.footerName)
        private val icon: ImageView = itemView.findViewById(R.id.footerIcon)

        fun bind(item: CategoryItem.FooterItem) {
            name.text = item.name
            icon.setImageResource(item.iconResId)
        }
    }
}
