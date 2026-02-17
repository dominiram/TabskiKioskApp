package com.example.kiosklikeapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiosklikeapp.models.MerchantBrandingModel
import com.example.kiosklikeapp.models.MerchantMenuModel
import com.example.kiosklikeapp.models.NetworkResult
import com.example.kiosklikeapp.repos.MerchantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MerchantHomePageViewModel @Inject constructor(
    merchantRepository: MerchantRepository
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")

    val uiState: StateFlow<MerchantHomeUiState> = combine(
        merchantRepository.fetchMenus(),
        merchantRepository.fetchBranding(),
        _searchQuery
    ) { menusResult, brandingResult, searchText ->
        when {
            menusResult is NetworkResult.Success && brandingResult is NetworkResult.Success -> {
                val initialMenus = menusResult.data ?: emptyList()

                val displayedMenus =
                    if (searchText.isBlank()) initialMenus else filterAllMenusByItems(
                        initialMenus,
                        searchText
                    )

                MerchantHomeUiState.Success(
                    initialMenus = initialMenus,
                    menus = displayedMenus,
                    branding = brandingResult.data!!,
                    searchText = searchText
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

    fun onSearchTriggered(searchText: String) {
        _searchQuery.value = searchText
    }

    private fun filterAllMenusByItems(
        menus: List<MerchantMenuModel>,
        searchText: String
    ): List<MerchantMenuModel> {
        val firstMenu = menus.firstOrNull() ?: return emptyList()

        val filteredCategories = firstMenu.categories.map { category ->
            category.copy(
                items = category.items.filter { item ->
                    item.name.contains(searchText, ignoreCase = true)
                }
            )
        }.filter { it.items.isNotEmpty() }

        return listOf(firstMenu.copy(categories = filteredCategories))
    }
}

sealed class MerchantHomeUiState {
    data class Success(
        val initialMenus: List<MerchantMenuModel>,
        val menus: List<MerchantMenuModel>,
        val branding: MerchantBrandingModel,
        val searchText: String
    ) : MerchantHomeUiState()

    data object Loading : MerchantHomeUiState()
    data class Error(val errorMessage: String) : MerchantHomeUiState()
}
