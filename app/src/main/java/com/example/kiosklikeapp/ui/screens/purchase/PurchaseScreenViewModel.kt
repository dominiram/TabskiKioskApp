package com.example.kiosklikeapp.ui.screens.purchase

import androidx.lifecycle.ViewModel
import com.example.kiosklikeapp.repos.MerchantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PurchaseScreenViewModel @Inject constructor(
    private val merchantRepository: MerchantRepository
): ViewModel() {
    fun addPaymentTips(tipsPercentage: Int) {
        merchantRepository.addPaymentTips(tipsPercentage)
    }

    fun createOrderPurchaseInfo(): OrderPurchaseInfo = merchantRepository.createOrderPurchaseInfo()
}
