package com.example.kiosklikeapp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.kiosklikeapp.models.MenuCategoryModel
import com.example.kiosklikeapp.models.MerchantBrandingModel
import com.example.kiosklikeapp.models.MerchantMenuModel
import com.example.kiosklikeapp.ui.screens.ErrorScreen
import com.example.kiosklikeapp.ui.screens.LoadingScreen
import kotlinx.coroutines.launch

@Composable
fun MerchantHomePageWrapper() {
    val viewModel: MerchantHomePageViewModel = hiltViewModel()

    when (val uiState = viewModel.uiState.collectAsState().value) {
        is MerchantHomeUiState.Success -> MerchantHomePageScreen(
            menus = uiState.menus,
            branding = uiState.branding,
            searchText = uiState.searchText,
            onSearchTriggered = { viewModel.onSearchTriggered(it) }
        )

        is MerchantHomeUiState.Error -> ErrorScreen(uiState.errorMessage)
        is MerchantHomeUiState.Loading -> LoadingScreen()
    }
}

@Composable
fun MerchantHomePageScreen(
    menus: List<MerchantMenuModel>,
    branding: MerchantBrandingModel,
    searchText: String,
    onSearchTriggered: (String) -> Unit,
    backgroundColor: Color = Color(0xFFE5E4E2)
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val focusManager = LocalFocusManager.current

    var selectedMenu by remember(menus) { mutableStateOf(menus.firstOrNull()) }
    var selectedCategory by remember(selectedMenu) {
        mutableStateOf(selectedMenu?.categories?.firstOrNull())
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { focusManager.clearFocus() }
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                MerchantLogo(coverUrl = branding.coverUrl, logoUrl = branding.logoUrl)
            }

            stickyHeader {
                MerchantMenus(
                    modifier = Modifier
                        .background(backgroundColor)
                        .statusBarsPadding(),
                    items = menus,
                    selectedMenu = selectedMenu?.name ?: "",
                    onMenuSelected = { selectedMenu = it },
                    onSearchTriggered = onSearchTriggered
                )

                if (searchText.isBlank()) selectedMenu?.categories?.let { categories ->
                    MerchantCategories(
                        modifier = Modifier.background(backgroundColor),
                        categories = categories,
                        isCategorySelected = { it == selectedCategory },
                        onCategorySelected = { category ->
                            selectedCategory = category
                            coroutineScope.launch {
                                val index = findCategoryIndex(selectedMenu, category)
                                listState.animateScrollToItem(index, scrollOffset = -10)
                            }
                        }
                    )
                }
            }

            selectedMenu?.categories?.forEach { category ->
                item(key = category.name) {
                    MenuItemTitle(
                        modifier = Modifier.padding(top = 8.dp),
                        title = category.name
                    )
                }

                items(category.items) { menuItem ->
                    MenuItem(menuItem)
                }
            }
        }
    }
}

private fun findCategoryIndex(menu: MerchantMenuModel?, target: MenuCategoryModel): Int {
    if (menu == null) return 0
    var index = 0

    for (category in menu.categories) {
        if (category.name == target.name) return index
        index += 1 + category.items.size
    }

    return index
}
