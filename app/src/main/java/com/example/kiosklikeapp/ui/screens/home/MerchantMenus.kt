package com.example.kiosklikeapp.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.kiosklikeapp.models.MerchantMenuModel
import com.example.kiosklikeapp.ui.composables.SearchableDropdownField

@Composable
fun MerchantMenus(
    modifier: Modifier = Modifier,
    items: List<MerchantMenuModel>,
    selectedMenu: String,
    onMenuSelected: (MerchantMenuModel) -> Unit,
    onSearchTriggered: (String) -> Unit
) {
    SearchableDropdownField(
        modifier = modifier,
        items = items.map { it.name },
        selectedMenu = selectedMenu,
        onItemSelected = { menu ->
            items.firstOrNull { it.name == menu }?.let {
                onMenuSelected(it)
            }
        },
        onSearchTriggered = onSearchTriggered
    )
}
