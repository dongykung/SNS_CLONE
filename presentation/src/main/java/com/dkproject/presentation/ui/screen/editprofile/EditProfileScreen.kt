package com.dkproject.presentation.ui.screen.editprofile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.components.CustomImage
import com.dkproject.presentation.ui.components.CustomInputField
import com.dkproject.presentation.ui.components.CustomTopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    viewModel: EditProfileVIewModel,
    onBackClick: () -> Unit,
    updateCompleted:()->Unit,
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState().value
    val visualMediaPcikerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                viewModel.profileChange = true
                viewModel.updateProfile(uri.toString())
            }
        }
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = stringResource(id = R.string.editpage),
                onBack = true, onBackClick = onBackClick,
                action = true,
                completeText = true, completedClick = {
                    viewModel.updateUserInfo(context=context,updateCompleted = {
                        updateCompleted()
                    })
                }
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                CustomImage(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .clickable {
                            visualMediaPcikerLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        },
                    url = state.profileImageUrl.toString(),
                )
                Text(
                    modifier = Modifier
                        .padding(top = 3.dp)
                        .padding(5.dp),
                    text = stringResource(id = R.string.imagechange),
                    color = Color(21, 239, 201)
                )
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = stringResource(id = R.string.nickname),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold, color = Color.White,
                        fontSize = 22.sp
                    ),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 16.dp, bottom = 6.dp)
                )

                CustomInputField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp),
                    value = state.username,
                    valueChange = { viewModel.updatename(it) },
                    placeholder = stringResource(id = R.string.nickname),
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                )

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.status),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold, color = Color.White,
                        fontSize = 22.sp
                    ),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 16.dp, bottom = 6.dp)
                )
                CustomInputField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp),
                    value = state.statusMessage,
                    valueChange = { viewModel.updatestatus(it) },
                    placeholder = stringResource(id = R.string.nickname),
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                )
            }
        }
    }
}