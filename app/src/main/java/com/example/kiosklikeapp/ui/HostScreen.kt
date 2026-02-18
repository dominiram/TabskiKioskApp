package com.example.kiosklikeapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.kiosklikeapp.ui.screens.home.MerchantHomePageWrapper
import com.example.kiosklikeapp.ui.screens.purchase.OrderPurchaseDialog
import com.example.kiosklikeapp.ui.screens.purchase.OrderPurchaseInfo
import com.example.kiosklikeapp.ui.screens.purchase.PurchaseScreen
import kotlinx.serialization.Serializable

sealed class NavigationScreen {
    @Serializable
    data object MerchantHomePage : NavigationScreen()

    @Serializable
    data object PurchaseScreen : NavigationScreen()

    @Serializable
    data class PurchasePopUpDialog(
        val totalPrice: Float,
        val subtotalPrice: Float,
        val tip: Float,
        val taxes: Float = 1.10f,
    ) : NavigationScreen()
}

@Composable
fun HostScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationScreen.MerchantHomePage
    ) {
        composable<NavigationScreen.MerchantHomePage> {
            MerchantHomePageWrapper(
                navigateToPurchaseScreen = {
                    navController.navigate(NavigationScreen.PurchaseScreen)
                }
            )
        }

        composable<NavigationScreen.PurchaseScreen> {
            PurchaseScreen(
                navigateToPaymentPopUpScreen = { orderInfo ->
                    navController.navigate(
                        NavigationScreen.PurchasePopUpDialog(
                            totalPrice = orderInfo.totalPrice,
                            subtotalPrice = orderInfo.subtotalPrice,
                            tip = orderInfo.tip
                        )
                    )
                }
            )
        }

        composable<NavigationScreen.PurchasePopUpDialog> { backStackEntry ->
            val route = backStackEntry.toRoute<NavigationScreen.PurchasePopUpDialog>()

            OrderPurchaseDialog(
                OrderPurchaseInfo(
                    totalPrice = route.totalPrice,
                    subtotalPrice = route.subtotalPrice,
                    tip = route.tip
                )
            )
        }
    }
}
