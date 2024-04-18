package com.dkproject.presentation.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionDialog(
    context: Context,
    dismiss: (Boolean) -> Unit,
) {
        AlertDialog(modifier=Modifier.padding(10.dp),
            onDismissRequest = { dismiss(false) },
            confirmButton = {
                TextButton(onClick = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package",context.packageName,null)
                    }
                    context.startActivity(intent)
                }) {
                    Text(modifier = Modifier.align(Alignment.CenterVertically), text = "확인")
                }
            },
            dismissButton = {
                TextButton(onClick = { dismiss(false) }) {
                    Text(modifier=Modifier.align(Alignment.CenterVertically),text = "취소")
                }
            },
            title = { Text(text = "미디어 저장소 권한 부여")},
            text = { Text(text = "포스팅하기 위해서 미디어 권한이 필요합니다. 권한을 허용하러 이동하시겠습니까?",color = Color.White)})

}