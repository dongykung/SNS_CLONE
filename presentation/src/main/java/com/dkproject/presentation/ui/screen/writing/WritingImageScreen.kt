package com.dkproject.presentation.ui.screen.writing

import android.widget.GridLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Circle
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.dkproject.domain.model.Image
import com.dkproject.presentation.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WritingImageScreen(
    viewModel: WriteViewModel,
    writeexit: () -> Unit,
    nextClick: () -> Unit,
) {
    val state = viewModel.state.collectAsState().value
    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(topBar = {
            CenterAlignedTopAppBar(title = { Text(text = stringResource(id = R.string.newpost)) },
                navigationIcon = {
                    IconButton(onClick = { writeexit() }) {
                        Icon(imageVector = Icons.Outlined.Cancel, contentDescription = "")
                    }
                },
                actions = {
                    TextButton(onClick = { nextClick() }) {
                        Text(text = stringResource(id = R.string.next))
                    }
                })
        }) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                SelecetedImageSection(
                    modifier = Modifier.weight(1f),
                    state.selectedImage.lastOrNull()?.uri
                )

                AllImageSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.White),
                    state.images,
                    selectedImages = state.selectedImage,
                    imageClick = {viewModel.updateSelectedImage(it)}
                )
            }
        }
    }
}

@Composable
fun SelecetedImageSection(
    modifier: Modifier = Modifier,
    selectedLastImage: String?
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = selectedLastImage),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AllImageSection(
    modifier: Modifier = Modifier,
    imageList: List<Image>,
    selectedImages: List<Image>,
    imageClick:(Image)->Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp)
            .height(50.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "사진", color = Color.White)
    }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(110.dp),
        modifier = modifier
    ) {
        items(count = imageList.size, key = { index -> imageList[index].uri }) { index ->
            val image = imageList[index]
            Box(modifier = Modifier.clickable { imageClick(image) }) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    painter = rememberAsyncImagePainter(
                        model = image.uri,
                        contentScale = ContentScale.Crop
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                if (selectedImages.contains(image)) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle, contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 2.dp, end = 2.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.Circle, contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 2.dp, end = 2.dp)
                    )
                }
            }
        }
    }
}

