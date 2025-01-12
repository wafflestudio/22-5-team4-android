package com.example.interpark.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.interpark.R
import com.example.interpark.data.CategoryItem

class HomeCategoryAdapter(
    private val items: List<CategoryItem.Category>,
    private val onCategoryClick: (CategoryItem.Category) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_category, parent, false)
        return CategoryViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val categoryItem = items[position]
        if(holder is CategoryViewHolder){
            holder.bind(categoryItem, onCategoryClick)
        }
    }

    override fun getItemCount(): Int = items.size
}
