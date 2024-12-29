package com.example.interpark.data


sealed class CategoryItem {
    data class Category(val name: String, val iconResId: Int) : CategoryItem()
    data class FooterItem(val name: String, val iconResId: Int) : CategoryItem()
}
