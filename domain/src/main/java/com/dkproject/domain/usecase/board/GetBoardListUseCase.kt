package com.dkproject.domain.usecase.board

import androidx.paging.PagingData
import com.dkproject.domain.model.Board
import kotlinx.coroutines.flow.Flow

interface GetBoardListUseCase {
    suspend operator fun invoke():Result<Flow<PagingData<Board>>>
}