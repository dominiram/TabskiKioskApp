package com.example.kiosklikeapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.kiosklikeapp.R
import com.example.kiosklikeapp.models.MerchantBrandingModel
import com.example.kiosklikeapp.models.MerchantMenuModel

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
            .fillMaxHeight(0.3f)
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        MerchantLogo(coverUrl = branding.coverUrl, logoUrl = branding.logoUrl)
        MerchantMenus()
    }
}

@Composable
private fun MerchantLogo(coverUrl: String?, logoUrl: String?) {
    Box {
        coverUrl?.let {
            AsyncImage(
                model = it,
                modifier = Modifier.fillMaxWidth(),
                placeholder = painterResource(R.drawable.ic_placeholder),
                error = painterResource(R.drawable.ic_placeholder),
                contentDescription = null
            )
        }

        logoUrl?.let {
            AsyncImage(
                model = it,
                modifier = Modifier.align(Alignment.Center).fillMaxSize(0.2f),
                placeholder = painterResource(R.drawable.ic_placeholder),
                error = painterResource(R.drawable.ic_placeholder),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun MerchantMenus() {

}
