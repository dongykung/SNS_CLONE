package com.dkproject.presentation.ui.screen.writing

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.dkproject.domain.model.Image
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.components.CustomInputField


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WritingTextScreen(
    viewModel: WriteViewModel,
    onBackClick: () -> Unit,
    postDone:()->Unit
) {
    Log.d("WritingTextScreen", "WritingTextScreen: ")
    val state = viewModel.state.collectAsState().value
    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(topBar = {
            CenterAlignedTopAppBar(title = { Text(text = stringResource(id = R.string.newpost)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }, actions = {
                    TextButton(onClick = {
                        viewModel.onPostClick(postDone = postDone)
                    }) {
                        Text(text = stringResource(id = R.string.complete))
                    }
                })
        }) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                ImagePager(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth(),
                    imageList = state.selectedImage.map { it.uri }
                )
                Divider()
                CustomInputField(
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxWidth(),
                    value = state.postMessage,
                    valueChange = { msg ->
                        viewModel.updatepostMessage(msg)
                    },
                    isSingleLine = false,
                    placeholder = "문구를 입력해주세요",
                    imeAction = ImeAction.Default,
                    keyboardType = KeyboardType.Text
                )
            }
        }

    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagePager(
    modifier: Modifier = Modifier,
    imageList: List<String>
) {
    Log.d("ImagePager", "ImagePager: ")
    val pagerState = rememberPagerState {
        imageList.size
    }

    Box(modifier = modifier) {
        HorizontalPager(state = pagerState) { page ->
            val image = imageList[page]
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberAsyncImagePainter(model = image), contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
           imageList.forEachIndexed  { index,it->
                val color = if (pagerState.currentPage == index) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = color)
                        .size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}