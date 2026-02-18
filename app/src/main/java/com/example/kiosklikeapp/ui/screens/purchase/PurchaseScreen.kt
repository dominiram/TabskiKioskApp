package com.example.kiosklikeapp.ui.screens.purchase

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.kiosklikeapp.ui.Constants.DARK_RED_COLOR
import com.example.kiosklikeapp.ui.composables.PaymentTips
import com.example.kiosklikeapp.ui.composables.PurchaseInfoSection
import com.example.kiosklikeapp.ui.composables.TitleText

@Composable
fun PurchaseScreen(navigateToPaymentPopUpScreen: (OrderPurchaseInfo) -> Unit) {
    val viewModel: PurchaseScreenViewModel = hiltViewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp, horizontal = 12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PurchaseInfoSection(
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
                title = "Ordering Method",
                description = "Online pickup"
            )

            PurchaseInfoSection(
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
                title = "Time",
                description = "As soon as possible (30 minutes)"
            )

            OrderDetailsSection()

            PurchaseInfoSection(
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
                title = "Payment",
                description = "**** **** **** 4242"
            )

            val focusManager = LocalFocusManager.current

            PaymentTips(
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
                onTipSelected = {
                    focusManager.clearFocus()
                    viewModel.addPaymentTips(it)
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(DARK_RED_COLOR),
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { navigateToPaymentPopUpScreen(viewModel.createOrderPurchaseInfo()) }
                .padding(horizontal = 12.dp, vertical = 16.dp)
        ) {
            TitleText(text = "Proceed to payment", textColor = Color.White)
        }
    }
}
