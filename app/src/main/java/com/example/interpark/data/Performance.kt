package com.example.interpark.data


data class Category(val name: String, val iconResId: Int)

val categories = listOf(
    Category("뮤지컬", 0),
    Category("콘서트", 1),
    Category("스포츠", 2),
    Category("클래식/무용", 3),
    Category("연극", 4),
    Category("레저/캠핑", 5),
    Category("아동/가족", 6),
    Category("전시/행사", 7)
)

sealed class CategoryItem {
    data class Category(val name: String, val iconResId: Int) : CategoryItem()
    data class FooterItem(val name: String, val iconResId: Int) : CategoryItem()
}