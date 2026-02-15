package com.example.kiosklikeapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingScreen(backgroundColor: Color = Color.White, loadingIndicatorColor: Color = Color.Red) {
    Box(
        modifier = Modifier.fillMaxSize().background(color = backgroundColor)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(128.dp),
            color = loadingIndicatorColor
        )
    }
}
