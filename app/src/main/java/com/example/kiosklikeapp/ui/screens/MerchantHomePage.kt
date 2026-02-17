package com.example.kiosklikeapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
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
        is MerchantHomeUiState.Success -> MerchantHomePageScreen(uiState.menus, uiState.branding)
        is MerchantHomeUiState.Error -> ErrorScreen(uiState.errorMessage)
        is MerchantHomeUiState.Loading -> LoadingScreen()
    }
}

@Composable
fun MerchantHomePageScreen(menus: List<MerchantMenuModel>, branding: MerchantBrandingModel) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        MerchantLogo(coverUrl = branding.coverUrl, logoUrl = branding.logoUrl)

        var selectedMenu: MerchantMenuModel? by remember { mutableStateOf(menus.firstOrNull()) }

        MerchantMenus(
            items = menus,
            selectedMenu = selectedMenu?.name ?: "",
            onMenuSelected = { selectedMenu = it }
        )

        var selectedCategory by remember {
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
    onMenuSelected: (MerchantMenuModel) -> Unit
) {
    SearchableDropdownField(
        items = items.map { it.name },
        selectedMenu = selectedMenu,
        onItemSelected = { item ->
            items.firstOrNull { it.name == item }?.let {
                onMenuSelected(it)
            }

        }
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
            .background(color = Color.LightGray),
        text = title,
        style = TextStyle(
            fontSize = 36.sp,
            fontWeight = FontWeight(700)
        )
    )
}

@Composable
private fun MenuItem(item: MenuItemModel) {
    Row(
        modifier = Modifier
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            TitleText(text = item.name)
            item.description?.let { DescriptionText(text = it) }
            TitleText(text = "$${item.price}")
        }

        UrlImage(
            imageUrl = item.imageUrl,
            modifier = Modifier.size(48.dp),
        )
    }
}
