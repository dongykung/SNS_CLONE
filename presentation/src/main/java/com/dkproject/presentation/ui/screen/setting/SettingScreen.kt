package com.dkproject.presentation.ui.screen.setting

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material3.Button
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
import coil.compose.rememberAsyncImagePainter
import com.dkproject.presentation.R
import com.dkproject.presentation.activity.EditProfileActivity
import com.dkproject.presentation.activity.LoginActivity
import com.dkproject.presentation.ui.components.CustomImage
import com.dkproject.presentation.ui.components.CustomTopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(viewModel: SettingViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current
    val editProfileActivityLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                viewModel.load()
            }
        }
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
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 6.dp)
            ) {
                myInfoSection(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
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
            }
        }
    }
}

@Composable
fun myInfoSection(
    modifier: Modifier = Modifier,
    profileUrl: String,
    username: String,
    statusmsg: String,
    editClick: () -> Unit,
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
        Button(onClick = editClick) {
            Text(text = "편집", color = Color.Black)
        }
    }
}


