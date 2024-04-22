package com.dkproject.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dkproject.presentation.model.BoardCardModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheet(
    model: BoardCardModel?,
    dismiss: () -> Unit,
    onBoardDelte: (BoardCardModel) -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    model?.run {
        ModalBottomSheet(
            modifier=Modifier.fillMaxSize(),
            sheetState = bottomSheetState,
            onDismissRequest = dismiss
        ) {
            TextButton(onClick = {
                onBoardDelte(this@run)
                dismiss()
            }) {
                Text(text = "삭제하기")
            }
        }
    }
}