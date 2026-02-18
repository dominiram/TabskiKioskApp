package com.example.kiosklikeapp.ui.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ErrorTitleText(text: String, modifier: Modifier = Modifier, textColor: Color = Color.Black) {
    Text(
        modifier = modifier,
        text = text,
        style = TextStyle(
            color = textColor,
            fontSize = 36.sp,
            fontWeight = FontWeight(700)
        )
    )
}

@Composable
fun TitleTextLarge(text: String, modifier: Modifier = Modifier, textColor: Color = Color.Black) {
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
fun TitleText(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Black,
    fontWeight: FontWeight = FontWeight(700)
) {
    Text(
        modifier = modifier,
        text = text,
        style = TextStyle(
            color = textColor,
            fontSize = 18.sp,
            fontWeight = fontWeight
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
            fontSize = 14.sp,
            fontWeight = FontWeight(400)
        )
    )
}
