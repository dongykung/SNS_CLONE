package com.dkproject.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.filled.Adb
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    onBack: Boolean = false,
    onBackClick: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    action: Boolean = false,
    completeText: Boolean = false,
    completedClick:()->Unit={},
    actionIcon: ImageVector = Icons.Filled.Adb,
    actionClick: () -> Unit = {}
) {
    TopAppBar(title = { Text(text = title, color = Color.White) },
        navigationIcon = {
            if (onBack) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Arrow Back"
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
        actions = {
            if (action) {
                if (completeText) {
                    TextButton(onClick = { completedClick() }) {
                        Text(text = "완료", style = TextStyle(color = Color(21,239,201),
                            fontSize = 18.sp))
                    }
                } else {
                    IconButton(onClick = { actionClick() }) {
                        Icon(
                            imageVector = actionIcon, contentDescription = "Exit",
                            tint = Color.White
                        )
                    }
                }
            }
        })
}