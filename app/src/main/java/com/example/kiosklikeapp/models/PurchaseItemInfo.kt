package com.example.kiosklikeapp.models

data class PurchaseItemInfo(
    val id: String,
    val name: String,
    val imageUrl: String?,
    val price: Float,
    val count: Int
)
