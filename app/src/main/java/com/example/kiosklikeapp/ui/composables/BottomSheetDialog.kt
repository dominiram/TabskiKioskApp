package com.example.kiosklikeapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kiosklikeapp.models.MenuItemModel
import com.example.kiosklikeapp.ui.Constants.DARK_RED_COLOR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemToCardBottomSheetDialog(
    modifier: Modifier = Modifier,
    item: MenuItemModel,
    addItemToCart: (MenuItemModel, Int) -> Unit,
    onDismiss: () -> Unit
) {
    val bottomSheetDialogState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
        sheetState = bottomSheetDialogState,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
                .background(color = Color.White)
                .padding(bottom = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.4f),
                ) {
                    UrlImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        imageUrl = item.imageUrl
                    )

                    Icon(
                        modifier = Modifier
                            .clickable { onDismiss() }
                            .padding(top = 8.dp, end = 8.dp)
                            .align(Alignment.TopEnd)
                            .size(36.dp)
                            .padding(4.dp)
                            .background(shape = CircleShape, color = Color(0x99CBCBCB)),
                        imageVector = Icons.Default.Close,
                        tint = Color.White,
                        contentDescription = null
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TitleTextLarge(text = item.name)
                    TitleTextLarge(text = "$${item.price}")
                }

                DescriptionText(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = item.description ?: ""
                )

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(horizontal = 16.dp)
                        .background(color = Color.LightGray)
                )

                TitleText(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "Order Notes. Please be concise!"
                )

                var text by remember { mutableStateOf("") }

                RegularTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = text,
                    onTextChanged = { text = it }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f)
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                var itemCount by remember { mutableIntStateOf(1) }

                AddItemButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .weight(1f),
                    itemCount = itemCount,
                    decrementItemCount = { itemCount -= 1 },
                    incrementItemCount = { itemCount += 1 },
                    backgroundColor = Color(0xFFE5E4E2)
                )

                AddToOrderButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .weight(2f)
                        .clickable {
                            addItemToCart(item, itemCount)
                            onDismiss()
                        },
                    price = item.price,
                    backgroundColor = Color(DARK_RED_COLOR)
                )
            }
        }
    }
}
