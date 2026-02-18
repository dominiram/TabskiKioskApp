package com.example.kiosklikeapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.kiosklikeapp.ui.Constants.DARK_RED_COLOR

@Composable
fun PaymentTips(modifier: Modifier = Modifier, onTipSelected: (Int) -> Unit) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.Start
    ) {
        DescriptionText(text = "Chose tip amount")

        val tipsPercentage = listOf(25, 20, 18, 15, 0)
        var selectedTips by remember { mutableIntStateOf(-1) }

        val isCustomTipsSelected by remember(selectedTips) {
            mutableStateOf(selectedTips == tipsPercentage.lastOrNull())
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            for (tip in tipsPercentage) {
                PaymentTipButton(
                    modifier = Modifier.weight(1f),
                    tipsPercentage = tip,
                    isSelected = { it == selectedTips },
                    onButtonClicked = {
                        selectedTips = it
                        if (it != tipsPercentage.lastIndex) onTipSelected(it)
                    }
                )
            }
        }

        var text by remember { mutableStateOf("") }

        if (isCustomTipsSelected) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RegularTextField(
                    modifier = Modifier.weight(1f),
                    text = text,
                    onTextChanged = { newValue ->
                        val newValueInt = newValue.toIntOrNull()

                        if (newValue.isEmpty() || newValue.isDigitsOnly() && newValueInt != null &&
                            newValueInt in 0..100
                        ) {
                            text = newValue
                        }
                    },
                    hintText = "Enter custom tips"
                )

                Box(
                    modifier = Modifier
                        .background(
                            color = Color(DARK_RED_COLOR),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { text.toIntOrNull()?.let { onTipSelected(it) } }
                        .padding(horizontal = 12.dp, vertical = 16.dp)
                ) {
                    TitleText(text = "Apply tips", textColor = Color.White)
                }
            }
        }
    }
}

@Composable
private fun PaymentTipButton(
    modifier: Modifier = Modifier,
    tipsPercentage: Int,
    isSelected: (Int) -> Boolean,
    onButtonClicked: (Int) -> Unit
) {
    Box(
        modifier = modifier
            .background(
                color = if (isSelected(tipsPercentage)) Color(DARK_RED_COLOR) else Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(shape = RoundedCornerShape(8.dp))
            .clickable { onButtonClicked(tipsPercentage) }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = tipsPercentage.takeIf { it > 0 }?.let { "$it%" } ?: "Custom",
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight(400)
            )
        )
    }
}
