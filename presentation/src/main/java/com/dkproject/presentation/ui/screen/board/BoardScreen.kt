package com.dkproject.presentation.ui.screen.board

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dkproject.presentation.model.BoardCardModel
import com.dkproject.presentation.ui.components.CustomBottomSheet
import com.dkproject.presentation.ui.components.CustomImage
import com.dkproject.presentation.ui.components.CustomTopAppBar
import com.dkproject.presentation.ui.screen.writing.ImagePager
import com.dkproject.presentation.ui.theme.SNS_CloneTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoardScreen(
    modifier: Modifier=Modifier,
    viewModel: BoardViewModel
) {
    val state = viewModel.state.collectAsState().value
    val items: LazyPagingItems<BoardCardModel> = state.boardItems.collectAsLazyPagingItems()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
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
                LazyColumn(modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(6.dp)
                ) {
                    items(count = items.itemCount,
                        key = { index ->
                            items.get(index)?.boardId ?: index
                        }) { index ->
                        items[index]?.run {
                            if (!state.deletedBoardItems.contains(this.boardId))
                                BoardCard(
                                    profileImageUrl = this.userProfileUrl,
                                    username = this.username,
                                    images = this.images,
                                    text = this.text,
                                    onReplyClick = {

                                    },
                                    onOptionClick = {
                                        modelDialog = this
                                    })
                        }
                    }

                }
            }
        }
    }

}

@Composable
fun BoardCard(
    profileImageUrl: String,
    username: String,
    images: List<String>,
    text: String,
    onOptionClick: () -> Unit,
    onReplyClick: () -> Unit,
) {
    Column {
        //헤더
        BoarderHeader(
            profileImageUrl = profileImageUrl,
            username = username,
            onOptionClick = onOptionClick
        )
        //이미지 페이저
        ImagePager(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp), imageList = images)

        // 내용
        var maxLines by remember { mutableStateOf(1) }
        var showMore by remember { mutableStateOf(false) }
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
            onClick = onReplyClick
        ) {
            Text(text = "댓글")
        }
    }

}

@Composable
fun BoarderHeader(
    modifier: Modifier = Modifier,
    profileImageUrl: String,
    username: String,
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
                .size(36.dp)
                .clip(CircleShape), url = profileImageUrl
        )
        //userName
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = username,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )
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

@Composable
@Preview
fun testpreview() {
    SNS_CloneTheme {
        BoardCard(
            profileImageUrl = "asdf",
            username = "dongkyung",
            images = emptyList(),
            text = "hello",
            onOptionClick = { },
            onReplyClick = {})
    }
}