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
import com.dkproject.domain.usecase.board.DeleteBoardUseCase
import com.dkproject.domain.usecase.board.GetBoardListUseCase
import com.dkproject.presentation.model.BoardCardModel
import com.dkproject.presentation.model.toUiModel
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
import javax.inject.Inject


@HiltViewModel
class BoardViewModel @Inject constructor(
    private val getBoardListUseCase: GetBoardListUseCase,
    private val deleteBoardUseCase: DeleteBoardUseCase
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

    fun onBoardDelete(boardId: Long) {
        viewModelScope.launch {
            deleteBoardUseCase(boardId).onSuccess {
                Log.d(TAG, "delete success")
                _state.update { it.copy(deletedBoardItems = state.value.deletedBoardItems+boardId) }
            }.onFailure {
                Log.d(TAG, it.toString())
            }
        }
    }

}


data class BoardUiState(
    val boardItems: Flow<PagingData<BoardCardModel>>,
    val deletedBoardItems:Set<Long> = emptySet()
)




