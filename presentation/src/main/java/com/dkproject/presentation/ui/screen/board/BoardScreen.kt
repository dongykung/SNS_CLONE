package com.dkproject.presentation.ui.screen.board

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dkproject.domain.model.Comment
import com.dkproject.presentation.model.BoardCardModel
import com.dkproject.presentation.ui.components.CommentDialog
import com.dkproject.presentation.ui.components.CustomBottomSheet
import com.dkproject.presentation.ui.components.CustomImage
import com.dkproject.presentation.ui.components.CustomTopAppBar
import com.dkproject.presentation.ui.screen.writing.ImagePager
import com.dkproject.presentation.ui.theme.SNS_CloneTheme
import com.dkproject.presentation.util.formatElapsedTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoardScreen(
    modifier: Modifier = Modifier,
    viewModel: BoardViewModel,
    onClickUser: (Long) -> Unit
) {
    val state = viewModel.state.collectAsState().value
    val items: LazyPagingItems<BoardCardModel> = state.boardItems.collectAsLazyPagingItems()
    Log.d("BoardScreen", items.toString())

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    var modelDialog: BoardCardModel? by remember { mutableStateOf(null) }

    CustomBottomSheet(model = modelDialog, dismiss = { modelDialog = null }, onBoardDelte = {
        viewModel.onBoardDelete(it.boardId)
    })



    Surface(modifier = modifier.fillMaxSize()) {
        Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CustomTopAppBar(title = "SNS", scrollBehavior = scrollBehavior)
            }) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(6.dp)
                ) {
                    items(count = items.itemCount,
                        key = { index ->
                            items.get(index)?.boardId ?: index
                        }) { index ->
                        items[index]?.run {
                            if (!state.deletedBoardItems.contains(this.boardId))
                                BoardCard(
                                    userId = this.userId,
                                    profileImageUrl = this.userProfileUrl,
                                    username = this.username,
                                    images = this.images,
                                    createdAt = this.createAt,
                                    text = this.text,
                                    comments = this.commentList,
                                    onOptionClick = {
                                        modelDialog = this
                                    },
                                    onClickUser = onClickUser)
                        }
                    }

                }
            }
        }
    }

}

@Composable
fun BoardCard(
    userId: Long,
    profileImageUrl: String,
    username: String,
    images: List<String>,
    text: String,
    createdAt: String,
    comments: List<Comment>,
    onClickUser:(Long)->Unit,
    onOptionClick: () -> Unit,
) {
    Log.d("BoardCard", userId.toString())
    var commentDialogVisible by remember {
        mutableStateOf(false)
    }
    Column {
        //헤더
        BoarderHeader(
            profileImageUrl = profileImageUrl,
            username = username,
            createdAt = createdAt,
            userId = userId,
            onOptionClick = onOptionClick,
            onClickUser = onClickUser
        )
        //이미지 페이저
        ImagePager(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp), imageList = images
        )

        // 내용
        var maxLines by rememberSaveable { mutableStateOf(1) }
        var showMore by rememberSaveable { mutableStateOf(false) }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp), text = text,
            color = Color.White,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResult ->
                showMore = textLayoutResult.didOverflowHeight
            }
        )

        if (showMore) {
            TextButton(onClick = {
                maxLines = Integer.MAX_VALUE
            }) {
                Text(
                    text = "더보기",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.LightGray
                )
            }
        }

        TextButton(
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(horizontal = 8.dp)
                .align(Alignment.End),
            onClick = { commentDialogVisible = true }
        ) {
            Text(text = "댓글")
        }
    }
    CommentDialog(visible = commentDialogVisible,
        comments = comments,
        onDismiss = { commentDialogVisible = false },
        onCloseClick = { commentDialogVisible = false },
        onClickUser = onClickUser)
}

@Composable
fun BoarderHeader(
    modifier: Modifier = Modifier,
    userId:Long,
    profileImageUrl: String,
    createdAt:String,
    username: String,
    onClickUser: (Long) -> Unit,
    onOptionClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(12.dp))
        //profile Image
        CustomImage(
            modifier = Modifier
                .clickable { onClickUser(userId) }
                .size(38.dp)
                .clip(CircleShape), url = profileImageUrl
        )
        //userName
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = username,
                style  = TextStyle(color=Color.White, fontSize = 18.sp,fontWeight = FontWeight.Bold),
                color = Color.White
            )
            Text(modifier = Modifier.padding(horizontal = 8.dp),
                text = formatElapsedTime(createdAt),
                style = TextStyle(color=Color.White, fontSize = 14.sp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { onOptionClick() }) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "option",
                tint = Color.White
            )
        }
    }
}

