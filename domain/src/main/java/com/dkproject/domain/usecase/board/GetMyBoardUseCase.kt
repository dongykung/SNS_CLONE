package com.dkproject.domain.usecase.board

import androidx.paging.PagingData
import com.dkproject.domain.model.Board
import kotlinx.coroutines.flow.Flow

interface GetMyBoardUseCase {
    suspend operator fun invoke(userId:Long):Result<Flow<PagingData<Board>>>
}