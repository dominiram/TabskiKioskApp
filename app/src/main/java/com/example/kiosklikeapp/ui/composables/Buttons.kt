package com.example.kiosklikeapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kiosklikeapp.ui.Constants.DARK_RED_COLOR

@Composable
fun AddItemButton(
    modifier: Modifier = Modifier,
    itemCount: Int,
    decrementItemCount: () -> Unit,
    incrementItemCount: () -> Unit,
    backgroundColor: Color = Color(0xFFE5E4E2)
) {
    Row(
        modifier = modifier
            .background(shape = RoundedCornerShape(8.dp), color = backgroundColor)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable(enabled = itemCount > 1) { decrementItemCount() },
            imageVector = Icons.Default.Remove,
            tint = if (itemCount > 1) Color.Black else Color.Gray,
            contentDescription = null
        )

        Text(
            text = itemCount.toString(),
            style = TextStyle(
                color = Color.Black,
                fontSize = 28.sp,
                fontWeight = FontWeight(700)
            )
        )

        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable { incrementItemCount() },
            imageVector = Icons.Default.Add,
            tint = Color.Black,
            contentDescription = null
        )
    }
}

@Composable
fun AddToOrderButton(
    modifier: Modifier = Modifier,
    price: Float,
    backgroundColor: Color = Color(DARK_RED_COLOR)
) {
    Row(
        modifier = modifier
            .background(shape = RoundedCornerShape(8.dp), color = backgroundColor)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TitleText(
            modifier = Modifier,
            text = "Add to order",
            fontWeight = FontWeight(400),
            textColor = Color.White
        )

        TitleText(
            text = "$$price",
            textColor = Color.White
        )
    }
}
