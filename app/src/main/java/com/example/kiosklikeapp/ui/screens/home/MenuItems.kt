package com.example.kiosklikeapp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kiosklikeapp.models.MenuItemModel
import com.example.kiosklikeapp.ui.composables.DescriptionText
import com.example.kiosklikeapp.ui.composables.TitleText
import com.example.kiosklikeapp.ui.composables.UrlImage

@Composable
fun MenuItemTitle(
    modifier: Modifier = Modifier,
    title: String
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp),
        text = title,
        style = TextStyle(
            fontSize = 26.sp,
            fontWeight = FontWeight(700)
        )
    )
}

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    item: MenuItemModel,
    onItemClicked: (MenuItemModel) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .clickable { onItemClicked(item) }
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            TitleText(text = item.name)
            DescriptionText(text = item.description ?: "")
            TitleText(text = "$${item.price}")
        }

        UrlImage(
            modifier = Modifier
                .height(64.dp)
                .width(64.dp)
                .clip(shape = RoundedCornerShape(8.dp)),
            imageUrl = item.imageUrl,
        )
    }
}
