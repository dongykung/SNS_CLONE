package com.dkproject.presentation.ui.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType


@Composable
fun CustomInputField(
    modifier: Modifier=Modifier,
    value:String,
    valueChange:(String)->Unit,
    placeholder:String,
    enabled:Boolean = true,
    isSingleLine:Boolean=true,
    imeAction: ImeAction,
    keyboardType: KeyboardType,
    keyboardAction: KeyboardActions = KeyboardActions.Default
    ) {
    OutlinedTextField(
        modifier=modifier,
        value = value,
        onValueChange =valueChange,
        enabled=enabled,
        placeholder = { Text(text = placeholder)},
        singleLine = isSingleLine,
        keyboardOptions = KeyboardOptions(keyboardType=keyboardType, imeAction = imeAction),
        keyboardActions = keyboardAction,
        colors = TextFieldDefaults.colors(unfocusedLabelColor = Color.White,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent)
        )
}