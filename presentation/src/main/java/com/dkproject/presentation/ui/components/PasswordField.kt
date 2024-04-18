package com.dkproject.presentation.ui.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation


@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    value: String,
    valueChange: (String) -> Unit,
    labelId: String,
    enabled: Boolean = true,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Password,
    imeAction: ImeAction,
    passwordVisibility: MutableState<Boolean>,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    checkpw:Boolean=false,
    checkpwValue:String=""
) {
    val visualTransformation = if (passwordVisibility.value) VisualTransformation.None
    else PasswordVisualTransformation()

    OutlinedTextField(
        modifier=modifier,
        value = value,
        onValueChange = valueChange,
        label = { Text(text = labelId)},
        singleLine = isSingleLine,
        enabled=enabled,
        keyboardOptions = KeyboardOptions(keyboardType=keyboardType, imeAction = imeAction),
        keyboardActions=keyboardActions,
        trailingIcon = {
            PasswordVisibility(passwordVisibility = passwordVisibility)
        },
        visualTransformation=visualTransformation,
        colors = TextFieldDefaults.colors(unfocusedLabelColor = Color.White,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = if(checkpw){
                if(checkpwValue==value) MaterialTheme.colorScheme.onSurfaceVariant
                else Color.Red
            } else MaterialTheme.colorScheme.onSurfaceVariant,
            focusedIndicatorColor = if(checkpw){
                if(checkpwValue==value) MaterialTheme.colorScheme.onSurfaceVariant
                else Color.Red
            } else MaterialTheme.colorScheme.onSurfaceVariant)
        )
}


@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible = passwordVisibility.value
    IconButton(onClick = {passwordVisibility.value=!visible}) {
        Icon(imageVector = Icons.Default.RemoveRedEye, contentDescription = "")
    }
}