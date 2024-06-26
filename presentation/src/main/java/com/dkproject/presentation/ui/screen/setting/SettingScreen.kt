package com.dkproject.presentation.ui.screen.setting

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.dkproject.presentation.R
import com.dkproject.presentation.activity.EditProfileActivity
import com.dkproject.presentation.activity.LoginActivity
import com.dkproject.presentation.model.BoardCardModel
import com.dkproject.presentation.ui.components.CustomImage
import com.dkproject.presentation.ui.components.CustomTopAppBar
import com.dkproject.presentation.util.Constants


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(viewModel: SettingViewModel) {
    Log.d("SettingScreen", "SettingScreen: ")
    val state = viewModel.state.collectAsState().value
    val items: LazyPagingItems<BoardCardModel> = state.boardItems.collectAsLazyPagingItems()


    val context = LocalContext.current
    val editProfileActivityLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                viewModel.load()
            }
        }
    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(topBar = {
            CustomTopAppBar(title = stringResource(id = R.string.mypage),
                action = true,
                actionIcon = Icons.AutoMirrored.Outlined.ExitToApp,
                actionClick = {
                    viewModel.logOut {
                        context.startActivity(Intent(context, LoginActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        })
                    }
                })
        }) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                InfoSection(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    userId = state.userId,
                    profileUrl = state.profileImageUrl.toString(),
                    username = state.username, statusmsg = state.statusMessage,
                    editClick = {
                        editProfileActivityLauncher.launch(Intent(
                            context,
                            EditProfileActivity::class.java
                        ).apply {
                            putExtra("profile", state.profileImageUrl)
                            putExtra("name", state.username)
                            putExtra("status", state.statusMessage)
                        })
                    }
                )
                Divider()
                BoardSection(myBoards = items, deleteBoardList = state.deletedBoardItems)
            }
        }
    }
}


@Composable
fun InfoSection(
    modifier: Modifier = Modifier,
    userId: Long?,
    profileUrl: String,
    username: String,
    statusmsg: String,
    editClick: () -> Unit = {},
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        //profile Image
        CustomImage(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape), url = profileUrl
        )

        Spacer(modifier = Modifier.width(22.dp))
        //username,status section
        Column(verticalArrangement = Arrangement.Center) {
            //username
            Text(text = username, style = TextStyle(color = Color.White, fontSize = 22.sp))
            Spacer(modifier = Modifier.height(6.dp))
            //status message
            Text(text = statusmsg, style = TextStyle(color = Color.LightGray))
        }
        Spacer(modifier = Modifier.weight(1f))
        //edit profile button
        if (userId == Constants.myId)
            Button(onClick = editClick) {
                Text(text = "편집", color = Color.Black)
            }
    }
}

@Composable
fun BoardSection(
    modifier: Modifier = Modifier,
    myBoards: LazyPagingItems<BoardCardModel>,
    deleteBoardList: Set<Long>,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(110.dp)
    ) {
        items(count = myBoards.itemCount, key = { index ->
            myBoards[index]?.boardId ?: index
        }) { index ->
            myBoards[index]?.run {
                if (!deleteBoardList.contains(this.boardId))
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .aspectRatio(1f),
                        painter = rememberAsyncImagePainter(
                            model = this.images.firstOrNull(),
                            contentScale = ContentScale.Crop
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
            }
        }

    }
}


