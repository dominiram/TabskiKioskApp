package com.example.kiosklikeapp.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.kiosklikeapp.R
import com.example.kiosklikeapp.ui.Constants.LIGHT_BLUE_COLOR

@Composable
fun PurchaseInfoSection(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    titleTextColor: Color = Color.Black,
    descriptionTextColor: Color = Color.Gray,
    dividerColor: Color = Color.LightGray
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        TitleText(text = title, textColor = titleTextColor)

        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DescriptionText(text = description, textColor = descriptionTextColor)

            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.edit_icon),
                contentDescription = null,
                tint = Color(LIGHT_BLUE_COLOR)
            )
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth().height(1.dp),
            color = dividerColor
        )
    }
}
