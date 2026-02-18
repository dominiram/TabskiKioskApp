package com.example.kiosklikeapp.ui.screens.purchase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kiosklikeapp.ui.Constants.DARK_RED_COLOR
import com.example.kiosklikeapp.ui.composables.DescriptionText
import com.example.kiosklikeapp.ui.composables.TitleText

@Composable
fun OrderPurchaseDialog(orderInfo: OrderPurchaseInfo) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .align(Alignment.Center),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TitleText(text = "Order Summary")

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = Color.LightGray)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DescriptionText(text = "Subtotal")
                    DescriptionText(text = "$${"%.2f".format(orderInfo.subtotalPrice)}")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DescriptionText(text = "Taxes")
                    DescriptionText(text = "$${"%.2f".format(orderInfo.taxes)}")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DescriptionText(text = "Tip")
                    DescriptionText(text = "$${"%.2f".format(orderInfo.tip)}")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TitleText(text = "Total")
                    TitleText(text = "$${"%.2f".format(orderInfo.totalPrice)}")
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(DARK_RED_COLOR),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    TitleText(
                        modifier = Modifier.padding(8.dp),
                        text = "Click to order",
                        textColor = Color.White
                    )
                }
            }
        }
    }
}

@Immutable
data class OrderPurchaseInfo(
    val totalPrice: Float,
    val subtotalPrice: Float,
    val tip: Float,
    val taxes: Float = 1.10f,
)
