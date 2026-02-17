package com.example.kiosklikeapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableDropdownField(
    items: List<String>,
    selectedMenu: String,
    onItemSelected: (String) -> Unit,
    onSearchTriggered: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearchMode by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    ExposedDropdownMenuBox(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        expanded = isExpanded,
        onExpandedChange = {
            if (!isSearchMode) isExpanded = !isExpanded
        }
    ) {
        OutlinedTextField(
            value = searchQuery.takeIf { it.isNotBlank() || isSearchMode } ?: selectedMenu,
            onValueChange = {
                if (isExpanded) isExpanded = false
                searchQuery = it
                onSearchTriggered(it)
            },
            shape = RoundedCornerShape(36.dp),
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, isExpanded)
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused) {
                        isSearchMode = false
                        isExpanded = false
                        searchQuery = ""
                    }
                },
            readOnly = !isSearchMode,
            leadingIcon = {
                IconButton(
                    onClick = {
                        isSearchMode = true
                        isExpanded = false
                        focusRequester.requestFocus()
                    }
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }
            },
            placeholder = {
                if (isSearchMode) {
                    Text(
                        text = "Search...",
                        style = TextStyle(
                            color = Color.Gray,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            },
            textStyle = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
            ),
            trailingIcon = {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(color = Color.LightGray, shape = CircleShape)
                ) {
                    val focusManager = LocalFocusManager.current

                    Icon(
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.Center)
                            .clickable {
                                if (isSearchMode) focusManager.clearFocus()
                                else isExpanded = true
                            },
                        imageVector = if (!isSearchMode) Icons.Default.KeyboardArrowDown else Icons.Default.Clear,
                        tint = Color.DarkGray,
                        contentDescription = null
                    )
                }
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                if (!isSearchMode) isExpanded = true
                            }
                        }
                    }
                }
        )

        if (items.isNotEmpty()) ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
                isSearchMode = false
            }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        searchQuery = item
                        onItemSelected(item)
                        isExpanded = false
                        isSearchMode = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Preview
@Composable
fun SearchableDropdownFieldTest() = SearchableDropdownField(
    items = listOf("All menus", "First menu", "Second menu"),
    selectedMenu = "First menu",
    onItemSelected = {},
    onSearchTriggered = {}
)
