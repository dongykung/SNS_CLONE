package com.dkproject.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.dkproject.domain.model.Comment
import com.dkproject.presentation.ui.theme.SNS_CloneTheme
import com.dkproject.presentation.util.rememberImeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentDialog(
    visible: Boolean,
    comments: List<Comment>,
    onDismiss: () -> Unit,
    onCloseClick: () -> Unit,
    onCommentSend: (String) -> Unit,
    onClickUser: (Long) -> Unit,
    onCommentDelete: (Comment) -> Unit = {}
) {
    val lazyListState = rememberLazyListState()
    LaunchedEffect(key1 = comments) {
        lazyListState.scrollToItem(comments.lastIndex)
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    if (visible) {
       AlertDialog(onDismissRequest = onDismiss,
           properties = DialogProperties(usePlatformDefaultWidth = false)
       ){
            var text by remember {
                mutableStateOf("")
            }
            Scaffold {pd->
                Column(modifier= Modifier
                    .fillMaxSize()
                    .padding(pd)) {
                    Row(modifier=Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "댓글 "+comments.size, color = Color.White,modifier=Modifier.padding(start=12.dp))
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = { onCloseClick()}) {
                            Icon(imageVector = Icons.Filled.Clear, contentDescription = "",
                                tint=Color.White)
                        }
                    }

                    LazyColumn(modifier = Modifier.weight(1f),
                        state = lazyListState) {
                        items(count = comments.size) { index ->
                            val comment = comments[index]
                            CommentCard(
                                modifier = Modifier.clickable { onClickUser(comment.createUserId) },
                                profileImageUrl = comment.profileImageUrl,
                                userId = comment.createUserId,
                                userName = comment.userName,
                                text = comment.comment,
                                createdAt = comment.createdAt,
                                onDeleteCommnet = { onCommentDelete(comment) }
                            )
                        }
                    }
                    Row (modifier= Modifier
                        .fillMaxWidth()
                        .imePadding()){
                        CustomInputField(
                            modifier=Modifier.weight(1f),
                            value = text,
                            valueChange = {text=it},
                            placeholder = "댓글을 입력해주세요",
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Text
                        )
                        IconButton(onClick = {
                            onCommentSend(text)
                            text=""
                            keyboardController?.hide()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "",
                                tint = Color.White
                            )
                        }
                    }
                }
            }

        }
    }
}






