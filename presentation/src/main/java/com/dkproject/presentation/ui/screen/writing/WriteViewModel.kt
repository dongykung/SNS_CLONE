package com.dkproject.presentation.ui.screen.writing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkproject.domain.model.Image
import com.dkproject.domain.usecase.file.GetImageListUseCase
import com.dkproject.domain.usecase.postwrite.PostBoardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    private val getImageListUseCase: GetImageListUseCase,
    private val postBoardUseCase: PostBoardUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(WriteUiState())
    val state: StateFlow<WriteUiState> = _state.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            val images = getImageListUseCase()
            _state.update { it.copy(selectedImage = images.firstOrNull()?.let { listOf(it) } ?: emptyList()) }
            _state.update { it.copy(images = images) }
        }
    }

    fun updateSelectedImage(image: Image) {
        if (state.value.selectedImage.contains(image)) {
            if (state.value.selectedImage.size != 1) {
                _state.update { it.copy(selectedImage = state.value.selectedImage - image) }
            }
        } else
            _state.update { it.copy(selectedImage = state.value.selectedImage + image) }
    }

    fun updatepostMessage(msg:String){
        _state.update { it.copy(postMessage = msg) }
    }

    fun onPostClick(postDone:()->Unit){
        viewModelScope.launch {
            postBoardUseCase(
                title="title",
                content = state.value.postMessage,
                imageList = state.value.selectedImage
            )
            postDone()
        }
    }


}


data class WriteUiState(
    val images: List<Image> = emptyList(),
    val selectedImage: List<Image> = emptyList(),
    val postMessage: String = "",
)