package com.example.kiosklikeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.kiosklikeapp.ui.screens.MerchantHomePageWrapper
import com.example.kiosklikeapp.ui.theme.KioskLikeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            KioskLikeAppTheme {
                MerchantHomePageWrapper()
            }
        }
    }
}
