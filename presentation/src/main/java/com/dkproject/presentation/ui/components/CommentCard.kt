package com.dkproject.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dkproject.presentation.ui.theme.SNS_CloneTheme
import com.dkproject.presentation.util.Constants
import com.dkproject.presentation.util.formatElapsedTime

@Composable
fun CommentCard(
    modifier: Modifier = Modifier,
    profileImageUrl: String,
    userId: Long,
    createdAt: String,
    userName: String,
    text: String,
    onDeleteCommnet: () -> Unit,
    ) {
    var visible by remember {
        mutableStateOf(false)
    }
    if (visible)
        AlertDialog(onDismissRequest = { visible = false }, confirmButton = {
            TextButton(onClick = {
                onDeleteCommnet()
                visible=false
            }) {
                Text(text = "확인", color = Color.White)
            }
        }, dismissButton = {
            TextButton(onClick = { visible = false }) {
                Text(text = "취소", color = Color.White)
            }
        },
            text = { Text(text = "댓글을 삭제하시겠습니까?",color=Color.White)})
    Surface {
        Row(
            modifier = modifier
                .padding(vertical = 8.dp)
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomImage(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape), url = profileImageUrl
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = userName, color = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = formatElapsedTime(createdAt),
                        style = TextStyle(color = Color.LightGray, fontSize = 12.sp)
                    )
                }
                Text(text = text, color = Color.White)
            }
            Spacer(modifier = Modifier.weight(1f))
            if (userId == Constants.myId) {
                IconButton(onClick = { visible = true }) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Filled.Clear, contentDescription = "Clear",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

