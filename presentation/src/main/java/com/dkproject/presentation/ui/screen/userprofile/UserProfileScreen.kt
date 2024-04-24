package com.dkproject.presentation.ui.screen.userprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dkproject.presentation.model.BoardCardModel
import com.dkproject.presentation.ui.components.CustomTopAppBar
import com.dkproject.presentation.ui.screen.setting.BoardSection
import com.dkproject.presentation.ui.screen.setting.InfoSection


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    viewModel: UserProfileViewModel,
    onBackClick: () -> Unit,
) {
    val state = viewModel.state.collectAsState().value
    val items: LazyPagingItems<BoardCardModel> = state.boardItems.collectAsLazyPagingItems()

    Surface(modifier=Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                CustomTopAppBar(title = state.username, onBack = true, onBackClick = onBackClick)
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {

                InfoSection(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    userId = state.userId,
                    profileUrl = state.profileImageUrl.toString(),
                    username = state.username,
                    statusmsg = state.statusMessage
                )
                Divider()
                BoardSection(myBoards = items, deleteBoardList = state.deletedBoardItems)
            }
        }
    }
}
