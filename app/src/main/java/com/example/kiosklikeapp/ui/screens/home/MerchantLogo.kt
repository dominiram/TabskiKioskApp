package com.example.kiosklikeapp.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.kiosklikeapp.ui.composables.UrlImage

@Composable
fun MerchantLogo(coverUrl: String?, logoUrl: String?) {
    Box(modifier = Modifier.fillMaxHeight(0.3f)) {
        UrlImage(imageUrl = coverUrl, modifier = Modifier.fillMaxWidth())

        UrlImage(
            imageUrl = logoUrl,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(0.2f)
        )
    }
}
