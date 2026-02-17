package com.example.kiosklikeapp.utils

import com.example.kiosklikeapp.GetMerchantBrandingQuery
import com.example.kiosklikeapp.GetMerchantMenusQuery
import com.example.kiosklikeapp.models.MenuCategoryModel
import com.example.kiosklikeapp.models.MenuItemModel
import com.example.kiosklikeapp.models.MerchantBrandingModel
import com.example.kiosklikeapp.models.MerchantMenuModel

fun GetMerchantBrandingQuery.Merchant.toMerchantDataModel() = MerchantBrandingModel(
    id = id,
    logoUrl = branding?.logoUrl,
    coverUrl = branding?.coverUrl,
    buttonBgColor = branding?.buttonBgColor,
    buttonTextColor = branding?.buttonTextColor,
    brightness = branding?.coverBrightness
)

fun GetMerchantMenusQuery.Menus.toMenuDomainModel(): List<MerchantMenuModel> {
    return this.items.map { menu ->
        MerchantMenuModel(
            id = menu.id,
            name = menu.name,
            categories = menu.categories.map { cat ->
                MenuCategoryModel(
                    id = cat.id,
                    name = cat.name,
                    sortOrder = (cat.order as? Int) ?: 0,
                    items = cat.menuItems.map { item ->
                        MenuItemModel(
                            id = item.id,
                            name = item.name,
                            description = item.description,
                            imageUrl = item.imageUrl,
                            price = (((item.price as? Int) ?: 0).toFloat() / 100),
                            stockCount = item.stockCount
                        )
                    }
                )
            }.sortedBy { it.sortOrder }
        )
    }
}

fun List<MerchantMenuModel>.addAllMenusItem(): List<MerchantMenuModel> = buildList {
    if (this@addAllMenusItem.isNotEmpty()) {
        add(createCombinedMenu(this@addAllMenusItem))
        addAll(this@addAllMenusItem)
    }
}

private fun createCombinedMenu(allMenusList: List<MerchantMenuModel>) = MerchantMenuModel(
    id = allMenusList.joinToString(limit = 2) { it.id },
    name = "All menus",
    categories = allMenusList.flatMap { it.categories }.sortedBy { it.sortOrder }
)
