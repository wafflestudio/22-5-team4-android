package com.example.interpark.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.interpark.R
import com.example.interpark.data.Category
import com.example.interpark.data.CategoryItem

data class HomeCategoryRankCategory(
    val category: Category,
    val selected: Boolean
)

class HomeCategoryRankCategoryListAdapter(
    private val items: List<HomeCategoryRankCategory>,
    private val onCategoryClick: (Category) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_CLICKED = 0
        const val TYPE_NOT_CLICKED = 1
    }


    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.categoryName)

        fun bind(item: Category, onClick: (Category) -> Unit) {
            name.text = item.name
            itemView.setOnClickListener { onClick(item) }
        }
    }

    override fun getItemViewType(position: Int): Int{
        return if(items[position].selected) TYPE_CLICKED else TYPE_NOT_CLICKED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_category_rank_category_list, parent, false)
        return CategoryViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val categoryItem = items[position]
        if(holder is CategoryViewHolder){
            holder.bind(categoryItem.category, onCategoryClick)
        }
    }

    override fun getItemCount(): Int = items.size
}
