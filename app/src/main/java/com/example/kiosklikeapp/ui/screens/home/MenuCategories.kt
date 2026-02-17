package com.example.kiosklikeapp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kiosklikeapp.models.MenuCategoryModel
import com.example.kiosklikeapp.ui.composables.TitleText
import kotlinx.coroutines.launch

@Composable
fun MerchantCategories(
    modifier: Modifier = Modifier,
    categories: List<MenuCategoryModel>,
    onCategorySelected: (MenuCategoryModel) -> Unit,
    isCategorySelected: (MenuCategoryModel) -> Boolean
) {
    val horizontalScrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    coroutineScope.launch { horizontalScrollState.animateScrollTo(0) }
                },
            imageVector = Icons.Default.ArrowBackIosNew,
            contentDescription = null,
            tint = Color.LightGray
        )

        Row(
            modifier = Modifier
                .horizontalScroll(state = horizontalScrollState)
                .weight(1f)
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (category in categories) {
                CategoryItem(
                    modifier = Modifier,
                    category = category,
                    isCategorySelected = isCategorySelected(category),
                    onCategorySelected = onCategorySelected
                )
            }
        }

        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    coroutineScope.launch {
                        horizontalScrollState.animateScrollTo(horizontalScrollState.maxValue)
                    }
                },
            imageVector = Icons.AutoMirrored.Default.ArrowForwardIos,
            contentDescription = null,
            tint = Color.LightGray
        )
    }
}

@Composable
private fun CategoryItem(
    modifier: Modifier = Modifier,
    category: MenuCategoryModel,
    isCategorySelected: Boolean,
    onCategorySelected: (MenuCategoryModel) -> Unit
) {
    val textColor: Color = if (isCategorySelected) Color.White else Color.Black

    TitleText(
        modifier = modifier
            .background(
                color = if (isCategorySelected) Color.Red else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable {
                onCategorySelected(category)
            }
            .padding(8.dp),
        text = category.name,
        textColor = textColor,
        fontWeight = FontWeight(if (isCategorySelected) 700 else 400)
    )
}
