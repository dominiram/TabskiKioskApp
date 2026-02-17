package com.example.kiosklikeapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.kiosklikeapp.ui.composables.ErrorTitleText

@Composable
fun ErrorScreen(errorMessage: String, backgroundColor: Color = Color.White) {
    Box(
        modifier = Modifier.fillMaxSize().background(color = backgroundColor)
    ) {
        ErrorTitleText(modifier = Modifier.align(Alignment.Center), text = errorMessage)
    }
}
