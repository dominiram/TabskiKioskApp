package com.example.kiosklikeapp.ui.screens.purchase

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kiosklikeapp.models.PurchaseItemInfo
import com.example.kiosklikeapp.ui.composables.TitleText
import com.example.kiosklikeapp.ui.composables.UrlImage

@Composable
fun OrderDetailsSection(
    title: String = "Order Details",
    titleTextColor: Color = Color.Black,
    items: Map<String, PurchaseItemInfo>,
    removeItem: (String) -> Unit
) {
    TitleText(text = title, textColor = titleTextColor)

    HorizontalDivider(
        modifier = Modifier.fillMaxWidth().height(1.dp),
        color = Color.LightGray
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (value in items.values) {
            OrderDetailsItem(value, removeItem)

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth().height(1.dp),
                color = Color.LightGray
            )
        }
    }
}

@Composable
private fun OrderDetailsItem(item: PurchaseItemInfo, removeItem: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TitleText(text = item.name)
            UrlImage(modifier = Modifier.size(48.dp), imageUrl = item.imageUrl)
        }

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val price = item.price * item.count
            TitleText(text = "$$price")

            Icon(
                modifier = Modifier
                    .size(48.dp)
                    .clickable { removeItem(item.id) },
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = Color.Red
            )
        }
    }
}
