package com.example.kiosklikeapp.ui.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TitleText(text: String, modifier: Modifier = Modifier, textColor: Color = Color.Black) {
    Text(
        modifier = modifier,
        text = text,
        style = TextStyle(
            color = textColor,
            fontSize = 24.sp,
            fontWeight = FontWeight(700)
        )
    )
}

@Composable
fun DescriptionText(text: String, modifier: Modifier = Modifier, textColor: Color = Color.Gray) {
    Text(
        modifier = modifier,
        text = text,
        style = TextStyle(
            color = textColor,
            fontSize = 18.sp,
            fontWeight = FontWeight(400)
        )
    )
}
