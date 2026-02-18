package com.example.kiosklikeapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.kiosklikeapp.ui.screens.home.MerchantHomePageWrapper
import com.example.kiosklikeapp.ui.screens.purchase.PurchaseScreen
import kotlinx.serialization.Serializable

sealed class NavigationScreen {
    data object MerchantHomePage : NavigationScreen()

    @Serializable
    data class PurchaseRoute(val price: String) : NavigationScreen()
}

@Composable
fun HostScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationScreen.MerchantHomePage.toRoute()
    ) {
        composable(NavigationScreen.MerchantHomePage.toRoute()) {
            MerchantHomePageWrapper(
                navigateToPurchaseScreen = { price ->
                    navController.navigate(
                        NavigationScreen.PurchaseRoute(price)
                    )
                }
            )
        }

        composable<NavigationScreen.PurchaseRoute> { backStackEntry ->
            val price = backStackEntry.toRoute<NavigationScreen.PurchaseRoute>().price
            PurchaseScreen(price)
        }
    }
}

fun NavigationScreen.toRoute(): String = this::class.simpleName ?: this.toString()
