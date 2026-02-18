package com.example.kiosklikeapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.kiosklikeapp.ui.screens.home.MerchantHomePageWrapper
import com.example.kiosklikeapp.ui.screens.purchase.PurchaseScreen

sealed class NavigationScreen {
    data object MerchantHomePage : NavigationScreen()
    data class PurchaseScreen(val price: String) : NavigationScreen()
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
                        NavigationScreen.PurchaseScreen(price)
                    )
                }
            )
        }

        composable<NavigationScreen.PurchaseScreen> { backStackEntry ->
            val price = backStackEntry.toRoute<NavigationScreen.PurchaseScreen>().price
            PurchaseScreen(price)
        }
    }
}

fun NavigationScreen.toRoute(): String = this::class.simpleName ?: this.toString()
