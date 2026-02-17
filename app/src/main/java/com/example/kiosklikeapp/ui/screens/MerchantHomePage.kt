package com.example.kiosklikeapp.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.kiosklikeapp.models.MenuCategoryModel
import com.example.kiosklikeapp.models.MenuItemModel
import com.example.kiosklikeapp.models.MerchantBrandingModel
import com.example.kiosklikeapp.models.MerchantMenuModel
import com.example.kiosklikeapp.ui.composables.DescriptionText
import com.example.kiosklikeapp.ui.composables.SearchableDropdownField
import com.example.kiosklikeapp.ui.composables.TitleText
import com.example.kiosklikeapp.ui.composables.UrlImage

@Composable
fun MerchantHomePageWrapper() {
    val viewModel: MerchantHomePageViewModel = hiltViewModel()

    when (val uiState = viewModel.uiState.collectAsState().value) {
        is MerchantHomeUiState.Success -> MerchantHomePageScreen(
            menus = uiState.menus,
            branding = uiState.branding,
            onSearchTriggered = { viewModel.onSearchTriggered(it) }
        )

        is MerchantHomeUiState.Error -> ErrorScreen(uiState.errorMessage)
        is MerchantHomeUiState.Loading -> LoadingScreen()
    }.also {
        Log.d("TAG", "MerchantHomePageWrapper: viewModel.uiState collected")
    }
}

@Composable
fun MerchantHomePageScreen(
    menus: List<MerchantMenuModel>,
    branding: MerchantBrandingModel,
    onSearchTriggered: (String) -> Unit,
    backgroundColor: Color = Color(0xFFE5E4E2)
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusManager.clearFocus()
            }
            .padding(start = 8.dp, end = 8.dp, bottom = 12.dp)
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        MerchantLogo(coverUrl = branding.coverUrl, logoUrl = branding.logoUrl)

        var selectedMenu by remember(menus) { mutableStateOf(menus.firstOrNull()) }

        MerchantMenus(
            items = menus,
            selectedMenu = selectedMenu?.name ?: "",
            onMenuSelected = { selectedMenu = it },
            onSearchTriggered = onSearchTriggered
        )

        var selectedCategory by remember(selectedMenu) {
            mutableStateOf(selectedMenu?.categories?.firstOrNull())
        }

        selectedMenu?.categories?.let { categories ->
            MerchantCategories(
                categories = categories,
                isCategorySelected = { it == selectedCategory }
            )

            MenuItems(categories = categories)
        }
    }
}

@Composable
private fun MerchantLogo(coverUrl: String?, logoUrl: String?) {
    Box(modifier = Modifier.fillMaxHeight(0.3f)) {
        UrlImage(imageUrl = coverUrl, modifier = Modifier.fillMaxWidth())

        UrlImage(
            imageUrl = logoUrl,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(0.2f)
        )
    }
}

@Composable
private fun MerchantMenus(
    items: List<MerchantMenuModel>,
    selectedMenu: String,
    onMenuSelected: (MerchantMenuModel) -> Unit,
    onSearchTriggered: (String) -> Unit
) {
    SearchableDropdownField(
        items = items.map { it.name },
        selectedMenu = selectedMenu,
        onItemSelected = { menu ->
            items.firstOrNull { it.name == menu }?.let {
                onMenuSelected(it)
            }
        },
        onSearchTriggered = onSearchTriggered
    )
}

@Composable
private fun MerchantCategories(
    categories: List<MenuCategoryModel>,
    isCategorySelected: (MenuCategoryModel) -> Boolean
) {
    val horizontalScrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.Default.ArrowBackIosNew,
            contentDescription = null,
            tint = Color.LightGray
        )

        Row(
            modifier = Modifier
                .horizontalScroll(state = horizontalScrollState)
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (category in categories) {
                CategoryItem(
                    modifier = Modifier,
                    category = category.name,
                    isCategorySelected = isCategorySelected(category)
                )
            }
        }

        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.AutoMirrored.Default.ArrowForwardIos,
            contentDescription = null,
            tint = Color.LightGray
        )
    }
}

@Composable
private fun CategoryItem(
    modifier: Modifier = Modifier,
    category: String,
    isCategorySelected: Boolean
) {
    val textColor: Color = if (isCategorySelected) Color.White else Color.Black

    Text(
        modifier = modifier.background(color = Color.Red, shape = RoundedCornerShape(16.dp)),
        text = category,
        style = TextStyle(
            fontWeight = FontWeight(if (isCategorySelected) 700 else 400),
            color = textColor
        )
    )
}

@Composable
private fun MenuItems(categories: List<MenuCategoryModel>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (category in categories) {
            MenuItemTitle(modifier = Modifier.padding(top = 8.dp), category.name)
            for (items in category.items) MenuItem(items)
        }
    }
}

@Composable
private fun MenuItemTitle(modifier: Modifier = Modifier, title: String) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp),
        text = title,
        style = TextStyle(
            fontSize = 26.sp,
            fontWeight = FontWeight(700)
        )
    )
}

@Composable
private fun MenuItem(item: MenuItemModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            TitleText(text = item.name)
            DescriptionText(text = item.description ?: "")
            TitleText(text = "$${item.price}")
        }

        UrlImage(
            modifier = Modifier
                .height(64.dp)
                .width(64.dp)
                .clip(shape = RoundedCornerShape(8.dp)),
            imageUrl = item.imageUrl,
        )
    }
}
