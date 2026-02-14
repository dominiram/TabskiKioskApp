package com.example.kiosklikeapp.models

data class MerchantMenuModel(
    val id: String,
    val name: String,
    val categories: List<MenuCategoryModel>
)

data class MenuCategoryModel(
    val id: String,
    val name: String,
    val sortOrder: Int,
    val items: List<MenuItemModel>
)

data class MenuItemModel(
    val id: String,
    val name: String,
    val description: String?,
    val imageUrl: String?,
    val price: Float,
    val stockCount: Int
)
