package com.example.kiosklikeapp.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.kiosklikeapp.R

@Composable
fun UrlImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    imagePlaceholder: Int = R.drawable.ic_placeholder
) {
    AsyncImage(
        model = imageUrl,
        modifier = modifier,
        placeholder = painterResource(imagePlaceholder),
        error = painterResource(imagePlaceholder),
        contentScale = ContentScale.FillBounds,
        contentDescription = null
    )
}
