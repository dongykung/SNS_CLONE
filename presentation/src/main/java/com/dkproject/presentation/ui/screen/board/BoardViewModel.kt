package com.dkproject.presentation.ui.screen.board

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.dkproject.domain.model.ACTION_POSTED
import com.dkproject.domain.model.Comment
import com.dkproject.domain.usecase.board.DeleteBoardUseCase
import com.dkproject.domain.usecase.board.DeleteCommentUseCase
import com.dkproject.domain.usecase.board.GetBoardListUseCase
import com.dkproject.domain.usecase.board.PostCommentUseCase
import com.dkproject.domain.usecase.token.GetMyHeartUseCase
import com.dkproject.domain.usecase.token.SetMyHeartUseCase
import com.dkproject.presentation.model.BoardCardModel
import com.dkproject.presentation.model.toUiModel
import com.dkproject.presentation.util.CommentWriteTime
import com.dkproject.presentation.util.Constants
import com.dkproject.presentation.util.ParseErrorResponse
import com.dkproject.presentation.util.showToastMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class BoardViewModel @Inject constructor(
    private val getBoardListUseCase: GetBoardListUseCase,
    private val deleteBoardUseCase: DeleteBoardUseCase,
    private val postCommentUseCase: PostCommentUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,

) : ViewModel() {
    companion object {
        const val TAG = "BoardViewModel"
    }

    private val _state = MutableStateFlow(BoardUiState(emptyFlow()))
    val state: StateFlow<BoardUiState> = _state.asStateFlow()


    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            val boardCardModelFlow: Flow<PagingData<BoardCardModel>>
            getBoardListUseCase().onSuccess {
                boardCardModelFlow = it.map { pagingData ->
                    pagingData.map { board ->
                        board.toUiModel()
                    }
                }.cachedIn(viewModelScope)
                Log.d(TAG, boardCardModelFlow.toString())

                _state.update {
                    it.copy(boardItems = boardCardModelFlow)
                }
            }.onFailure {
                Log.d(TAG, "load: fail")
            }

        }
    }

    fun onCommentSend(boardId: Long, text: String) {
        viewModelScope.launch {
            postCommentUseCase(boardId, text).onSuccess { commentId ->
                Log.d(TAG, "onCommentSend: ")
                val comment = Comment(
                    id = commentId,
                    comment = text,
                    createdAt = CommentWriteTime(),
                    createUserId = Constants.myId,
                    userName = Constants.myName,
                    profileImageUrl = Constants.myProfileUrl
                )
                val newAddedComments = state.value.addedComments + Pair(
                    boardId,
                    state.value.addedComments[boardId].orEmpty() + comment
                )
                _state.update { it.copy(addedComments = newAddedComments) }
            }.onFailure {
                Log.d(TAG, it.toString())

            }
        }
    }

    fun onDeleteComment(boardId:Long,comment:Comment,context:Context){
        Log.d(TAG, "${boardId}  ${comment.id}")

        viewModelScope.launch {
            deleteCommentUseCase(boardId = boardId, commentId = comment.id).onSuccess {
                state.value.addedComments
                val newDeletedComments = state.value.deletedComments + Pair(
                    boardId,
                    state.value.deletedComments[boardId].orEmpty() + comment
                )
                Log.d(TAG, "onDeleteComment: ")

                _state.update { it.copy(deletedComments = newDeletedComments) }
            }.onFailure {
                Log.d(TAG, it.toString())
                Log.d(TAG, it.localizedMessage.toString())
                Log.d(TAG, it.cause.toString())

                Log.d(TAG, it.message.toString())
            }
        }
    }

    fun onBoardDelete(boardId: Long, deleteSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            deleteBoardUseCase(boardId).onSuccess {
                Log.d(TAG, "delete success")
                deleteSuccess()
                _state.update { it.copy(deletedBoardItems = state.value.deletedBoardItems + boardId) }
            }.onFailure {
                Log.d(TAG, it.toString())
            }
        }
    }


}


data class BoardUiState(
    val boardItems: Flow<PagingData<BoardCardModel>>,
    val deletedBoardItems: Set<Long> = emptySet(),
    val addedComments: Map<Long, List<Comment>> = emptyMap(),
    val deletedComments: Map<Long, List<Comment>> = emptyMap()

)




