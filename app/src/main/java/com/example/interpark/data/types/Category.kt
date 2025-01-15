package com.example.interpark.data.types


data class Category(val name: String, val kname: String, val iconResId: Int)

val list_categories = listOf(
    Category("MUSICAL","뮤지컬", 0),
    Category("CONCERT","콘서트", 1),
    Category("SPORT","스포츠", 2),
    Category("CLASSIC","클래식/무용", 3),
    Category("PLAY","연극", 4),
    Category("LEISURE","레저/캠핑", 5),
    Category("CHILDREN","아동/가족", 6),
    Category("EXHIBITION","전시/행사", 7)
)

sealed class CategoryItem {
    data class Category(val name: String, val iconResId: Int) : CategoryItem()
    data class FooterItem(val name: String, val iconResId: Int) : CategoryItem()
}