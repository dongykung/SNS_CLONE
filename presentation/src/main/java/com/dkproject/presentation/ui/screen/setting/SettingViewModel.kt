package com.dkproject.presentation.ui.screen.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.dkproject.domain.usecase.board.GetMyBoardUseCase
import com.dkproject.domain.usecase.token.ClearTokenUseCase
import com.dkproject.domain.usecase.user.GetMyInfoUseCase
import com.dkproject.presentation.model.BoardCardModel
import com.dkproject.presentation.model.toUiModel
import com.dkproject.presentation.ui.screen.board.BoardViewModel
import com.dkproject.presentation.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getUserInfoUseCase: GetMyInfoUseCase,
    private val clearTokenUseCase: ClearTokenUseCase,
    private val getMyBoardUseCase: GetMyBoardUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SettingUiState(emptyFlow()))
    val state: StateFlow<SettingUiState> = _state.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            getUserInfoUseCase().onSuccess { user ->
                Log.d("SettingViewModel", user.toString())
                updateState(
                    id = user.id,
                    name = user.userName,
                    url = user.profileFilePath.toString(),
                    stmsg = user.extraUserInfo
                )
                Constants.myId = user.id
                Constants.myProfileUrl=user.profileFilePath.toString()
                Constants.myName=user.userName
                getMyBoard(user.id)
            }.onFailure {

            }
        }
    }

    fun getMyBoard(userId: Long) {
        viewModelScope.launch {
            val boardCardModelFlow: Flow<PagingData<BoardCardModel>>
            getMyBoardUseCase(userId).onSuccess {
                boardCardModelFlow = it.map { pagingData ->
                    pagingData.map { board ->
                        board.toUiModel()
                    }
                }.cachedIn(viewModelScope)
                _state.update { it.copy(boardItems = boardCardModelFlow) }
            }.onFailure {

            }
        }
    }

    fun updateState(id: Long, name: String, url: String, stmsg: String) {
        _state.update {
            it.copy(
                userId = id,
                profileImageUrl = url,
                username = name,
                statusMessage = stmsg
            )
        }
    }

    fun logOut(moveToLogin: () -> Unit) {
        viewModelScope.launch {
            clearTokenUseCase()
            moveToLogin()
        }
    }

    fun onBoardDelete(boardId: Long) {
        viewModelScope.launch {
            _state.update { it.copy(deletedBoardItems = state.value.deletedBoardItems + boardId) }
        }
    }
}


data class SettingUiState(
    val boardItems: Flow<PagingData<BoardCardModel>>,
    val deletedBoardItems: Set<Long> = emptySet(),
    val userId: Long? = null,
    val profileImageUrl: String? = null,
    val username: String = "",
    val statusMessage: String = ""
)