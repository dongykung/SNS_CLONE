package com.dkproject.presentation.ui.screen.userprofile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.dkproject.domain.usecase.board.GetMyBoardUseCase
import com.dkproject.domain.usecase.user.GetUserInfoUseCase
import com.dkproject.presentation.model.BoardCardModel
import com.dkproject.presentation.model.toUiModel
import com.dkproject.presentation.ui.screen.setting.SettingUiState
import com.dkproject.presentation.util.ParseErrorResponse
import com.dkproject.presentation.util.showToastMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getMyBoardUseCase: GetMyBoardUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SettingUiState(emptyFlow()))
    val state: StateFlow<SettingUiState> = _state.asStateFlow()

    fun load(userId: Long, context: Context) {
        viewModelScope.launch {
            getUserInfoUseCase(userId).onSuccess { user ->
                _state.update {
                    it.copy(
                        userId = user.id, profileImageUrl = user.profileFilePath,
                        username = user.userName, statusMessage = user.extraUserInfo
                    )
                }
                getUserBoard(user.id, context)
            }.onFailure {
                if (it is HttpException) {
                    val errorMessage = it.response()?.errorBody()?.string()
                    val message = ParseErrorResponse(errorMessage ?: "")
                    showToastMessage(context, message)
                } else {
                    showToastMessage(context, "유저 정보를 가져오는데 실패하였습니다.")
                }
            }
        }
    }

    fun getUserBoard(userId: Long, context: Context) {
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
                if (it is HttpException) {
                    val errorMessage = it.response()?.errorBody()?.string()
                    val message = ParseErrorResponse(errorMessage ?: "")
                    showToastMessage(context, message)
                } else {
                    showToastMessage(context, "유저 게시글을 가져오는데 실패하였습니다")
                }
            }
        }
    }


}



