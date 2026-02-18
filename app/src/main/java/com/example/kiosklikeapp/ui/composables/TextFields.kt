package com.example.kiosklikeapp.ui.composables

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RegularTextField(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Color.Black,
    hintText: String = "Write your instructions here...",
    onTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = text,
        onValueChange = { newValue ->
            onTextChanged(newValue)
        },
        modifier = modifier,
        placeholder = { Text(text = hintText) },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.LightGray,
            unfocusedBorderColor = Color.LightGray,
            focusedTextColor = textColor
        )
    )
}
