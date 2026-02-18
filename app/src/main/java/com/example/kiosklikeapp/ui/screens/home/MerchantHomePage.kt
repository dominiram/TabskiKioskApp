package com.example.kiosklikeapp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.kiosklikeapp.models.MenuCategoryModel
import com.example.kiosklikeapp.models.MenuItemModel
import com.example.kiosklikeapp.models.MerchantBrandingModel
import com.example.kiosklikeapp.models.MerchantMenuModel
import com.example.kiosklikeapp.models.PurchaseItemInfo
import com.example.kiosklikeapp.ui.Constants.DARK_RED_COLOR
import com.example.kiosklikeapp.ui.Constants.LIGHT_RED_COLOR
import com.example.kiosklikeapp.ui.composables.AddItemToCardBottomSheetDialog
import com.example.kiosklikeapp.ui.composables.TitleText
import com.example.kiosklikeapp.ui.screens.ErrorScreen
import com.example.kiosklikeapp.ui.screens.LoadingScreen
import kotlinx.coroutines.launch

@Composable
fun MerchantHomePageWrapper(navigateToPurchaseScreen: (String) -> Unit) {
    val viewModel: MerchantHomePageViewModel = hiltViewModel()
    var selectedItemForCustomization by remember { mutableStateOf<MenuItemModel?>(null) }
    val addedItems = viewModel.addedItems.collectAsState(emptyMap()).value

    when (val uiState = viewModel.uiState.collectAsState().value) {
        is MerchantHomeUiState.Success -> MerchantHomePageScreen(
            menus = uiState.menus,
            branding = uiState.branding,
            searchText = uiState.searchText,
            addedItems = addedItems,
            onSearchTriggered = { viewModel.onSearchTriggered(it) },
            openCartBottomSheetDialog = { selectedItemForCustomization = it },
            navigateToPurchaseScreen = navigateToPurchaseScreen
        )

        is MerchantHomeUiState.Error -> ErrorScreen(uiState.errorMessage)
        is MerchantHomeUiState.Loading -> LoadingScreen()
    }

    selectedItemForCustomization?.let { item ->
        AddItemToCardBottomSheetDialog(
            item = item,
            onDismiss = { selectedItemForCustomization = null },
            addItemToCart = { item, count -> viewModel.addItemToCount(item, count) }
        )
    }
}

@Composable
fun MerchantHomePageScreen(
    menus: List<MerchantMenuModel>,
    branding: MerchantBrandingModel,
    searchText: String,
    addedItems: Map<String, PurchaseItemInfo>,
    onSearchTriggered: (String) -> Unit,
    openCartBottomSheetDialog: (MenuItemModel) -> Unit,
    navigateToPurchaseScreen: (String) -> Unit,
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
                    MenuItem(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        item = menuItem,
                        onItemClicked = { item ->
                            openCartBottomSheetDialog(item)
                        }
                    )
                }
            }
        }

        addedItems.takeIf { it.isNotEmpty() }?.let {
            Row(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .align(Alignment.BottomCenter)
                    .background(color = Color(DARK_RED_COLOR), shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 12.dp)
                    .clickable { navigateToPurchaseScreen(addedItems.getTotalItemsPrice()) },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TitleText(
                        modifier = Modifier
                            .background(
                                color = Color(LIGHT_RED_COLOR),
                                shape = CircleShape
                            )
                            .padding(8.dp),
                        text = addedItems.getTotalItemsCount(),
                        textColor = Color.White
                    )

                    TitleText(text = "Order summary", textColor = Color.White)
                }

                TitleText(text = addedItems.getTotalItemsPrice(), textColor = Color.White)
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

private fun Map<String, PurchaseItemInfo>.getTotalItemsCount(): String {
    var result = 0

    for (purchaseItem in values) {
        result += purchaseItem.count
    }

    return result.toString()
}

private fun Map<String, PurchaseItemInfo>.getTotalItemsPrice(): String {
    var result = 0f

    for (purchaseItem in values) {
        result += purchaseItem.count * purchaseItem.price
    }

    return "$$result"
}
