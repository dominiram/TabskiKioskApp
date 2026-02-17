package com.example.kiosklikeapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiosklikeapp.models.MerchantBrandingModel
import com.example.kiosklikeapp.models.MerchantMenuModel
import com.example.kiosklikeapp.models.NetworkResult
import com.example.kiosklikeapp.repos.MerchantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MerchantHomePageViewModel @Inject constructor(
    merchantRepository: MerchantRepository
) : ViewModel() {
    val uiState: StateFlow<MerchantHomeUiState> = combine(
        merchantRepository.fetchMenus(),
        merchantRepository.fetchBranding()
    ) { menusResult, brandingResult ->

        when {
            menusResult is NetworkResult.Success && brandingResult is NetworkResult.Success -> {
                MerchantHomeUiState.Success(
                    menus = menusResult.data ?: emptyList(),
                    branding = brandingResult.data!!
                )
            }

            menusResult is NetworkResult.Error -> MerchantHomeUiState.Error(
                menusResult.message ?: "Menu Error"
            )

            brandingResult is NetworkResult.Error -> MerchantHomeUiState.Error(
                brandingResult.message ?: "Branding Error"
            )

            else -> MerchantHomeUiState.Error("Unknown Error")
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = MerchantHomeUiState.Loading
    )
}

sealed class MerchantHomeUiState {
    data class Success(
        val menus: List<MerchantMenuModel>,
        val branding: MerchantBrandingModel
    ) : MerchantHomeUiState()

    data object Loading : MerchantHomeUiState()
    data class Error(val errorMessage: String) : MerchantHomeUiState()
}
